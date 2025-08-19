package ru.sinara.atm;

public class MappedCellImpl extends AtmCellImpl implements MappedCell {

    private final Denomination denomination;

    public MappedCellImpl(Denomination denomination) {
        this.denomination = denomination;
    }

    @Override
    public Denomination getDenomination() {
        return denomination;
    }

    @Override
    public long getSum() {
        return (long) getCount() * denomination.getValue();
    }
}
