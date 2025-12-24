package ru.aaf.finshop.datacenter.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplConfig {

    private static final int THREAD_POOL_SIZE = 1;

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    }
}
