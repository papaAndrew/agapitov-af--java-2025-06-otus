package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.atm.exception.AtmException;

public class BasicAtmTest {
    private static final Random random = Random.from(RandomGenerator.getDefault());

    private static int genInitialCells() {
        return random.nextInt(8, 16);
    }

    private static int genBillsCount() {
        return random.nextInt(1, 10000);
    }

    BasicAtm basicAtm;

    @BeforeEach
    public void reset() {
        basicAtm = new BasicAtmImpl();
    }

    @Test
    @DisplayName("Capacity is equal to configured value. Cannot change capacity")
    public void testCellsCount() {
        int initialCells = genInitialCells();
        basicAtm.capacity(initialCells);

        assertThat(basicAtm.getCapacity()).isEqualTo(initialCells);

        assertThatExceptionOfType(AtmException.class).isThrownBy(() -> basicAtm.capacity(initialCells + 1));
    }

    @Test
    @DisplayName("Capacity can be reset")
    public void testReinitAtm() {
        int capacity1 = genInitialCells();
        basicAtm.capacity(capacity1);
        assertThat(basicAtm.getCapacity()).isEqualTo(capacity1);

        basicAtm.reset();
        assertThat(basicAtm.getCapacity()).isEqualTo(0);

        int capacity2 = genInitialCells();
        basicAtm.capacity(capacity2);
        assertThat(basicAtm.getCapacity()).isEqualTo(capacity2);

        basicAtm.reset().capacity(capacity1);
        assertThat(basicAtm.getCapacity()).isEqualTo(capacity1);
    }

    @Test
    @DisplayName("Access to Cell")
    public void testGetCell() {
        int capacity = genInitialCells();
        basicAtm.capacity(capacity);
        assertThat(basicAtm.getCell(capacity - 1)).isInstanceOf(AtmCell.class);
        assertThat(basicAtm.getCell(capacity)).isNull();
    }
}
