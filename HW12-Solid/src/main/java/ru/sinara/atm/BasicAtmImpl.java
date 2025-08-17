package ru.sinara.atm;

import java.util.ArrayList;
import java.util.List;

public class BasicAtmImpl implements BasicAtm {

    private final List<Integer> cells = new ArrayList<>();


    @Override
    public int getCapacity() {
        return cells.size();
    }

    @Override
    public BasicAtm capacity(int maxCount) {
        if (getCapacity() > 0) {
            throw new ArrayStoreException("This operation needs the ATM reset first");
        }
        for (int idx = 0; idx < maxCount) {

        }
        return this;
    }

    @Override
    public void chargeCell(int cellId, int banknotes) {}

    @Override
    public void dischargeCell(int cellId, int banknotes) {}

    @Override
    public void dischargeCell(int cellId) {}

    @Override
    public void discharge() {}

    @Override
    public int showCell(int cellId) {
        return cells.get(cellId);
    }
}
