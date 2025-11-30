package ru.otus.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("java:S2142")
public class StoreServiceImpl implements StoreService {
    private static final Logger log = LoggerFactory.getLogger(StoreServiceImpl.class);

    long newValue;
    long currentValue;

    private final SeqStreamObserver observer;

    public StoreServiceImpl(SeqStreamObserver observer) {
        this.observer = observer;
        this.newValue = observer.getNewValue();
        this.currentValue = newValue;
    }

    @Override
    public void genSequence(long firsValue, long lastValue) {
        delay();

        for (long i = firsValue; i <= lastValue; i++) {
            // currentValue = [currentValue] + [ПОСЛЕДНЕЕ число от сервера] + 1
            var servedValue = observer.getNewValue();
            if (newValue < servedValue) {
                newValue = servedValue;
                currentValue += newValue + 1;
            } else {
                currentValue += 1;
            }
            log.info("currentValue = {}", currentValue);

            delay();
        }
    }

    private void delay() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            log.error("Thread interrupted!");
        }
    }
}
