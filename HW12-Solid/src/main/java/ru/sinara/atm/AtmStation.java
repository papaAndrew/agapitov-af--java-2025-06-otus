package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

import java.util.Map;

public interface AtmStation {

    /**
     * Balance
     * @return summa
     */
    long getBalance();

    /**
     * Add banknotes to Cell
     * @param denomination bill
     * @param bills count
     * @return this
     */
    AtmStation addCache(Denomination denomination, int bills) throws AtmException;

    /**
     *
     * @param amount
     * @return
     */
    Map<Denomination, Integer> giveCache(int amount);
}
