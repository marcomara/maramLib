package it.maram.utils;

public class Math {

    public static int getInt(double d){
        int a = (int) d;
        if(d-a>=0D){
            return a;
        }else return a-1;
    }
    public static int roundUp(double d){
        if((d-getInt(d))>0D) return getInt(d)+1;
        else return getInt(d);
    }
    public static String getPercentage(double number, int decimals){
        int temp = getInt(java.lang.Math.pow(10, decimals + 1) * number);
        int lastValue = temp - ((temp / 10) * 10);
        if (lastValue >= 5){
            temp = temp - lastValue +10;
        }
        double temp1 = temp / java.lang.Math.pow(10, decimals + 1);
        String temp2 = String.valueOf(temp1);
        return temp2 + "%";
    }
}
