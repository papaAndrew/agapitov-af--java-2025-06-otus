package ru.sinara.atm;

public interface AtmCellFactory {
    MappedCell createMappedCell(Denomination denomination);
}
