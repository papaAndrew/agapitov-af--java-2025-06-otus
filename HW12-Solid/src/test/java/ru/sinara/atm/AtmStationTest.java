package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AtmStationTest {
    //    public static final int DEFAULT_CAPACITY = 8;

    private static final Random random = Random.from(RandomGenerator.getDefault());

    AtmStation atmStation;

    @BeforeEach
    public void reset() {
        atmStation = new AtmClientStation(null);
    }

    @Test
    @DisplayName("Add bills into Cell")
    public void testAddBills() throws Exception {
        //        int[] items = {
        //            random.nextInt(0, 1000), random.nextInt(0, 1000), random.nextInt(0, 1000),
        //        };
        //        atmStation
        //                .addBills(Denomination.P100, items[0])
        //                .addBills(Denomination.P500, items[1])
        //                .addBills(Denomination.P2000, items[2]);
        //        assertThat(atmStation.getBillsCount(Denomination.P100)).isEqualTo(items[0]);
        //        assertThat(atmStation.getBillsCount(Denomination.P500)).isEqualTo(items[1]);
        //        assertThat(atmStation.getBillsCount(Denomination.P2000)).isEqualTo(items[2]);
    }

    @Test
    @DisplayName("Balance")
    public void testBalance() throws Exception {
        //        atmStation
        //                .addBills(Denomination.P100, 100)
        //                .addBills(Denomination.P500, 100)
        //                .addBills(Denomination.P2000, 100);
        //        assertThat(atmStation.getBalance()).isEqualTo(10_000L + 50_000L + 200_000L);
    }
}
