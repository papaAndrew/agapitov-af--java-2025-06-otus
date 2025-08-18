package ru.sinara.atm;

import ru.sinara.atm.exception.AtmException;

public interface MappedAtm {
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
    MappedAtm addBills(Denomination denomination, int bills) throws AtmException;

    /**
     * Banknotes count
     * @param denomination spec
     * @return count
     */
    int getBillsCount(Denomination denomination) throws AtmException;


}
