package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.atm.exception.AtmException;

public class AtmCellTest {

    private static final Random random = Random.from(RandomGenerator.getDefault());

    private AtmCell atmCell;

    @BeforeEach
    public void reset() {
        atmCell = new AtmCellImpl();
    }

    @Test
    @DisplayName("New Cell contains a zero bills")
    public void testInit() {
        assertThat(atmCell.getCount()).isEqualTo(0);
    }

    @Test
    @DisplayName("Count changed after once insert banknotes then clear")
    public void testAddOnce() throws AtmException {
        for (int i = 0; i < 10; i++) {
            int cnt = random.nextInt(0, 1000);
            atmCell.add(cnt);
            assertThat(atmCell.getCount()).isEqualTo(cnt);

            atmCell.clear();
            assertThat(atmCell.getCount()).isEqualTo(0);
        }
    }

    @Test
    @DisplayName("Count changed after incremental insert banknotes")
    public void testAddInc() throws AtmException {
        for (int i = 0; i < 10; i++) {
            atmCell.add(i);
        }
        assertThat(atmCell.getCount()).isEqualTo(45);
    }

    @Test
    @DisplayName("Count changed after decremental remove banknotes")
    public void testRemoveDec() throws AtmException {
        atmCell.add(56);
        for (int i = 10; i > 0; i--) {
            atmCell.add(-i);
        }
        assertThat(atmCell.getCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Exception throws if to try remove unacceptable count")
    public void tesUnacceptableRemoving() throws AtmException {
        atmCell.add(5);
        assertThatExceptionOfType(AtmException.class).isThrownBy(() -> atmCell.add(-6));
    }
}
