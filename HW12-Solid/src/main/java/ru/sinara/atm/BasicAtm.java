package ru.sinara.atm;

public interface BasicAtm {

    /**
     * Add new Cell
     * @return address
     */
    int addCell();


    /**
     * AtmCell access by Id
     * @param cellId index
     * @return cell by index or null
     */
    AtmCell getCell(int cellId);
}
