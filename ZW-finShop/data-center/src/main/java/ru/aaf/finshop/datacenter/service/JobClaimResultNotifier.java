package ru.aaf.finshop.datacenter.service;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JobClaimResultNotifier {
    private static final Logger log = LoggerFactory.getLogger(JobClaimResultNotifier.class);
    private final BusProducer busProducer;
    private final ScheduledExecutorService executor;

    public JobClaimResultNotifier(BusProducer busProducer, ScheduledExecutorService executor) {
        this.busProducer = busProducer;
        this.executor = executor;
    }

    @Autowired
    public void execute() {
        log.info("execute claims ...");
        executor.scheduleAtFixedRate(() -> busProducer.send(makeMessage()), 0, 60, TimeUnit.SECONDS);
    }

    private String makeMessage() {
        var msg = "Scheduled message";
        log.info("makeMessage: {}", msg);
        return msg;
    }
}
