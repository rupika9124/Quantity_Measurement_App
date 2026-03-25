package UC13;

public class Quantity<U extends IMeasurable> {
    private final double v;
    private final U unit;

    private static final double EPSILON = 1e-6;

    public Quantity(double v, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        this.v = v;
        this.unit = unit;
    }

    public Quantity<U> convertTo(U targetUnit) {
        double base = unit.convertToBaseUnit(v);
        double converted = targetUnit.convertFromBaseUnit(base);
        return new Quantity<>(round(converted), targetUnit);
    }

    public Quantity<U> add(Quantity<U> other) {
        return add(other, this.unit);
    }
    public Quantity<U> add(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.ADD);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(round(result), targetUnit);
    }
    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }
    public Quantity<U> subtract(Quantity<U> other) {
        return subtract(other, this.unit);
    }
    public Quantity<U> subtract(Quantity<U> other, U targetUnit) {
        validateArithmeticOperands(other, targetUnit, true);
        double baseResult = performBaseArithmetic(other, ArithmeticOperation.SUBTRACT);
        double result = targetUnit.convertFromBaseUnit(baseResult);
        return new Quantity<>(round(result), targetUnit);
    }
    public double divide(Quantity<U> other) {
        validateArithmeticOperands(other, null, false);
        return performBaseArithmetic(other, ArithmeticOperation.DIVIDE);
    }

    private void validateArithmeticOperands(Quantity<U> other, U targetUnit, boolean isTargetRequired) {
        if (other == null) {
            throw new IllegalArgumentException("Other quantity cannot be null");
        }
        if (!this.unit.getClass().equals(other.unit.getClass())) {
            throw new IllegalArgumentException("Different measurement categories");
        }
        if (!Double.isFinite(this.v) || !Double.isFinite(other.v)) {
            throw new IllegalArgumentException("Invalid numeric value");
        }
        if (isTargetRequired) {
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }
            if (!this.unit.getClass().equals(targetUnit.getClass())) {
                throw new IllegalArgumentException("Target unit must match category");
            }
        }
    }

    private double performBaseArithmetic(Quantity<U> other, ArithmeticOperation operation) {
        double baseThis = this.unit.convertToBaseUnit(this.v);
        double baseOther = other.unit.convertToBaseUnit(other.v);
        return operation.compute(baseThis, baseOther);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity<?> other)) return false;
        if (!unit.getClass().equals(other.unit.getClass()))
            return false;
        double base1 = unit.convertToBaseUnit(v);
        double base2 = other.unit.convertToBaseUnit(other.v);
        return Math.abs(base1 - base2) < EPSILON;
    }
    @Override
    public int hashCode() {
        double base = unit.convertToBaseUnit(v);
        return Double.hashCode(Math.round(base / EPSILON));
    }
    @Override
    public String toString() {
        return "Quantity(" + v + ", " + unit.getUnitName() + ")";
    }
}
