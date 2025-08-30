package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public class AtmCellImpl implements AtmCell {

    private int count = 0;

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public void add(int items) throws AtmException {
        if ((count + items) < 0) {
            throw new AtmException("Not enough items");
        }
        this.count += items;
    }
}
