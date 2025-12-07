package ru.otus.controllers;

import io.grpc.ManagedChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.aaf.finshop.proto.Principal;
import ru.aaf.finshop.proto.RemoteServiceGrpc;
import ru.otus.SomethingWrongException;
import ru.otus.domain.ClientView;

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
        var principal = Principal.newBuilder().setLogin(username).build();
        var response = stub.getProfileByPrincipal(principal);
        //        var clientId = new ProfileDto(response.getId(), response.getClientId(), null);
        if (response == null) {
            throw new SomethingWrongException("User not found");
        }

        return new RedirectView(String.format("/client/%s", response.getClientId()), true);
    }

    @GetMapping("/client/{clientId}")
    public String clientAccount(@PathVariable("clientId") String clientId, Model model) {
        logger.info("clientId = {}", clientId);

        var clientView = new ClientView("papa", "6501", null, null);
        model.addAttribute("clientView", clientView);

        return "creditClaim";
    }
}
