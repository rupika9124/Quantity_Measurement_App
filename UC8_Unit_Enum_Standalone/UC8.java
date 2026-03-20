package UC8_Unit_Enum_Standalone;

// ===== STANDALONE ENUM =====
enum LengthUnit {

    FEET(1.0),
    INCHES(1.0 / 12.0),
    YARDS(3.0),
    CENTIMETERS(0.393701 / 12.0);

    private final double toFeetFactor;

    LengthUnit(double toFeetFactor) {
        this.toFeetFactor = toFeetFactor;
    }

    // Convert to base unit (FEET)
    public double convertToBaseUnit(double value) {
        return value * toFeetFactor;
    }

    // Convert from base unit (FEET)
    public double convertFromBaseUnit(double baseValue) {
        return baseValue / toFeetFactor;
    }
}

// ===== QUANTITY CLASS =====
class QuantityLength {

    private final double value;
    private final LengthUnit unit;

    public QuantityLength(double value, LengthUnit unit) {
        if (!Double.isFinite(value)) {
            throw new IllegalArgumentException("Invalid value");
        }
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null");
        }

        this.value = value;
        this.unit = unit;
    }

    // ===== CONVERSION =====
    public QuantityLength convertTo(LengthUnit targetUnit) {
        double base = unit.convertToBaseUnit(value);
        double result = targetUnit.convertFromBaseUnit(base);
        return new QuantityLength(result, targetUnit);
    }

    // ===== EQUALITY =====
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        QuantityLength other = (QuantityLength) obj;

        double thisBase = unit.convertToBaseUnit(value);
        double otherBase = other.unit.convertToBaseUnit(other.value);

        return Double.compare(thisBase, otherBase) == 0;
    }

    // ===== ADD (UC6) =====
    public QuantityLength add(QuantityLength other) {
        if (other == null) {
            throw new IllegalArgumentException("Other cannot be null");
        }

        double sumBase = unit.convertToBaseUnit(value)
                + other.unit.convertToBaseUnit(other.value);

        double result = unit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, unit);
    }

    // ===== ADD (UC7 with target unit) =====
    public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
        if (other == null || targetUnit == null) {
            throw new IllegalArgumentException("Invalid input");
        }

        double sumBase = unit.convertToBaseUnit(value)
                + other.unit.convertToBaseUnit(other.value);

        double result = targetUnit.convertFromBaseUnit(sumBase);

        return new QuantityLength(result, targetUnit);
    }

    @Override
    public String toString() {
        return value + " " + unit;
    }
}

// ===== MAIN CLASS =====
public class UC8 {

    public static void main(String[] args) {

        try {
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

            // Conversion
            System.out.println("Convert:");
            System.out.println(q1.convertTo(LengthUnit.INCHES));

            // Equality
            System.out.println("\nEquality:");
            System.out.println(q1.equals(q2)); // true

            // Addition UC6
            System.out.println("\nAddition (default):");
            System.out.println(q1.add(q2)); // 2 FEET

            // Addition UC7
            System.out.println("\nAddition (target unit):");
            System.out.println(q1.add(q2, LengthUnit.INCHES)); // 24 INCHES
            System.out.println(q1.add(q2, LengthUnit.YARDS));  // ~0.667 YARDS

            // More test
            QuantityLength q3 = new QuantityLength(36.0, LengthUnit.INCHES);
            QuantityLength q4 = new QuantityLength(1.0, LengthUnit.YARDS);

            System.out.println("\nMore:");
            System.out.println(q3.equals(q4)); // true
            System.out.println(q3.add(q4, LengthUnit.FEET)); // 6 FEET

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}