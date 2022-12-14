import functions.*;
import functions.basic.*;

import java.io.*;

public class Main{
    public static void main(String[] args) {
        double leftX = 0.0;
        double rightX = 2.0;
        double[] v = {0.0, 1.0, 2.0};

        double leftX2 = 0.0;
        double rightX2 = 2.0;
        double[] v2 = {0.0, 1.0, 3};

        try {
            TabulatedFunction arr = new ArrayTabulatedFunction(leftX, rightX, v);
            ArrayTabulatedFunction arr1 = new ArrayTabulatedFunction(leftX, rightX, v);
            TabulatedFunction arr2 = new LinkedListTabulatedFunction(leftX2, rightX2, v2);

            System.out.println(arr.toString());
            System.out.println(arr2.toString());
            System.out.println();

            System.out.println("arr = arr1? : " + arr.equals(arr1));
            System.out.println("arr = arr2? : " + arr.equals(arr2));
            System.out.println();

            FunctionPoint p = new FunctionPoint(1.0, 2.5);
            System.out.println("Hash code point : " + p.hashCode());
            System.out.println("Hash code arr : " + arr.hashCode());
            System.out.println("Hash code arr1 : " + arr1.hashCode());
            System.out.println("Hash code arr2 : " + arr2.hashCode());
            System.out.println();

            arr1 = new ArrayTabulatedFunction(leftX, rightX + 0.000001, v);
            System.out.println("Hash code changed arr1 : " + arr1.hashCode());




            try{
                Object tmp = null;
                tmp = arr1.clone2();
                System.out.println("Equals");
                System.out.println(arr1.equals(tmp));
            }catch (Exception e){
                e.printStackTrace();
            }

            //Object arr4 = arr2.clone();
            //System.out.println(arr.equals(arr3));
            //System.out.println(arr2.equals(arr4));
            //System.out.println();

            arr = new ArrayTabulatedFunction(leftX, rightX + 0.8, v);
            arr2 = new ArrayTabulatedFunction(leftX, rightX + 0.3, v);
            //System.out.println(arr.equals(arr3));
            //System.out.println(arr2.equals(arr4));
        } catch (InappropriateFunctionPointException e) {
            throw new RuntimeException(e);
        }
    }
}
