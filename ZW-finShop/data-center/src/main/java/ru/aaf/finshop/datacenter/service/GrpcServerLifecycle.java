package ru.aaf.finshop.datacenter.service;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import ru.aaf.finshop.datacenter.exception.GrpcServerException;

@Component
public class GrpcServerLifecycle implements SmartLifecycle {

    private Server server;

    private final int port;
    private final RemoteDataService remoteDataService;

    public GrpcServerLifecycle(@Value("${grpc.server.port}") int port, RemoteDataService remoteDataService) {
        this.port = port;
        this.remoteDataService = remoteDataService;
    }

    @Override
    public void start() {
        try {
            server = ServerBuilder.forPort(port)
                    .addService(remoteDataService)
                    .build()
                    .start();
        } catch (IOException e) {
            throw new GrpcServerException(e);
        }

        System.out.println("gRPC server started on port {}");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            server.shutdown();
        }));
    }

    @Override
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    @Override
    public boolean isRunning() {
        return server != null && !server.isShutdown();
    }
}
