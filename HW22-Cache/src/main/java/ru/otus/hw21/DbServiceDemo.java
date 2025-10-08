package ru.otus.hw21;

import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.MyCache;
import ru.otus.hw21.core.repository.DataTemplateHibernate;
import ru.otus.hw21.core.repository.HibernateUtils;
import ru.otus.hw21.core.sessionmanager.TransactionManagerHibernate;
import ru.otus.hw21.crm.dbmigrations.MigrationsExecutorFlyway;
import ru.otus.hw21.crm.model.Address;
import ru.otus.hw21.crm.model.Client;
import ru.otus.hw21.crm.model.Phone;
import ru.otus.hw21.crm.service.DbServiceClientImpl;

public class DbServiceDemo {

    private static final Logger log = LoggerFactory.getLogger(DbServiceDemo.class);

    public static final String HIBERNATE_CFG_FILE = "hibernate.cfg.xml";

    public static void main(String[] args) {
        var configuration = new Configuration().configure(HIBERNATE_CFG_FILE);

        var dbUrl = configuration.getProperty("hibernate.connection.url");
        var dbUserName = configuration.getProperty("hibernate.connection.username");
        var dbPassword = configuration.getProperty("hibernate.connection.password");

        new MigrationsExecutorFlyway(dbUrl, dbUserName, dbPassword).executeMigrations();

        var sessionFactory =
                HibernateUtils.buildSessionFactory(configuration, Client.class, Address.class, Phone.class);

        var transactionManager = new TransactionManagerHibernate(sessionFactory);
        var clientTemplate = new DataTemplateHibernate<>(Client.class);
        var myCache = new MyCache<String, Client>();

        var dbServiceClient = new DbServiceClientImpl(transactionManager, clientTemplate, myCache);
        var clientFirst = dbServiceClient.saveClient(new Client("dbServiceFirst"));

        var clientFirstSelected = dbServiceClient
                .getClient(clientFirst.getId())
                .orElseThrow(() -> new RuntimeException("Client not found, id:" + clientFirst.getId()));

        log.info("clientFirstSelected:{}", clientFirstSelected);
    }
}
