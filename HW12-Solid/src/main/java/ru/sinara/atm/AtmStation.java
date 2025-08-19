package ru.sinara.atm;

import java.util.Map;
import ru.sinara.atm.exception.AtmException;

public interface AtmStation {

    long getBalance();

    AtmStation addCache(Denomination denomination, int bills) throws AtmException;

    Map<Denomination, Integer> giveCache(long amount) throws AtmException;
}
