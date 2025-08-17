package ru.sinara.atm;

public interface AtmSupport {
    AtmSupport mapCell(int cellId, Denomination denomination);
    AtmCell[] getCells(Denomination denomination);
}
