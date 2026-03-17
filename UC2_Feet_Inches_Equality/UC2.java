package UC2_Feet_Inches_Equality;

import java.util.*;

public class UC2 {
    public static class Feet{
        private final double value;
        public Feet(double value){
            this.value=value;
        }

        @Override
        public boolean equals(Object obj){
            if(this==obj) return true;
            if(obj==null) return false;
            if(getClass()!=obj.getClass()) return false;
            Feet t=(Feet) obj;
            return Double.compare(this.value,t.value)==0;

        }
    }

     public static class Inches{
         private final double value;
         public Inches(double value){
             this.value=value;
         }

         @Override
         public boolean equals(Object obj) {
             if (this == obj) return true;
             if (obj == null || getClass() != obj.getClass()) return false;
             Inches inches = (Inches) obj;
             return Double.compare(this.value, inches.value) == 0;
         }
     }

    public static boolean compareFeet(double v1, double v2) {
        Feet f1 = new Feet(v1);
        Feet f2 = new Feet(v2);
        return f1.equals(f2);
    }

    public static boolean compareInches(double v1, double v2) {
        Inches i1 = new Inches(v1);
        Inches i2 = new Inches(v2);
        return i1.equals(i2);
    }

    static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try{
            double v1=sc.nextDouble();
            double v2=sc.nextDouble();

            char check = sc.next().charAt(0);

            switch(check){
                case 'f':
                    System.out.println(compareFeet(v1,v2));
                    break;

                case 'i':
                    System.out.println(compareInches(v1,v2));
                    break;

                default:
                    System.out.println("invalid comparison");
            }


        } catch (InputMismatchException e) {
            System.out.println("Enter numeric values only");
        }
    }
}
