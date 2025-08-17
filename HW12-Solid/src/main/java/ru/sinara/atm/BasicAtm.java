package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public interface BasicAtm {
    /**
     * Get ATM cells capacity
     * @return max count
     */
    int getCapacity();

    /**
     * Set initial cells capacity
     * @return this
     */
    BasicAtm capacity(int cellsCount) throws AtmException;

    /**
     * Clear capacity
     * @return this
     */
    BasicAtm reset();

    /**
     * AtmCell access by Id
     * @param cellId index
     * @return cell by index or null
     */
    AtmCell getCell(int cellId);
}
