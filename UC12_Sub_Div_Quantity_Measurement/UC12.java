package UC12_Sub_Div_Quantity_Measurement;

public class UC12 {
    public static void main(String[] args) {

        MeasureBox<LinearScale> l1 = new MeasureBox<>(10, LinearScale.FEET);
        MeasureBox<LinearScale> l2 = new MeasureBox<>(6, LinearScale.INCH);

        System.out.println(l1.subtract(l2));
        System.out.println(l1.subtract(l2, LinearScale.INCH));
        System.out.println(l2.subtract(l1));

        System.out.println(l1.divide(new MeasureBox<>(2, LinearScale.FEET)));
        System.out.println(new MeasureBox<>(24, LinearScale.INCH)
                .divide(new MeasureBox<>(2, LinearScale.FEET)));
    }
}

// ===== INTERFACE =====
interface Scale {
    double factor();

    default double toBase(double value) {
        return value * factor();
    }

    default double fromBase(double baseValue) {
        return baseValue / factor();
    }

    String nameLabel();
}

// ===== LENGTH =====
enum LinearScale implements Scale {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);

    private final double factor;

    LinearScale(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String nameLabel() {
        return name();
    }
}

// ===== WEIGHT =====
enum MassScale implements Scale {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    MassScale(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String nameLabel() {
        return name();
    }
}

// ===== VOLUME =====
enum LiquidScale implements Scale {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    LiquidScale(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String nameLabel() {
        return name();
    }
}

// ===== MAIN GENERIC CLASS =====
class MeasureBox<T extends Scale> {

    private final double value;
    private final T unit;
    private static final double EPS = 1e-6;

    public MeasureBox(double value, T unit) {
        if (unit == null)
            throw new IllegalArgumentException("Unit cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid value");

        this.value = value;
        this.unit = unit;
    }

    public MeasureBox<T> convertTo(T targetUnit) {
        double base = unit.toBase(value);
        double converted = targetUnit.fromBase(base);
        return new MeasureBox<>(round(converted), targetUnit);
    }

    public MeasureBox<T> add(MeasureBox<T> other) {
        return add(other, this.unit);
    }

    public MeasureBox<T> add(MeasureBox<T> other, T targetUnit) {
        validate(other, targetUnit);

        double sum = unit.toBase(value) + other.unit.toBase(other.value);
        double result = targetUnit.fromBase(sum);

        return new MeasureBox<>(round(result), targetUnit);
    }

    // ===== SUBTRACT =====
    public MeasureBox<T> subtract(MeasureBox<T> other) {
        return subtract(other, this.unit);
    }

    public MeasureBox<T> subtract(MeasureBox<T> other, T targetUnit) {
        validate(other, targetUnit);

        double resultBase = unit.toBase(value) - other.unit.toBase(other.value);
        double result = targetUnit.fromBase(resultBase);

        return new MeasureBox<>(round(result), targetUnit);
    }

    // ===== DIVIDE =====
    public double divide(MeasureBox<T> other) {
        validate(other);

        double base1 = unit.toBase(value);
        double base2 = other.unit.toBase(other.value);

        if (base2 == 0.0)
            throw new ArithmeticException("Divide by zero");

        return base1 / base2;
    }

    // ===== VALIDATION =====
    private void validate(MeasureBox<T> other) {
        if (other == null)
            throw new IllegalArgumentException("Null value");

        if (!unit.getClass().equals(other.unit.getClass()))
            throw new IllegalArgumentException("Different categories");
    }

    private void validate(MeasureBox<T> other, T targetUnit) {
        validate(other);

        if (targetUnit == null)
            throw new IllegalArgumentException("Target cannot be null");

        if (!unit.getClass().equals(targetUnit.getClass()))
            throw new IllegalArgumentException("Invalid target type");
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MeasureBox<?> other)) return false;

        if (!unit.getClass().equals(other.unit.getClass()))
            return false;

        double base1 = unit.toBase(value);
        double base2 = other.unit.toBase(other.value);

        return Math.abs(base1 - base2) < EPS;
    }

    @Override
    public String toString() {
        return "MeasureBox(" + value + ", " + unit.nameLabel() + ")";
    }
}