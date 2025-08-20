package ru.sinara.atm;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Random;
import java.util.random.RandomGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.sinara.atm.exception.AtmException;

public class MappedCellTest {

    private static final Random random = Random.from(RandomGenerator.getDefault());

    private AtmCellFactory factory;

    @BeforeEach
    public void reset() {
        factory = new MappedCellFactory();
    }

    private final List<Denomination> nominals = List.of(
            Denomination.P10,
            Denomination.P50,
            Denomination.P100,
            Denomination.P200,
            Denomination.P500,
            Denomination.P1000,
            Denomination.P2000,
            Denomination.P5000);

    @Test
    @DisplayName("New Cell created with Denomination")
    public void testCreateAndCetDenomination() {
        for (Denomination denomination : nominals) {
            assertThat(factory.createMappedCell(denomination).getDenomination()).isEqualTo(denomination);
        }
    }

    @Test
    @DisplayName("Test Cell getSum")
    public void testCellGetSum() throws AtmException {
        for (Denomination denomination : nominals) {
            MappedCell mappedCell = factory.createMappedCell(denomination);
            mappedCell.add(1);
            assertThat(mappedCell.getSum()).isEqualTo(denomination.getValue());
        }
    }
}
