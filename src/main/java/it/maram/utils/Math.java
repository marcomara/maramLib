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
}
