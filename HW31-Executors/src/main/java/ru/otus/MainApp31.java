package ru.otus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp31 {
    private static final Logger logger = LoggerFactory.getLogger(MainApp31.class);

    private static final Long DELAY = 1000L;

    private long prev = 0;
    private long next = 0;
    private long step = 1;

    public static void main(String[] args) {
        new MainApp31().go();
    }

    private boolean isSynchronized() {
        return prev == next;
    }

    private void boost() {
        this.next += step;
        logger.info("Boost: {}", next);

        if (next == 10L || (next + step) == 0L) {
            this.step = -step;
        }
    }

    private void sync() {
        this.prev = next;
        logger.info("Sync: {}", prev);
    }

    private void go() {
        new Thread(this::one, "thread-1").start();
        new Thread(this::two, "thread-2").start();
    }

    private synchronized void one() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (!isSynchronized()) {
                    this.wait();
                }
                boost();
                sleep();
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private synchronized void two() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (isSynchronized()) {
                    this.wait();
                }
                sync();
                notifyAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(DELAY);
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
