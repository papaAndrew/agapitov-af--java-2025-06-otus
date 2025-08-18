package ru.sinara.atm;

import java.util.*;
import ru.sinara.atm.exception.AtmException;

public class AtmClientStation extends BasicAtmImpl implements AtmStation {

    Map<Denomination, Integer> cellMap = new EnumMap<>(Denomination.class);

    @Override
    public int findOrCreateCell(Denomination denomination) {
        if (cellMap.containsKey(denomination)) {
            return cellMap.get(denomination);
        }
        int address = addCell();
        cellMap.put(denomination, address);

        return address;
    }

    @Override
    public AtmStation addBills(Denomination denomination, int bills) throws AtmException {
        int address = findOrCreateCell(denomination);
        getCell(address).add(bills);
        return this;
    }

    @Override
    public int getBillsCount(Denomination denomination) throws AtmException {
        int address = resolveAddress(denomination);
        return getCell(address).getCount();
    }

    @Override
    public void checkCellExists(Denomination denomination) throws AtmException {
        if (!cellMap.containsKey(denomination)) {
            throw new AtmException("Denomination Cell not found");
        }
    }

    @Override
    public long getBalance() {
        return cellMap.entrySet().stream()
                .mapToLong(entry -> (long) entry.getKey().getValue()
                        * getCell(entry.getValue()).getCount())
                .sum();
    }

    private int resolveAddress(Denomination denomination) throws AtmException {
        checkCellExists(denomination);
        return cellMap.get(denomination);
    }
}
