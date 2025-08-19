package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public interface AtmCell {

    int getCount();

    void add(int billCount) throws AtmException;
}
