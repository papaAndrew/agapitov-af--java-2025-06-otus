package ru.aaf.finshop.client.controllers.controllers;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import ru.aaf.finshop.client.SomethingWrongException;
import ru.aaf.finshop.client.domain.ClientView;
import ru.aaf.finshop.proto.ClientProto;
import ru.aaf.finshop.proto.IdProto;
import ru.aaf.finshop.proto.PrincipalProto;
import ru.aaf.finshop.proto.RemoteServiceGrpc;

@SuppressWarnings("java:S125")
@Controller
public class BankClientController {
    private static final Logger logger = LoggerFactory.getLogger(BankClientController.class);

    private final ManagedChannel channel;

    public BankClientController(ManagedChannel channel) {
        this.channel = channel;
    }

    @GetMapping("/login")
    public String login(Model model) {
        logger.info("Welcome to login page");
        //        var clientView = new ClientView("papa", "6501", null, null);
        //        model.addAttribute("clientView", clientView);
        return "loginPage";
    }

    @PostMapping("/login")
    public RedirectView loginPost(
            @RequestParam("username") String username, @RequestParam("password") String password, Model model) {
        logger.info("Somebody requests POST Login");

        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var principal = PrincipalProto.newBuilder().setLogin(username).build();
        var response = stub.getProfileByPrincipal(principal);
        //        var clientId = new ProfileDto(response.getId(), response.getClientId(), null);
        logger.info("response.authorized: {}", response.getAuthorized());
        if (!response.getAuthorized()) {
            throw new SomethingWrongException("Profile not found");
        }
        var clientIdParam = response.getClientId() == 0 ? "" : "?clientId=" + response.getClientId();

        return new RedirectView(String.format("/profile/%s%s", response.getId(), clientIdParam), true);
    }

    @GetMapping("/profile/{profileId}")
    public String clientAccount(
            @PathVariable("profileId") String profileId,
            @RequestParam(value = "clientId", required = false) String clientId,
            Model model) {
        logger.info("profileId: {}; clientId: {}", profileId, clientId);

        var clientView = new ClientView(Long.parseLong(profileId));

        if (clientId != null) {
            var stub = RemoteServiceGrpc.newBlockingStub(channel);
            var client = stub.getClientById(
                    IdProto.newBuilder().setId(Long.parseLong(clientId)).build());

            clientView.setClientId(client.getId());
            clientView.setName(client.getName());
            clientView.setPassport(client.getPassport());
        }

        model.addAttribute("clientView", clientView);

        return "creditClaim";
    }

    @PostMapping("/client/save")
    public RedirectView saveClient(
            @RequestParam(value = "name", required = true) String clientName,
            @RequestParam(value = "passport", required = false) String passport,
            @RequestParam(value = "income", required = false) String income,
            @RequestParam(value = "clientId", required = false) String clientId,
            Model model) {
        logger.info("saveClient: {}", clientName);

        var clientProto = ClientProto.newBuilder()
                .setId(clientId == null ? 0 : Long.parseLong(clientId))
                .setName(clientName)
                .setPassport(passport)
                .build();
        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var response = stub.saveClient(clientProto);

        logger.info("redirect to clientId: {}", response.getId());

        return new RedirectView(String.format("/profile/%s?clientId=%s", response.getId(), response.getId()), true);
    }

    public RedirectView saveClient1(@RequestBody() ClientView clientView, Model model) {
        logger.info("saveClient: {}", clientView);

        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var response = stub.saveClient(mapClientProto(clientView));

        return new RedirectView(String.format("/profile/%s?clientId=%s", response.getId(), response.getId()), true);
    }

    private ClientProto mapClientProto(ClientView clientView) {
        return ClientProto.newBuilder()
                .setId(clientView.getClientId() == null ? 0 : clientView.getClientId())
                .setName(clientView.getName())
                .setPassport(clientView.getPassport())
                .build();
    }
}
