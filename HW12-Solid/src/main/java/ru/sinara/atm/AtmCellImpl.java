package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public class AtmCellImpl implements AtmCell {

    private int count = 0;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void add(int bills) {
        this.count += bills;
    }

    @Override
    public void remove(int bills) {
        if (bills > count) {
            throw new AtmException("Cannot remove this number of items");
        }
        this.count -= bills;
    }

    @Override
    public void clear() {
        this.count = 0;
    }
}
