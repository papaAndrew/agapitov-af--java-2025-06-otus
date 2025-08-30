package ru.sinara.atm;

import java.util.Map;
import ru.sinara.atm.exception.AtmException;

public interface AtmStation {

    long getBalance();

    AtmStation addCash(Denomination denomination, int bills) throws AtmException;

    Map<Denomination, Integer> issueCash(long amount) throws AtmException;
}
