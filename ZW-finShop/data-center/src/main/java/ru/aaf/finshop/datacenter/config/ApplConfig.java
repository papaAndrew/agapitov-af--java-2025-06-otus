package ru.aaf.finshop.datacenter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Configuration
public class ApplConfig {

    private static final int THREAD_POOL_SIZE = 1;

    //    @Bean
    //    public ScheduledExecutorService scheduledExecutorService() {
    //        return Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    //    }

    @Bean
    public Scheduler workerPool() {
        return Schedulers.newParallel("processor-thread", THREAD_POOL_SIZE);
    }
}
