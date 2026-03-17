package UC3_Generic_Quantity;

public class UC3 {
    public static boolean demonstrateLengthEquality(Length l1, Length l2){
        return l1.equals(l2);
    }

    public static void demonstrateFeetEquality(){
        Length f1 = new Length(5, Length.LengthUnit.FEET);
        Length f2 = new Length(5, Length.LengthUnit.FEET);

        System.out.println("Equal or not - " + demonstrateLengthEquality(f1, f2));
    }

    public static void demonstrateInchesEquality(){
        Length i1 = new Length(12, Length.LengthUnit.INCHES);
        Length i2 = new Length(12, Length.LengthUnit.INCHES);

        System.out.println("Equal or not - " + demonstrateLengthEquality(i1, i2));
    }

    public static void demonstrateFeetInchesComparison(){
        Length l1 = new Length(1, Length.LengthUnit.FEET);
        Length l2 = new Length(12, Length.LengthUnit.INCHES);

        System.out.println("Feet & Inches comparison - " + demonstrateLengthEquality(l1, l2));
    }

    public static void main(String[] args) {
        demonstrateFeetEquality();
        demonstrateInchesEquality();
        demonstrateFeetInchesComparison();
    }
}

class Length{
    private final double v;
    private final LengthUnit unit;

    public enum LengthUnit{
        FEET(12.0),
        INCHES(1.0);

        private final double conversionFactor;

        LengthUnit(double conversionFactor){
            this.conversionFactor = conversionFactor;
        }

        public double getConversionFactor(){
            return conversionFactor;
        }
    }

    public Length(double v, LengthUnit unit){
        if(unit == null){
            throw new IllegalArgumentException("Unit cannot be null");
        }
        if(Double.isNaN(v)){
            throw new IllegalArgumentException("Invalid value");
        }
        this.v=v;
        this.unit=unit;
    }

    private double convertToBaseUnit(){
        return v * unit.getConversionFactor();
    }

    @Override
    public boolean equals(Object obj){
        if(this==obj) return true;

        if(obj==null || getClass()!=obj.getClass()) return false;

        Length l=(Length)obj;
        return Double.compare(this.convertToBaseUnit(), l.convertToBaseUnit())==0;
    }
}
