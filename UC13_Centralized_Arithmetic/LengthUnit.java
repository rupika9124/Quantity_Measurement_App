package UC13;

public enum LengthUnit implements IMeasurable{
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);
    private final double factor;
    LengthUnit(double factor) {
        this.factor = factor;
    }
    @Override
    public double getConversionFactor() {
        return factor;
    }
    @Override
    public String getUnitName() {
        return name();
    }

}
