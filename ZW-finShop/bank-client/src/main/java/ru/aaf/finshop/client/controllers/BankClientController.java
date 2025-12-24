package ru.aaf.finshop.client.controllers;

import io.grpc.ManagedChannel;
import java.util.List;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import reactor.core.publisher.Flux;
import ru.aaf.finshop.client.SomethingWrongException;
import ru.aaf.finshop.client.domain.ClientView;
import ru.aaf.finshop.client.processor.DataProcessor;
import ru.aaf.finshop.proto.IdProto;
import ru.aaf.finshop.proto.NameProto;
import ru.aaf.finshop.proto.ProfileProto;
import ru.aaf.finshop.proto.RemoteServiceGrpc;

@SuppressWarnings({"java:S125", "java:S1172"})
@Controller
public class BankClientController {
    private static final Logger logger = LoggerFactory.getLogger(BankClientController.class);

    private final ManagedChannel channel;
    private final DataProcessor<StringValue> dataProcessor;

    public BankClientController(ManagedChannel channel, DataProcessor<StringValue> dataProcessor) {
        this.channel = channel;
        this.dataProcessor = dataProcessor;
    }

    @GetMapping("/login")
    public String login(Model model) {
        logger.info("Welcome to login page");
        //        var clientView = new ClientView("papa", "6501", null, null);
        //        model.addAttribute("clientView", clientView);
        return "loginPage";
    }

    @PostMapping("/login")
    public RedirectView loginPost(@RequestParam("username") String username) {
        logger.info("Somebody requests POST Login");

        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var profileName = NameProto.newBuilder().setName(username).build();
        var response = stub.getProfileByName(profileName);
        var authorized = response.getId() > 0;
        logger.info("response.authorized: {}", authorized);
        if (!authorized) {
            throw new SomethingWrongException("Profile not authorized");
        }

        return new RedirectView(String.format("/profile/%s", response.getId()), true);
    }

    @GetMapping("/profile/{profileId}")
    public String creditClaim(@PathVariable("profileId") String profileId, Model model) {
        logger.info("profileId: {}", profileId);

        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var response = stub.getProfileById(
                IdProto.newBuilder().setId(Long.parseLong(profileId)).build());

        var clientView = mapProfile(response);
        logger.info("clientView: {}", clientView);
        model.addAttribute("clientView", clientView);

        return "creditClaim";
    }

    @PostMapping("/client/save")
    public RedirectView saveClient(
            @RequestParam(value = "profileId") String profileId,
            @RequestParam(value = "clientName", required = false) String clientName,
            @RequestParam(value = "clientId", required = false) String clientId,
            @RequestParam(value = "passport", required = false) String passport) {
        var clientView = new ClientView(profileId, null, clientId, clientName, passport);
        logger.info("saveClient: {}", clientView);

        var profileProto = mapProfile(clientView);
        var stub = RemoteServiceGrpc.newBlockingStub(channel);
        var response = stub.saveProfile(profileProto);
        logger.info("redirect to clientId: {}", response.getId());

        return new RedirectView(String.format("/profile/%s", response.getId()), true);
    }

    @ResponseBody
    @GetMapping(value = "/stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<StringValue> data() {
        logger.info("request for data");
        var srcRequest = List.of(new StringValue("Одобрям :-)"));

        return dataProcessor.processFlux(srcRequest);
    }

    private ClientView mapProfile(ProfileProto profileProto) {
        var clientProto = profileProto.getClient();
        return new ClientView(
                String.valueOf(profileProto.getId()),
                profileProto.getName(),
                String.valueOf(clientProto.getId()),
                clientProto.getName(),
                clientProto.getPassport());
    }

    private ProfileProto mapProfile(ClientView clientView) {
        var builder = ProfileProto.newBuilder()
                .setId(keyStrToLong(clientView.profileId()))
                .setClient(mapClient(clientView));
        if (isDefined(clientView.profileName())) {
            builder.setName(clientView.profileName());
        }
        return builder.build();
    }

    private ProfileProto.Client.Builder mapClient(ClientView clientView) {
        var builder = ProfileProto.Client.newBuilder().setId(keyStrToLong(clientView.clientId()));
        if (isDefined(clientView.clientName())) {
            builder.setName(clientView.clientName());
        }
        if (isDefined(clientView.passport())) {
            builder.setPassport(clientView.passport());
            ;
        }
        return builder;
    }

    private long keyStrToLong(String strKey) {
        return strKey == null || strKey.isBlank() ? 0 : Long.parseLong(strKey);
    }

    private String strNonNull(String value) {
        return Objects.requireNonNullElse(value, "null");
    }

    private boolean isDefined(String value) {
        return !Objects.requireNonNullElse(value, "").isBlank();
    }
}
