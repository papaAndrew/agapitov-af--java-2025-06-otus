package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class BasicAtmTest {
    private static final Random random = Random.from(RandomGenerator.getDefault());

    private static int genInitialCells() {
        return random.nextInt(8, 16);
    }

    private BasicAtm basicAtm;

    @BeforeEach
    public void reset() {
        basicAtm = new BasicAtmImpl();
    }

    @Test
    @DisplayName("AddCell returns address of Cell")
    public void testAddCell() {
        int initialCells = genInitialCells();
        for (int i = 0; i < initialCells; i++) {
            assertThat(basicAtm.addCell(new AtmCellImpl())).isEqualTo(i);
        }
    }
}
