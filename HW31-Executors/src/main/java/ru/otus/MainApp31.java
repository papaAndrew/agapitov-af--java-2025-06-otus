package ru.otus;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApp31 {
    private static final Logger logger = LoggerFactory.getLogger(MainApp31.class);

    private static final Long DELAY = 1000L;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();

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
        lock.writeLock().lock();
        try {
            this.next += step;
            logger.info("Boost: {}", next);

            if (next == 10L || (next + step) == 0L) {
                this.step = -step;
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void sync() {
        try {
            if (lock.readLock().tryLock(DELAY, TimeUnit.MILLISECONDS)) {
                try {
                    this.prev = next;
                    logger.info("Sync: {}", prev);
                } finally {
                    lock.readLock().unlock();
                }
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    private void go() {
        new Thread(this::one, "thread-1").start();
        new Thread(this::two, "thread-2").start();
    }

    private void one() {
        while (!Thread.currentThread().isInterrupted()) {
            if (isSynchronized()) {
                boost();
            }
            sleep();
        }
    }

    private void two() {
        while (!Thread.currentThread().isInterrupted()) {
            if (!isSynchronized()) {
                sync();
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
