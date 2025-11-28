package ru.otus.model;

public class NumRange {

    private long firstValue;
    private long lastValue;

    public NumRange(long firstValue, long lastValue) {
        this.firstValue = firstValue;
        this.lastValue = lastValue;
    }

    public long getFirstValue() {
        return firstValue;
    }

    public void setFirstValue(long firstValue) {
        this.firstValue = firstValue;
    }

    public NumRange firstValue(long firstValue) {
        this.firstValue = firstValue;
        return this;
    }

    public long getLastValue() {
        return lastValue;
    }

    public void setLastValue(long lastValue) {
        this.lastValue = lastValue;
    }

    public NumRange lastValue(long lastValue) {
        this.lastValue = lastValue;
        return this;
    }
}
