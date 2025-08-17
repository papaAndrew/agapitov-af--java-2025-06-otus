package ru.sinara.atm;

import java.util.ArrayList;
import java.util.List;
import ru.sinara.atm.exception.AtmException;

public class BasicAtmImpl implements BasicAtm {

    private final List<AtmCell> cells = new ArrayList<>();

    // here atmCells factory could be injected
    private static AtmCell createAtmCell() {
        return new AtmCellImpl();
    }

    @Override
    public int getCapacity() {
        return cells.size();
    }

    @Override
    public BasicAtm capacity(int maxCount) {
        if (getCapacity() > 0) {
            throw new AtmException("ATM already initialised");
        }
        for (int i = 0; i < maxCount; i++) {
            cells.add(createAtmCell());
        }
        return this;
    }

    @Override
    public BasicAtm reset() {
        cells.clear();
        return this;
    }

    @Override
    public AtmCell getCell(int cellId) {
        try {
            return cells.get(cellId);
        } catch (IndexOutOfBoundsException e) {
            return null;
        }
    }
}
