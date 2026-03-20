package UC7_Addition_Target_Unit;

public class UC7 {

    // ===== ENUM =====
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


        public QuantityLength convertTo(LengthUnit targetUnit) {
            double base = this.unit.toFeet(this.value);
            double converted = targetUnit.fromFeet(base);
            return new QuantityLength(converted, targetUnit);
        }

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

        public QuantityLength add(QuantityLength other) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }

            double sumFeet = this.unit.toFeet(this.value)
                    + other.unit.toFeet(other.value);

            double result = this.unit.fromFeet(sumFeet);

            return new QuantityLength(result, this.unit);
        }

        public QuantityLength add(QuantityLength other, LengthUnit targetUnit) {
            if (other == null) {
                throw new IllegalArgumentException("Other cannot be null");
            }
            if (targetUnit == null) {
                throw new IllegalArgumentException("Target unit cannot be null");
            }

            double sumFeet = this.unit.toFeet(this.value)
                    + other.unit.toFeet(other.value);

            double result = targetUnit.fromFeet(sumFeet);

            return new QuantityLength(result, targetUnit);
        }


        public static QuantityLength add(QuantityLength q1, QuantityLength q2, LengthUnit targetUnit) {
            return q1.add(q2, targetUnit);
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

        // ===== toString =====
        @Override
        public String toString() {
            return value + " " + unit;
        }
    }


    public static void main(String[] args) {

        try {
            QuantityLength q1 = new QuantityLength(1.0, LengthUnit.FEET);
            QuantityLength q2 = new QuantityLength(12.0, LengthUnit.INCHES);

            // UC6
            System.out.println("Default Add:");
            System.out.println(q1.add(q2)); // 2 FEET
            System.out.println(q2.add(q1)); // 24 INCHES

            // UC7
            System.out.println("\nExplicit Target Add:");
            System.out.println(q1.add(q2, LengthUnit.FEET));   // 2 FEET
            System.out.println(q1.add(q2, LengthUnit.INCHES)); // 24 INCHES
            System.out.println(q1.add(q2, LengthUnit.YARDS));  // ~0.667 YARDS

            QuantityLength q3 = new QuantityLength(36.0, LengthUnit.INCHES);
            QuantityLength q4 = new QuantityLength(1.0, LengthUnit.YARDS);

            System.out.println(q3.add(q4, LengthUnit.FEET)); // 6 FEET

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}