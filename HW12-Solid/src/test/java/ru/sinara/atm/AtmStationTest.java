package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.atm.exception.AtmException;

public class AtmStationTest {

    public static final Denomination[] NOMINALS = new Denomination[] {
        Denomination.P5000,
        Denomination.P2000,
        Denomination.P1000,
        Denomination.P500,
        Denomination.P200,
        Denomination.P100,
        Denomination.P50,
        Denomination.P10
    };

    AtmStation atmStation;

    @BeforeEach
    public void reset() {
        atmStation = new AtmClientStation(new MappedCellFactory());
    }

    @Test
    @DisplayName("Balance of new Station equals with zero")
    public void testGetBalance() throws Exception {
        assertThat(atmStation.getBalance()).isEqualTo(0);
    }

    @Test
    @DisplayName("Add bills into Station")
    public void testAddCache() throws Exception {
        for (int i = 0; i < NOMINALS.length; i++) {
            atmStation.addCash(NOMINALS[i], i + 1);
        }
        assertThat(atmStation.getBalance()).isEqualTo(16030);
    }

    @Test
    @DisplayName("Empty ATM cannot issue any cash")
    public void testNoMoney() {
        assertThatExceptionOfType(AtmException.class).isThrownBy(() -> atmStation.issueCash(10));
        assertThat(atmStation.getBalance()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Put and get the same")
    public void testWhatInThatOut() throws AtmException {
        assertThatNoException()
                .isThrownBy(() -> atmStation.addCash(Denomination.P10, 2).issueCash(20));

        assertThat(atmStation.getBalance()).isEqualTo(0L);
    }

    @Test
    @DisplayName("Put a small, get a lot")
    public void testGreedy() {
        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atmStation.addCash(Denomination.P10, 3).issueCash(40));

        assertThat(atmStation.getBalance()).isEqualTo(30L);
    }

    @Test
    @DisplayName("Multiplicity")
    public void testMultiplicity() {
        assertThatExceptionOfType(AtmException.class)
                .isThrownBy(() -> atmStation.addCash(Denomination.P5000, 2).issueCash(10));

        assertThat(atmStation.getBalance()).isEqualTo(10000L);
    }

    @Test
    @DisplayName("Issue requested Amount")
    public void testIssueCashQuality() throws Exception {
        for (int i = 0; i < NOMINALS.length; i++) {
            atmStation.addCash(NOMINALS[i], i + 1);
        }

        Map<Denomination, Integer> issuedMoney = atmStation.issueCash(16000L);
        assertThat(atmStation.getBalance()).isEqualTo(30L);

        for (int i = 0; i < NOMINALS.length - 1; i++) {
            assertThat(issuedMoney.get(NOMINALS[i])).isEqualTo(i + 1);
        }
        assertThat(issuedMoney.get(Denomination.P10)).isEqualTo(5);
    }
}
