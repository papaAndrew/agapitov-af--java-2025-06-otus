package ru.sinara.atm;

import java.util.ArrayList;
import java.util.List;

public class BasicAtmImpl implements BasicAtm {

    private final List<AtmCell> cells = new ArrayList<>();

    // here atmCells factory could be injected
    private static AtmCell createAtmCell() {
        return new AtmCellImpl();
    }

    public int getCapacity() {
        return cells.size();
    }

    @Override
    public int addCell() {
        cells.add(createAtmCell());
        return cells.size() - 1;
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
