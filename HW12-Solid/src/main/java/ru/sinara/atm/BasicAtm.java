package ru.sinara.atm;

public interface BasicAtm {
    /**
     * Get ATM cells count
     * @return
     */
    int getCapacity();

    /**
     * Set initial cells count
     * @return
     */
    BasicAtm capacity(int cellsCount);

    AtmCell getCell(int cellId);
}
