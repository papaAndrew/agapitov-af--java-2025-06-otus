package ru.otus.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.view.ClientView;

@Controller
public class ClientController {
    private static final Logger log = LoggerFactory.getLogger(ClientController.class);

    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping({"/", "/client"})
    public String clientsListView(Model model) {
        List<Client> clients = clientService.findAll();
        model.addAttribute(
                "clients", clientService.findAll().stream().map(ClientView::new).toList());
        model.addAttribute("clientView", new ClientView());
        return "clientsList";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(ClientView clientView) {
        log.info("Save client from {}", clientView);
        clientService.saveClient(clientView.createClient());
        return new RedirectView("/", true);
    }
}
