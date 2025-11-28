package ru.otus;

import io.grpc.ServerBuilder;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.RemoteStoreService;

@SuppressWarnings({"java:S1854", "java:S125", "java:S1130", "java:S2094", "java:S1481", "java:S106"})
public class GrpcServer {
    public static final int SERVER_PORT = 8190;
    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteService = new RemoteStoreService();

        var server =
                ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();
        server.start();
        log.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
