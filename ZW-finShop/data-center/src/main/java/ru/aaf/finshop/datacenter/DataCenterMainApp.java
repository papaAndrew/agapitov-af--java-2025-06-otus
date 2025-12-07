package ru.aaf.finshop.datacenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SuppressWarnings({"java:S1068", "java:S1854", "java:S125", "java:S1481"})
@SpringBootApplication
public class DataCenterMainApp {
    private static final Logger log = LoggerFactory.getLogger(DataCenterMainApp.class);

    public static void main(String[] args) {
        SpringApplication.run(DataCenterMainApp.class, args);

        //        var remoteService = new RemoteDataService(new DataServiceImpl());
        //
        //        var server =
        //                ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();
        //        server.start();
        //        log.info("server waiting for client connections...");
        //        server.awaitTermination();
    }
}
