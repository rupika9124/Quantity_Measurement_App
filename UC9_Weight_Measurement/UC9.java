package UC9_Weight_Measurement;

// ================= LENGTH ENUM =================
enum LengthUnitEnum {
    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnitEnum(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// ================= LENGTH CLASS =================
class LengthQuantity {

    private final double value;
    private final LengthUnitEnum unit;

    public LengthQuantity(double value, LengthUnitEnum unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public LengthQuantity convertTo(LengthUnitEnum targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);
        return new LengthQuantity(result, targetUnit);
    }

    public LengthQuantity add(LengthQuantity other) {
        double sum = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
        return new LengthQuantity(unit.convertFromBaseUnit(sum), unit);
    }

    public LengthQuantity add(LengthQuantity other, LengthUnitEnum targetUnit) {
        double sum = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
        return new LengthQuantity(targetUnit.convertFromBaseUnit(sum), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        LengthQuantity other = (LengthQuantity) obj;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

// ================= WEIGHT ENUM =================
enum WeightUnitEnum {
    KILOGRAM(1.0),
    GRAM(0.001),
    POUND(0.453592);

    private final double toKgFactor;

    WeightUnitEnum(double toKgFactor) {
        this.toKgFactor = toKgFactor;
    }

    public double convertToBaseUnit(double value) {
        return value * toKgFactor;
    }

    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toKgFactor;
    }
}

// ================= WEIGHT CLASS =================
class WeightQuantity {

    private final double value;
    private final WeightUnitEnum unit;

    public WeightQuantity(double value, WeightUnitEnum unit) {
        if (!Double.isFinite(value)) throw new IllegalArgumentException("Invalid value");
        if (unit == null) throw new IllegalArgumentException("Unit cannot be null");

        this.value = value;
        this.unit = unit;
    }

    public WeightQuantity convertTo(WeightUnitEnum targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);
        return new WeightQuantity(result, targetUnit);
    }

    public WeightQuantity add(WeightQuantity other) {
        double sum = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
        return new WeightQuantity(unit.convertFromBaseUnit(sum), unit);
    }

    public WeightQuantity add(WeightQuantity other, WeightUnitEnum targetUnit) {
        double sum = unit.convertToBaseUnit(value) + other.unit.convertToBaseUnit(other.value);
        return new WeightQuantity(targetUnit.convertFromBaseUnit(sum), targetUnit);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        WeightQuantity other = (WeightQuantity) obj;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

// ================= MAIN =================
public class UC9 {

    public static void main(String[] args) {

        try {
            LengthQuantity l1 = new LengthQuantity(1.0, LengthUnitEnum.FEET);
            LengthQuantity l2 = new LengthQuantity(12.0, LengthUnitEnum.INCHES);

            System.out.println("Length:");
            System.out.println(l1.equals(l2));
            System.out.println(l1.add(l2));
            System.out.println(l1.add(l2, LengthUnitEnum.YARDS));

            WeightQuantity w1 = new WeightQuantity(1.0, WeightUnitEnum.KILOGRAM);
            WeightQuantity w2 = new WeightQuantity(1000.0, WeightUnitEnum.GRAM);

            System.out.println("\nWeight:");
            System.out.println(w1.equals(w2));
            System.out.println(w1.add(w2));
            System.out.println(w1.add(w2, WeightUnitEnum.GRAM));

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}