package ru.sinara.atm;

public interface MappedCell extends AtmCell {

    Denomination getDenomination();

    long getSum();
}
