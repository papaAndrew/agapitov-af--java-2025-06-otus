package ru.sinara.atm;

public class MappedCellFactory implements AtmCellFactory {
    @Override
    public MappedCell createMappedCell(Denomination denomination) {
        return new MappedCellImpl(denomination);
    }
}
