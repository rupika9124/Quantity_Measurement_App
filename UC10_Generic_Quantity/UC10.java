package UC10_Generic_Quantity;

public class UC10 {
    public static void main(String[] args) {

        Measure<Length> l1 = new Measure<>(1, Length.FEET);
        Measure<Length> l2 = new Measure<>(12, Length.INCH);

        System.out.println(l1.equals(l2));
        System.out.println(l1.convertTo(Length.INCH));
        System.out.println(l1.add(l2, Length.FEET));

        Measure<Weight> w1 = new Measure<>(1, Weight.KILOGRAM);
        Measure<Weight> w2 = new Measure<>(1000, Weight.GRAM);

        System.out.println(w1.equals(w2));
        System.out.println(w1.convertTo(Weight.GRAM));
        System.out.println(w1.add(w2, Weight.KILOGRAM));

        System.out.println(l1.equals(w1)); // false
    }
}

// ===== COMMON UNIT INTERFACE =====
interface Unit {
    double getConversionFactor();

    default double toBase(double value) {
        return value * getConversionFactor();
    }

    default double fromBase(double baseValue) {
        return baseValue / getConversionFactor();
    }

    String getName();
}

// ===== LENGTH UNITS =====
enum Length implements Unit {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);

    private final double factor;

    Length(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public String getName() {
        return name();
    }
}

// ===== WEIGHT UNITS =====
enum Weight implements Unit {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    Weight(double factor) {
        this.factor = factor;
    }

    public double getConversionFactor() {
        return factor;
    }

    public String getName() {
        return name();
    }
}

// ===== GENERIC MEASURE CLASS =====
class Measure<U extends Unit> {

    private final double value;
    private final U unit;
    private static final double EPSILON = 1e-6;

    public Measure(double value, U unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public Measure<U> convertTo(U targetUnit) {
        double base = unit.toBase(value);
        double converted = targetUnit.fromBase(base);
        return new Measure<>(round(converted), targetUnit);
    }

    public Measure<U> add(Measure<U> other) {
        return add(other, this.unit);
    }

    public Measure<U> add(Measure<U> other, U targetUnit) {
        if (other == null)
            throw new IllegalArgumentException("Null measurement");

        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        double result = targetUnit.fromBase(sum);

        return new Measure<>(round(result), targetUnit);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Measure<?> other)) return false;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double base1 = unit.toBase(value);
        double base2 = other.unit.toBase(other.value);

        return Math.abs(base1 - base2) < EPSILON;
    }

    @Override
    public int hashCode() {
        double base = unit.toBase(value);
        return Double.hashCode(Math.round(base / EPSILON));
    }

    @Override
    public String toString() {
        return "Measure(" + value + ", " + unit.getName() + ")";
    }
}
