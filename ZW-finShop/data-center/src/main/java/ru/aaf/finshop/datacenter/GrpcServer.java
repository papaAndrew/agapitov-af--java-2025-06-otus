package ru.aaf.finshop.datacenter;

import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.aaf.finshop.datacenter.service.RemoteDataService;

import java.io.IOException;

@SuppressWarnings({"java:S1068", "java:S1854", "java:S125", "java:S1481"})
public class GrpcServer {
    public static final int SERVER_PORT = 8190;
    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {

        var remoteService = new RemoteDataService();

                var server =
                        ServerBuilder.forPort(SERVER_PORT).addService(remoteService).build();
                server.start();
                log.info("server waiting for client connections...");
                server.awaitTermination();
    }
}
