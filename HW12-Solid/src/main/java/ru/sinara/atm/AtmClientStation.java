package ru.sinara.atm;

import java.util.*;
import java.util.stream.Collectors;
import ru.sinara.atm.exception.AtmException;

public class AtmClientStation extends BasicAtmImpl implements AtmStation {

    private final AtmCellFactory cellFactory;

    public AtmClientStation(AtmCellFactory cellFactory) {
        this.cellFactory = cellFactory;
    }

    private final Map<Denomination, MappedCell> cellMap = new EnumMap<>(Denomination.class);

    @Override
    public long getBalance() {
        return cellMap.values().stream().mapToLong(MappedCell::getSum).sum();
    }

    @Override
    public AtmStation addCash(Denomination denomination, int bills) throws AtmException {
        MappedCell cell = cellMap.get(denomination);
        if (cell == null) {
            cell = addCell(denomination);
        }
        cell.add(bills);
        return this;
    }

    @Override
    public Map<Denomination, Integer> issueCash(long amount) throws AtmException {
        Map<Denomination, Integer> resultMap = new EnumMap<>(Denomination.class);

        Deque<MappedCell> deque = cellMap.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .collect(Collectors.toCollection(ArrayDeque::new));

        Map<MappedCell, Integer> commitMap = calc(deque, amount);

        for (Map.Entry<MappedCell, Integer> entry : commitMap.entrySet()) {
            MappedCell cell = entry.getKey();
            int billsCount = entry.getValue();
            cell.add(-billsCount);
            resultMap.put(cell.getDenomination(), billsCount);
        }
        return resultMap;
    }

    private MappedCell addCell(Denomination denomination) {
        MappedCell cell = cellFactory.createMappedCell(denomination);
        this.addCell(cell);
        cellMap.put(denomination, cell);
        return cell;
    }

    private static Map<MappedCell, Integer> calc(Deque<MappedCell> deque, long amount) throws AtmException {
        Map<MappedCell, Integer> commitMap = new HashMap<>();

        long reminder = amount;
        while (reminder > 0) {
            MappedCell cell = deque.pollLast();
            if (cell == null) {
                throw new AtmException("Required amount unavailable");
            }
            int nom = cell.getDenomination().getValue();
            long cnt = reminder / nom;
            long givenCnt = Math.min(cnt, cell.getCount());
            long givenAmount = givenCnt * nom;
            reminder -= givenAmount;

            commitMap.put(cell, Math.toIntExact(givenCnt));
        }
        return commitMap;
    }
}
