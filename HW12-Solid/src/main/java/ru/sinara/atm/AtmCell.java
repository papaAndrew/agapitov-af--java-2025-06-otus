package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public interface AtmCell {
    /**
     * Current banknotes count in Cell
     * @return count
     */
    int getCount();

    /**
     * Replenish Cell
     * @param bills count
     */
    void add(int bills) throws AtmException;

    //    /**
    //     * Decrease bills from Cell
    //     * @param bills count
    //     * @throws AtmException no such items
    //     */
    //    void remove(int bills) throws AtmException;

    /**
     * Remove all banknotes and clear Cell
     */
    void clear();
}
