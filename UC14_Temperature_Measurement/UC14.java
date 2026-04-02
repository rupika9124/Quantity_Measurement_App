package UC14_Temperature_Measurement;

import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;

public class UC14 {

    public static void main(String[] args) {

        Quantity1<DistanceUnit> l1 = new Quantity1(10, DistanceUnit.FEET);
        Quantity1<DistanceUnit> l2 = new Quantity1(6, DistanceUnit.INCH);

        System.out.println("Subtract: " + l1.subtract(l2));

        Quantity1<MassUnit> w1 = new Quantity1(10, MassUnit.KILOGRAM);
        Quantity1<MassUnit> w2 = new Quantity1(5000, MassUnit.GRAM);

        System.out.println("Weight Add: " + w1.add1(w2, MassUnit.GRAM));

        Quantity1<LiquidUnit> v1 = new Quantity1(5, LiquidUnit.LITRE);
        Quantity1<LiquidUnit> v2 = new Quantity1(2, LiquidUnit.LITRE);

        System.out.println("Volume Divide: " + v1.divide(v2));

        Quantity1<ThermalUnit> t1 = new Quantity1(0.0, ThermalUnit.CELSIUS);
        Quantity1<ThermalUnit> t2 = new Quantity1(32.0, ThermalUnit.FAHRENHEIT);

        System.out.println("Temp Equality: " + t1.equals(t2));
        System.out.println("Convert: " + t1.convertTo(ThermalUnit.FAHRENHEIT));

        try {
            t1.add1(new Quantity1(50.0, ThermalUnit.CELSIUS));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

@FunctionalInterface
interface ArithmeticSupport {
    boolean allowed();
}

interface UnitSpec {

    double factor();

    default double toBase(double value) {
        return value * factor();
    }

    default double fromBase(double base) {
        return base / factor();
    }

    String symbol();

    ArithmeticSupport support = () -> true;

    default boolean supportsArithmetic() {
        return support.allowed();
    }

    default void validateOperation(String operation) {
    }
}

enum DistanceUnit implements UnitSpec {
    FEET(1.0), INCH(1.0 / 12.0), YARD(3.0), CM(1.0 / 30.48);

    private final double f;

    DistanceUnit(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum MassUnit implements UnitSpec {
    KILOGRAM(1.0), GRAM(0.001), POUND(0.453592);

    private final double f;

    MassUnit(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum LiquidUnit implements UnitSpec {
    LITRE(1.0), MILLILITRE(0.001), GALLON(3.78541);

    private final double f;

    LiquidUnit(double f) {
        this.f = f;
    }

    public double factor() {
        return f;
    }

    public String symbol() {
        return name();
    }
}

enum ThermalUnit implements UnitSpec {

    CELSIUS(
            c -> c,
            c -> c
    ),

    FAHRENHEIT(
            f -> (f - 32) * 5 / 9,
            c -> (c * 9 / 5) + 32
    ),

    KELVIN(
            k -> k - 273.15,
            c -> c + 273.15
    );

    private final Function<Double, Double> toBase;
    private final Function<Double, Double> fromBase;
    private final ArithmeticSupport support = () -> false;

    ThermalUnit(Function<Double, Double> toBase, Function<Double, Double> fromBase) {
        this.toBase = toBase;
        this.fromBase = fromBase;
    }

    @Override
    public double toBase(double value) {
        return toBase.apply(value);
    }

    @Override
    public double fromBase(double base) {
        return fromBase.apply(base);
    }

    @Override
    public String symbol() {
        return name();
    }

    @Override
    public boolean supportsArithmetic() {
        return support.allowed();
    }

    @Override
    public void validateOperation(String operation) {
        throw new UnsupportedOperationException("Temperature does not support " + operation);
    }

    @Override
    public double factor() {
        return 1.0;
    }
}

enum OperationType {

    ADD1((a, b) -> a + b),

    SUBTRACT((a, b) -> a - b),

    DIVIDE((a, b) -> {
        if (Math.abs(b) < 1e-9)
            throw new ArithmeticException();
        return a / b;
    });

    private final DoubleBinaryOperator op;

    OperationType(DoubleBinaryOperator op) {
        this.op = op;
    }

    double apply(double a, double b) {
        return op.applyAsDouble(a, b);
    }
}

class Quantity1<U extends UnitSpec> {

    private final double value;
    private final U unit;
    private static final double EPS = 1e-6;

    public Quantity1(double value, U unit) {
        if (unit == null) throw new IllegalArgumentException();
        if (!Double.isFinite(value)) throw new IllegalArgumentException();
        this.value = value;
        this.unit = unit;
    }

    public Quantity1<U> convertTo(U target) {
        double base = unit.toBase(value);
        double converted = target.fromBase(base);
        return new Quantity1<>(round(converted), target);
    }

    public Quantity1<U> add1(Quantity1<U> other) {
        return add1(other, this.unit);
    }

    public Quantity1<U> add1(Quantity1<U> other, U target) {
        validate(other, target, true);
        double base = compute(other, OperationType.ADD1);
        return new Quantity1<>(round(target.fromBase(base)), target);
    }

    public Quantity1<U> subtract(Quantity1<U> other) {
        return subtract(other, this.unit);
    }

    public Quantity1<U> subtract(Quantity1<U> other, U target) {
        validate(other, target, true);
        double base = compute(other, OperationType.SUBTRACT);
        return new Quantity1<>(round(target.fromBase(base)), target);
    }

    public double divide(Quantity1<U> other) {
        validate(other, null, false);
        return compute(other, OperationType.DIVIDE);
    }

    private void validate(Quantity1<U> other, U target, boolean needTarget) {
        if (other == null) throw new IllegalArgumentException();
        if (!unit.getClass().equals(other.unit.getClass())) throw new IllegalArgumentException();
        if (!Double.isFinite(value) || !Double.isFinite(other.value)) throw new IllegalArgumentException();
        if (needTarget) {
            if (target == null) throw new IllegalArgumentException();
            if (!unit.getClass().equals(target.getClass())) throw new IllegalArgumentException();
        }
    }

    private double compute(Quantity1<U> other, OperationType op) {
        unit.validateOperation(op.name());
        other.unit.validateOperation(op.name());
        double a = unit.toBase(value);
        double b = other.unit.toBase(other.value);
        return op.apply(a, b);
    }

    private double round(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Quantity1<?> q)) return false;
        if (!unit.getClass().equals(q.unit.getClass())) return false;
        double a = unit.toBase(value);
        double b = q.unit.toBase(q.value);
        return Math.abs(a - b) < EPS;
    }

    @Override
    public int hashCode() {
        double base = unit.toBase(value);
        return Double.hashCode(Math.round(base / EPS));
    }

    @Override
    public String toString() {
        return "Quantity(" + value + ", " + unit.symbol() + ")";
    }
}
