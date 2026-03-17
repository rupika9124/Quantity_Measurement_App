package UC4_Extended_Unit_Support;

public class UC4 {
    public static boolean demonstrateLengthEquality(Length l1, Length l2){
        return l1.equals(l2);
    }

    public static boolean demonstrateLengthComparison(double a, Length.LengthUnit unit1, double b, Length.LengthUnit unit2){
        Length l1 = new Length(a, unit1);
        Length l2 = new Length(b, unit2);

        return demonstrateLengthEquality(l1, l2);
    }

    public static void main(String[] args) {
        System.out.println(demonstrateLengthComparison(3, Length.LengthUnit.FEET, 1, Length.LengthUnit.YARDS));
    }
}

class Length{
    private final double v;
    private final LengthUnit unit;

    public enum LengthUnit{
        FEET(12),
        INCHES(1),
        YARDS(36),
        CENTIMETERS(0.393701);

        private final double conversionFactor;

        LengthUnit(double conversionFactor){
            this.conversionFactor=conversionFactor;
        }

        public double getConversionFactor(){
            return this.conversionFactor;
        }
    }

    Length(double v, LengthUnit unit){
        this.v=v;
        this.unit=unit;
    }

    private double convertToBaseUnit(){
        return v * unit.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;

        if(obj == null || getClass() != obj.getClass()) return false;

        Length l = (Length) obj;
        return Double.compare(this.convertToBaseUnit(), l.convertToBaseUnit()) == 0;
    }

}