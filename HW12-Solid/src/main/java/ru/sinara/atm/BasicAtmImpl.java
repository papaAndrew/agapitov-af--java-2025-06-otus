package ru.sinara.atm;

import java.util.ArrayList;
import java.util.List;

public class BasicAtmImpl implements BasicAtm {

    private final List<AtmCell> cells = new ArrayList<>();

    @Override
    public int addCell(AtmCell atmCell) {
        cells.add(atmCell);
        return cells.size() - 1;
    }
}
