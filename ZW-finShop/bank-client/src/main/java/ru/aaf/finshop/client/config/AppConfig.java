package ru.aaf.finshop.client.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings({"java:S125", "java:S1068"})
@Configuration
public class AppConfig {

    @Bean
    public ManagedChannel dataCenterClient(@Value("${datacenter.url}") String url) {
        return ManagedChannelBuilder.forTarget(url).usePlaintext().build();
    }
}
