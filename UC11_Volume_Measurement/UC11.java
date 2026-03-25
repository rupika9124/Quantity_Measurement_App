package UC11_Volume_Measurement;


public class UC11 {
    public static void main(String[] args) {

        MeasureValue<VolumeType> v1 = new MeasureValue<>(1.0, VolumeType.LITRE);
        MeasureValue<VolumeType> v2 = new MeasureValue<>(1000.0, VolumeType.MILLILITRE);
        MeasureValue<VolumeType> v3 = new MeasureValue<>(1.0, VolumeType.GALLON);

        System.out.println(v1.equals(v2)); // true
        System.out.println(v1.equals(v3)); // false

        System.out.println(v1.convertTo(VolumeType.MILLILITRE));
        System.out.println(v3.convertTo(VolumeType.LITRE));

        System.out.println(v1.add(v2));
        System.out.println(v1.add(v3, VolumeType.MILLILITRE));

        MeasureValue<DistanceType> d1 = new MeasureValue<>(1, DistanceType.FEET);
        System.out.println(v1.equals(d1)); // false
    }
}

// ===== COMMON CONTRACT =====
interface Convertible {
    double factor();

    default double toBase(double value) {
        return value * factor();
    }

    default double fromBase(double baseValue) {
        return baseValue / factor();
    }

    String label();
}

// ===== DISTANCE =====
enum DistanceType implements Convertible {
    FEET(1.0),
    INCH(1.0 / 12.0),
    YARD(3.0),
    CENTIMETER(1.0 / 30.48);

    private final double factor;

    DistanceType(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String label() {
        return name();
    }
}

// ===== MASS =====
enum MassType implements Convertible {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double factor;

    MassType(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String label() {
        return name();
    }
}

// ===== VOLUME =====
enum VolumeType implements Convertible {
    LITRE(1.0),
    MILLILITRE(0.001),
    GALLON(3.78541);

    private final double factor;

    VolumeType(double factor) {
        this.factor = factor;
    }

    public double factor() {
        return factor;
    }

    public String label() {
        return name();
    }
}

// ===== GENERIC VALUE CLASS =====
class MeasureValue<T extends Convertible> {

    private final double value;
    private final T type;
    private static final double EPS = 1e-6;

    public MeasureValue(double value, T type) {
        if (type == null)
            throw new IllegalArgumentException("Type cannot be null");
        if (!Double.isFinite(value))
            throw new IllegalArgumentException("Invalid number");

        this.value = value;
        this.type = type;
    }

    public MeasureValue<T> convertTo(T target) {
        double base = type.toBase(value);
        double converted = target.fromBase(base);
        return new MeasureValue<>(round(converted), target);
    }

    public MeasureValue<T> add(MeasureValue<T> other) {
        return add(other, this.type);
    }

    public MeasureValue<T> add(MeasureValue<T> other, T target) {
        if (other == null)
            throw new IllegalArgumentException("Null value");

        double sum = type.toBase(value) + other.type.toBase(other.value);
        double result = target.fromBase(sum);

        return new MeasureValue<>(round(result), target);
    }

    private double round(double val) {
        return Math.round(val * 100.0) / 100.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof MeasureValue<?> other)) return false;

        if (!type.getClass().equals(other.type.getClass()))
            return false;

        double base1 = type.toBase(value);
        double base2 = other.type.toBase(other.value);

        return Math.abs(base1 - base2) < EPS;
    }

    @Override
    public String toString() {
        return "MeasureValue(" + value + ", " + type.label() + ")";
    }
}
