package UC1_Feet_Equality;

import java.util.InputMismatchException;
import java.util.Scanner;

public class UC1 {
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


    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        try{
            double v1=sc.nextDouble();
            double v2=sc.nextDouble();


            UC1.Feet f1=new Feet(v1);
            UC1.Feet f2=new Feet(v2);

            if(f1.equals(f2)) {
                System.out.println("Both value are same");
            }
            else {
                System.out.println("Values are different");
            }
        } catch (InputMismatchException e) {
            System.out.println("Enter numeric values only");
        }

    }
}
