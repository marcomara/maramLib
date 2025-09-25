package it.maram.utils;

import java.io.InputStream;
import java.lang.Math;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class StringUtils {
    private static final char[] alphaNumChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final char[] alphaChars = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
    private static final char[] numChars = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final int anLength = 36;
    private static final int aLength = 26;
    private static final int nLength = 10;
    public static String readAll(InputStream is) throws Exception{
        int b;
        StringBuilder sb = new StringBuilder();
        while((b = is.read())!=-1){
            sb.append((char) b);
        }
        return sb.toString();
    }
    public static String fromBytes(byte[] bArr){
        return fromBytes(bArr, 0);
    }
    public static String fromBytes(byte[] bArr, int offset){
        StringBuilder sb = new StringBuilder();
        while(offset < bArr.length){
            sb.append((char) bArr[offset++]);
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
    public static String generateAlphaNumRandom(int length){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<length; i++){
            int car = rand.nextInt(nLength+aLength*2);
            if(car>aLength*2) sb.append(numChars[car-aLength*2]);
            else if (car>aLength) sb.append(Character.toUpperCase(alphaChars[car-aLength]));
            else sb.append(alphaChars[car]);
        }
        return sb.toString();
    }
    public static String generateAlphaRandom(int length){
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i<length; i++){
            int car = rand.nextInt(aLength*2);
            if(car>aLength) sb.append(Character.toUpperCase(alphaChars[car-aLength]));
            else sb.append(alphaChars[car]);
        }
        return sb.toString();
    }
    public static List<String> alphabetize(final List<String> list){
        List<String> sorted = new ArrayList<>();
        int max = list.size();
        byte min = Byte.MAX_VALUE;
        String tw = null;
        while(sorted.size()<max){
            for(String s : list){
                if (s.isEmpty()) continue;
                if((byte)s.charAt(0)<min){
                    min = (byte) s.charAt(0);
                    tw = s;
                }
                if((byte)s.charAt(0) == min){
                    assert tw != null;
                    int minL = (Math.min(s.length(), tw.length()));
                    for(int i = 1; i<minL; i++){
                        if(s.charAt(i)==tw.charAt(i)) continue;
                        else{
                            if((byte)s.charAt(i)<(byte)tw.charAt(i)){
                                tw = s;
                            }
                            break;
                        }
                    }
                }
            }
            list.remove(tw);
            if(tw!=null)sorted.add(tw);
            else max--;
            tw = null;
            min = Byte.MAX_VALUE;
        }
        return sorted;
    }
    public static String alphabetizeToString(final List<String> list){
        StringBuilder sb = new StringBuilder();
        for (String s : alphabetize(list)){
            sb.append(s).append(", ");
        }
        if (sb.toString().isEmpty()) return "";
        else return sb.substring(0, sb.toString().length()-2);
    }
    public static String alphabetizeToString(final String string, final String regex){
        StringBuilder sb = new StringBuilder();
        List<String> list = ListUtils.listOfStrings(string.split(regex));
        for (String s : alphabetize(list)){
            sb.append(s).append(", ");
        }
        if (sb.toString().isEmpty()) return "";
        else return sb.substring(0, sb.toString().length()-2);
    }
    public static boolean checkInteger(String str){
        try{
            int i = Integer.parseInt(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean checkDouble(String str){
        try{
            double i = Double.parseDouble(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean checkFloat(String str){
        try{
            float i = Float.parseFloat(str);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    public static boolean checkBoolean(String str){
        return (str != null) && (str.equalsIgnoreCase("true")||str.equalsIgnoreCase("false"));
    }
}
