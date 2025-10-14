package ru.otus;

import java.util.List;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.helpers.MigrationsExecutorFlyway;
import ru.otus.hibernate.repository.DataTemplateHibernate;
import ru.otus.hibernate.repository.HibernateUtils;
import ru.otus.hibernate.sessionmanager.TransactionManagerHibernate;
import ru.otus.model.Address;
import ru.otus.model.Client;
import ru.otus.model.Phone;
import ru.otus.model.User;
import ru.otus.server.SecuredWebServer;
import ru.otus.server.WebServer;
import ru.otus.services.*;

public class MainApp {

    private static final int WEB_SERVER_PORT = 8080;
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";
    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    public static void main(String[] args) throws Exception {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");
        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var dbServiceClient = new DbServiceClientImpl(
                new TransactionManagerHibernate(HibernateUtils.buildSessionFactory(
                        configuration, Client.class, Address.class, Phone.class, User.class)),
                new DataTemplateHibernate<>(Client.class));
        addDefaultData(dbServiceClient);

        serverStart(dbServiceClient);
    }

    private static void serverStart(DBServiceClient dbServiceClient) throws Exception {
        TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);
        UserAuthService authService = new UserAuthServiceImpl();

        WebServer webServer = new SecuredWebServer(WEB_SERVER_PORT, authService, dbServiceClient, templateProcessor);

        webServer.start();
        webServer.join();
    }

    private static void addDefaultData(DBServiceClient dbServiceClient) {
        var admin = new Client(null, "admin");
        admin.setUser(new User(null, "admin"));
        findOrCreateClient(dbServiceClient, admin);

        var analyst =
                new Client(null, "Аналитик", new Address(null, "г.Москва"), List.of(new Phone(null, "13-555-22")));
        findOrCreateClient(dbServiceClient, analyst);

        var tester = new Client(
                null,
                "Тестировщик",
                new Address(null, "г.Екатеринбург"),
                List.of(new Phone(null, "88-777-66"), new Phone(null, "14-666-333")));
        findOrCreateClient(dbServiceClient, tester);
    }

    private static void findOrCreateClient(DBServiceClient dbServiceClient, Client client) {
        if (dbServiceClient.findClientByName(client.getName()).isEmpty()) {
            dbServiceClient.saveClient(client);
            log.info("Client created {} ", client);
            return;
        }
        log.info("Client already exists {}", client);
    }
}
