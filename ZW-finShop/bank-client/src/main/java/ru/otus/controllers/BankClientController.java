package ru.otus.controllers;

import java.math.BigDecimal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.util.HtmlUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.domain.ClientView;
import ru.otus.domain.Message;

@Controller
public class BankClientController {
    private static final Logger logger = LoggerFactory.getLogger(BankClientController.class);


    @GetMapping("/client/{clientId}")
    public String clientAccount(@PathVariable("clientId") String clientId, Model model) {
        logger.info("clientId = {}", clientId);

        var clientView = new ClientView("papa", "6501", null, null);
        model.addAttribute("clientView", clientView);

        return "creditClaim";
    }


}
