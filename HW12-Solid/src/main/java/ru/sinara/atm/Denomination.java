package ru.sinara.atm;

/**
 * Enum representing Russian ruble (RUR) bill denominations.
 * The naming style follows P10, P50, P100, etc. pattern where
 * the number represents the denomination value in rubles.
 */
public enum Denomination {
    P10(10),
    P50(50),
    P100(100),
    P200(200),
    P500(500),
    P1000(1000),
    P2000(2000),
    P5000(5000);

    private final int value;

    /**
     * Constructor for Denomination enum.
     * @param value The monetary value of the denomination in rubles.
     */
    Denomination(int value) {
        this.value = value;
    }

    /**
     * Gets the monetary value of the denomination.
     * @return The value in rubles.
     */
    public int getValue() {
        return value;
    }

    /**
     * Returns a string representation of the denomination.
     * @return String in format "P{value}".
     */
    @Override
    public String toString() {
        return "P" + value;
    }

    /**
     * Gets all denominations in descending order of value.
     * @return Array of denominations from highest to lowest.
     */
    public static Denomination[] getDescendingOrder() {
        Denomination[] values = values();
        // Since enum values are declared in ascending order, we need to reverse
        for (int i = 0; i < values.length / 2; i++) {
            Denomination temp = values[i];
            values[i] = values[values.length - 1 - i];
            values[values.length - 1 - i] = temp;
        }
        return values;
    }
}
