package UC6_Addition_Two_Length;

public class UC6 {

    enum LengthUnit {
        FEET(1.0),
        INCHES(1.0 / 12.0),
        YARDS(3.0),
        CENTIMETERS(1.0 / 30.48);

        private final double toFeet;

        LengthUnit(double toFeet) {
            this.toFeet = toFeet;
        }

        public double toBase(double value) {
            return value * toFeet;
        }

        public double fromBase(double valueInFeet) {
            return valueInFeet / toFeet;
        }
    }

    static class Quantity {
        private final double value;
        private final LengthUnit unit;

        public Quantity(double value, LengthUnit unit) {
            if (unit == null || !Double.isFinite(value)) {
                throw new IllegalArgumentException("Invalid input");
            }
            this.value = value;
            this.unit = unit;
        }

        private double toFeet() {
            return unit.toBase(value);
        }

        public Quantity add(Quantity other) {
            if (other == null) {
                throw new IllegalArgumentException("Null operand");
            }

            double sumInFeet = this.toFeet() + other.toFeet();

            double resultValue = this.unit.fromBase(sumInFeet);

            return new Quantity(resultValue, this.unit);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) return true;
            if (obj == null || getClass() != obj.getClass()) return false;

            Quantity other = (Quantity) obj;

            return Math.abs(this.toFeet() - other.toFeet()) < 0.0001;
        }

        @Override
        public String toString() {
            return "Quantity(" + value + ", " + unit + ")";
        }
    }

    public static void main(String[] args) {
        Quantity q1 = new Quantity(1.0, LengthUnit.FEET);
        Quantity q2 = new Quantity(12.0, LengthUnit.INCHES);

        System.out.println(q1.add(q2)); // Quantity(2.0, FEET)

        Quantity q3 = new Quantity(12.0, LengthUnit.INCHES);
        Quantity q4 = new Quantity(1.0, LengthUnit.FEET);

        System.out.println(q3.add(q4)); // Quantity(24.0, INCHES)
    }
}
