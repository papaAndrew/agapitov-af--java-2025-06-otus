package ru.otus.service;

import io.grpc.stub.StreamObserver;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.ScoreMessage;

public class SeqStreamObserver implements StreamObserver<ScoreMessage> {
    private static final Logger log = LoggerFactory.getLogger(SeqStreamObserver.class);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    private long newValue = 0;
    private final CountDownLatch latch;

    public SeqStreamObserver(CountDownLatch latch) {
        this.latch = latch;
    }

    public long getNewValue() {
        lock.readLock().lock();
        try {
            return newValue;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public void onNext(ScoreMessage scoreMessage) {
        lock.writeLock().lock();
        try {
            this.newValue = scoreMessage.getNextValue();
        } finally {
            lock.readLock().unlock();
        }
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
