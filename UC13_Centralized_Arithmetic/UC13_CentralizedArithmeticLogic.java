package UC13;

public class UC13_CentralizedArithmeticLogic {
    public static void main(String[] args) {
        Quantity<LengthUnit> l1 = new Quantity<>(10, LengthUnit.FEET);
        Quantity<LengthUnit> l2 = new Quantity<>(6, LengthUnit.INCH);
        System.out.println("Subtract (implicit unit): " + l1.subtract(l2));
        System.out.println("Subtract (target INCH): " + l1.subtract(l2, LengthUnit.INCH));
        System.out.println("Reverse Subtract: " + l2.subtract(l1));
        System.out.println("Divide (same unit): " + l1.divide(new Quantity<>(2, LengthUnit.FEET)));
        System.out.println("Divide (cross unit): " + new Quantity<>(24, LengthUnit.INCH).divide(new Quantity<>(2, LengthUnit.FEET)));
        Quantity<WeightUnit> w1 = new Quantity<>(10, WeightUnit.KILOGRAM);
        Quantity<WeightUnit> w2 = new Quantity<>(5000, WeightUnit.GRAM);
        System.out.println("Weight Add (GRAM): " + w1.add(w2, WeightUnit.GRAM));
        System.out.println("Weight Subtract: " + w1.subtract(w2));
        Quantity<VolumeUnit> v1 = new Quantity<>(5, VolumeUnit.LITRE);
        Quantity<VolumeUnit> v2 = new Quantity<>(2, VolumeUnit.LITRE);
        System.out.println("Volume Subtract (ML): " + v1.subtract(v2, VolumeUnit.MILLILITRE));
        System.out.println("Volume Divide: " + v1.divide(v2));
    }
}
