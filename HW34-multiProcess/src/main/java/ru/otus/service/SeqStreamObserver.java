package ru.otus.service;

import io.grpc.stub.StreamObserver;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.ScoreMessage;

public class SeqStreamObserver implements StreamObserver<ScoreMessage> {
    private static final Logger log = LoggerFactory.getLogger(SeqStreamObserver.class);

    private long newValue = 0;
    private final CountDownLatch latch;

    public SeqStreamObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    public long getNewValue() {
        return newValue;
    }

    @Override
    public void onNext(ScoreMessage scoreMessage) {
        this.newValue = scoreMessage.getNextValue();
        log.info("newValue = {}", this.newValue);
    }

    @Override
    public void onError(Throwable throwable) {
        log.error("Server Error", throwable);
    }

    @Override
    public void onCompleted() {
        log.info("Request completed");
        latch.countDown();
    }
}
