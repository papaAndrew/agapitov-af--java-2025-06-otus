package ru.aaf.finshop.datacenter.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GrpcServerConfig {

    @Value("${grpc.server.port}")
    private int grpcPort;

    //    @Bean
    //    public GrpcServerLifecycle grpcServerLifecycle(RemoteDataService remoteDataService) {
    //        return new GrpcServerLifecycle(grpcPort, remoteDataService);
    //    }
}
