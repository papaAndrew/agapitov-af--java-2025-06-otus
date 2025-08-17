package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BasicAtmTest {
    private static final Random random = Random.from(RandomGenerator.getDefault());

    private static int genInitialCells() {
        return random.nextInt(8,16);
    }
    private static int genBillsCount() {
        return random.nextInt(1,10000);
    }

    BasicAtm basicAtm;

    @BeforeEach
    public void reset() {
        basicAtm = new BasicAtmImpl();
    }

    @Test
    @DisplayName("Cells count is equal to configured value")
    public void testCellsCount() {
        int initialCells = genInitialCells();
        basicAtm.cellsCount(initialCells);

        assertThat(basicAtm.getCellsCount()).isEqualTo(initialCells);
    }

    @Test
    @DisplayName("Add banknotes into the Cell and show totals")
    public void testChargeCell() {
        basicAtm.cellsCount(genInitialCells());
        for (int idx = 0; idx < basicAtm.getCellsCount(); idx++) {
            int bills = genBillsCount();
            basicAtm.chargeCell(idx, bills);

            assertThat(basicAtm.showCell(idx)).isEqualTo(bills);
        }
    }
}
