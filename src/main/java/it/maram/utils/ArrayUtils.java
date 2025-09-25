package it.maram.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class ArrayUtils {
    public static char[] getFromBytes(final byte[] array){
        final char[] arr = new char[array.length];
        for(int i = 0; i<arr.length; i++){
            arr[i] = (char)array[i];
        }
        return arr;
    }
    public static byte[] getFromChars(final char[] array){
        final byte[] arr = new byte[array.length];
        for(int i = 0; i<arr.length; i++){
            arr[i] = (byte) array[i];
        }
        return arr;
    }
    public static <T> boolean compare(T[] array1, T[] array2){
        if(array1.length!=array2.length) return false;
        for(int i = 0; i<array1.length; i++){
            if(array1[i]!=array2[i]) return false;
        }
        return true;
    }
    public static <T extends Comparable<T>> boolean contains(T[] array, T object){
        for(T obj : array){
            if (obj.equals(object)) return true;
        }
        return false;
    }
    public static byte[] readAllBytes(InputStream is) throws IOException {
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int result;
        while((result = is.read())!=-1){
            bos.write(result);
        }
        return bos.toByteArray();
    }
    public static <T> T[] addToArrayTail(T[] array, T elementToAdd){
        Object[] newArray = new Object[array.length + 1];
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        newArray[newArray.length - 1] = elementToAdd;
        return (T[]) newArray;
    }
    public static <T> T[] addToArrayHead(T[] array, T elementToAdd){
        Object[] newArray = new Object[array.length + 1];
        newArray[0] = elementToAdd;
        for (int i = 0; i < array.length; i++){
            newArray[i + 1] = array[i];
        }
        return (T[]) newArray;
    }
    public static <T> T[] addAllToArrayTail(T[] array, T[] arrayToAdd){
        Object[] newArray = new Object[array.length + arrayToAdd.length];
        for (int i = 0; i < array.length; i++){
            newArray[i] = array[i];
        }
        for (int i = 0; i < arrayToAdd.length; i++){
            newArray[array.length + i] = arrayToAdd[i];
        }
        return (T[]) newArray;
    }
    public static <T> T[] addAllToArrayHead(T[] array, T[] arrayToAdd){
        Object[] newArray = new Object[array.length + arrayToAdd.length];
        for (int i = 0; i < arrayToAdd.length; i++){
            newArray[i] = arrayToAdd[i];
        }
        for (int i = 0; i < array.length; i++){
            newArray[arrayToAdd.length + i] = array[i];
        }
        return (T[]) newArray;
    }
    public static <T> int indexOf(T[] arr, T elem){
        for (int i = 0; i < arr.length; i++){
            if(arr[i] == elem) return i;
        }
        return -1;
    }
}
