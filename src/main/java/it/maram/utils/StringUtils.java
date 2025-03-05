package it.maram.utils;

import java.io.InputStream;

public class StringUtils {
    public static String readAll(InputStream is) throws Exception{
        int b;
        StringBuilder sb = new StringBuilder();
        while((b = is.read())!=-1){
            sb.append((char) b);
        }
        return sb.toString();
    }
    public static String fromBytes(byte[] bArr){
        StringBuilder sb = new StringBuilder();
        for(byte b : bArr){
            sb.append((char) b);
        }
        return sb.toString();
    }
    public static int getIndexOf(String value, String[] arr){
        for(int i = 0; i<arr.length; i++){
            String s = arr[i];
            if(s.equals(value)) return i;
        }
        return -1;
    }
}
