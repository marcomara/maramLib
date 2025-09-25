package it.maram.utils;

import java.util.*;

public class ListUtils {
    public static List<String> listOfStrings(String ... s){
        List<String> tr = new ArrayList<>();
        for(String o : s){
            tr.add(o);
        }
        return tr;
    }
    public static String[] asStringArray(List<String> list){
        String[] str = new String[list.size()];
        for(int i =0; i<list.size(); i++){
            str[i]=list.get(i);
        }
        return str;
    }
    public static String[] asStringArray(Set<String> set){
        String[] str = new String[set.size()];
        int i = 0;
        for(String s : set){
            str[i]=s;
            i++;
        }
        return str;
    }
    public static String[] concatToArray(String[] array, String... toAdd){
        String[] str = new String[array.length+toAdd.length];
        int i = 0;
        for(int j = 0; j<array.length;j++,i++ ){
            str[i]=array[j];
        }
        for(int j = 0; j<toAdd.length; j++, i++){
            str[i]= toAdd[j];
        }
        return str;
    }
    public static <T extends NamedClasses> boolean contains(List<T> list, String name){
        for(T elem : list){
            if(elem.getName().equals(name)) return true;
        }
        return false;
    }
    public static <T extends NamedClasses> T get(Iterable<T> list, String name){
        for(T elem : list) if(elem.getName().equals(name)) return elem;
        return null;
    }
    public static <T extends NamedClasses> T get(List<T> list, String name){
        for(T elem : list) if(elem.getName().equals(name)) return elem;
        return null;
    }
}
