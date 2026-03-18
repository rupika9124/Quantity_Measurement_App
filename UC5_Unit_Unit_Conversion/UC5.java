package UC5_Unit_Unit_Conversion;

public class UC5 {

    // Enum with conversion factors (to FEET)
    public enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(0.393701 / 12.0);

        private final double toFeetFactor;

        LengthUnit(double toFeetFactor) {
            this.toFeetFactor = toFeetFactor;
        }

        public double toFeet(double value) {
            return value * toFeetFactor;
        }

        public double fromFeet(double valueInFeet) {
            return valueInFeet / toFeetFactor;
        }
    }

    // Quantity class
    public static class QuantityLength {
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

        // Convert to another unit (instance method)
        public QuantityLength convertTo(LengthUnit targetUnit) {
            double base = this.unit.toFeet(this.value);
            double converted = targetUnit.fromFeet(base);
            return new QuantityLength(converted, targetUnit);
        }

        // Static conversion (as per UC)
        public static double convert(double value, LengthUnit from, LengthUnit to) {
            if (!Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid value");
            }
            if (from == null || to == null) {
                throw new IllegalArgumentException("Unit cannot be null");
            }

            double base = from.toFeet(value);
            return to.fromFeet(base);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            QuantityLength other = (QuantityLength) obj;

            double thisFeet = this.unit.toFeet(this.value);
            double otherFeet = other.unit.toFeet(other.value);

            return Double.compare(thisFeet, otherFeet) == 0;
        }

        @Override
        public String toString() {
            return value + " " + unit;
        }
    }

    // Overloaded methods (as required)

    // Method 1
    public static void demonstrateLengthConversion(double value, LengthUnit from, LengthUnit to) {
        double result = QuantityLength.convert(value, from, to);
        System.out.println(value + " " + from + " = " + result + " " + to);
    }

    // Method 2
    public static void demonstrateLengthConversion(QuantityLength q, LengthUnit to) {
        QuantityLength result = q.convertTo(to);
        System.out.println(q + " = " + result);
    }

    // Equality demo
    public static void demonstrateLengthEquality(QuantityLength q1, QuantityLength q2) {
        System.out.println(q1 + " == " + q2 + " → " + q1.equals(q2));
    }

    // Main method
    public static void main(String[] args) {

        try {
            // Conversion tests
            demonstrateLengthConversion(1.0, LengthUnit.FEET, LengthUnit.INCHES);
            demonstrateLengthConversion(1.0, LengthUnit.YARDS, LengthUnit.FEET);

            QuantityLength q = new QuantityLength(2.0, LengthUnit.YARDS);
            demonstrateLengthConversion(q, LengthUnit.INCHES);

            // Equality tests
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

            demonstrateLengthEquality(q1, q2);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
