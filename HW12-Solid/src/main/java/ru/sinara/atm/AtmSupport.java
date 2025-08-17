package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public interface AtmSupport {
    /**
     * Marks Cell as Denomination bills container
     * @param denomination own
     * @return address
     */
    int findOrCreateCell(Denomination denomination);

    /**
     * Add banknotes to Cell
     * @param denomination bill
     * @param bills count
     * @return this
     */
    AtmSupport addBills(Denomination denomination, int bills) throws AtmException;

    /**
     * Banknotes count
     * @param denomination spec
     * @return count
     */
    int getBillsCount(Denomination denomination) throws AtmException;

    /**
     * Throws AtmException if Cell not found
     * @param denomination criteria
     * @throws AtmException Cell not found
     */
    void checkCellExists(Denomination denomination) throws AtmException;

    /**
     * Balance
     * @return summa
     */
    long getBalance();
}
