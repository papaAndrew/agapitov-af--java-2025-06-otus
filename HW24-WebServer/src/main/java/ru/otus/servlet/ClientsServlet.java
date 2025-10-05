package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ru.otus.services.DBServiceClient;
import ru.otus.services.TemplateProcessor;

@SuppressWarnings({"java:S1989"})
public class ClientsServlet extends HttpServlet {

    private static final String PAGE_CLIENT_LIST_TEMPLATE = "clients.html";

    private final transient DBServiceClient serviceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("clients", serviceClient.findAll());

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(PAGE_CLIENT_LIST_TEMPLATE, paramsMap));
    }
}
