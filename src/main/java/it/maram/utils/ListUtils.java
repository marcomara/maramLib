package it.maram.utils;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {
    public static List<String> listOfStrings(String ... s){
        List<String> tr = new ArrayList<>();
        for(String o : s){
            tr.add(o);
        }
        return tr;
    }
}
