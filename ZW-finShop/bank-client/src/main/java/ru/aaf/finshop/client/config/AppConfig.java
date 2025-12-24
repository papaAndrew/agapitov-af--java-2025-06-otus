package ru.aaf.finshop.client.config;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    private static final int THREAD_POOL_SIZE = 2;

    @Bean
    public ManagedChannel dataCenterClient(@Value("${datacenter.url}") String url) {
        return ManagedChannelBuilder.forTarget(url).usePlaintext().build();
    }

    //    @Bean
    //    public ObjectMapper objectMapper() {
    //        return new ObjectMapper();
    //    }
    //
    //    @Bean(destroyMethod = "close")
    //    public NioEventLoopGroup eventLoopGroup() {
    //        return new NioEventLoopGroup(THREAD_POOL_SIZE, new ThreadFactory() {
    //            private final AtomicLong threadIdGenerator = new AtomicLong(0);
    //
    //            @Override
    //            public Thread newThread(@Nullable Runnable task) {
    //                return new Thread(task, "client-thread-" + threadIdGenerator.incrementAndGet());
    //            }
    //        });
    //    }
    //
    //    @Bean
    //    public ReactorResourceFactory reactorResourceFactory(NioEventLoopGroup eventLoopGroup) {
    //        var resourceFactory = new ReactorResourceFactory();
    //        resourceFactory.setLoopResources(b -> eventLoopGroup);
    //        resourceFactory.setUseGlobalResources(false);
    //        return resourceFactory;
    //    }
    //
    //    @Bean
    //    public ReactorClientHttpConnector reactorClientHttpConnector(ReactorResourceFactory resourceFactory) {
    //        return new ReactorClientHttpConnector(resourceFactory, mapper -> mapper);
    //    }

    //    @Bean
    //    public Scheduler timer() {
    //        return Schedulers.immediate().newParallel("processor-thread", 2);
    //    }
}
