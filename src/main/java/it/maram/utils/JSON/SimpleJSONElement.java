package it.maram.utils.JSON;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SimpleJSONElement {
    private Map<String, String> lines;
    public SimpleJSONElement(){
        lines = new HashMap<>();
    }
    public boolean addValue(String key, String value){
        if (!lines.containsKey(key)){
            lines.put(key, value);
            return true;
        }else return false;
    }
    @Nullable
    public String getValue(String key){
        return lines.getOrDefault(key, null);
    }
    /**
     * @return true if the combination of key-oldValue exists and get modified<br\>
     *         false if the combination of key-oldValue doesn't exist
     * @param key the name of the value to modify
     * @param newValue the value that will replace the previous
     * */
    public boolean modifyValue(String key, String newValue){
        if (lines.containsKey(key)){
            lines.replace(key, newValue);
            return true;
        }else return false;
    }
    /**
     * @return true if the combination of key-oldValue exists and get modified,
     * in case it doesn't exist this method will create one
     *
     * @param key the name of the value to modify
     * @param newValue the value that will replace the previous
     * */
    public boolean modifyValueSafe(String key, String newValue){
        if (lines.containsKey(key)){
            lines.replace(key, newValue);
        }else addValue(key,newValue);
        return true;
    }
    /**
     * @return true if the combination of key-oldValue exists and get modified<br\>
     *         false if the combination of key-oldValue doesn't exist
     * @param key the name of the value to modify
     * @param oldValue the value that needs to be modified
     * @param newValue the value that will replace the previous
     * */
    public boolean modifyValue(String key, String oldValue, String newValue){
        if (lines.containsKey(key) && lines.get(key).equals(oldValue)){
            lines.replace(key, oldValue, newValue);
            return true;
        }else return false;
    }
    public String serialize(){
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        for (Map.Entry<String, String> entry : lines.entrySet()){
            sb.append("\t\"").append(entry.getKey()).append("\" : \"").append(entry.getValue()).append("\"\n");
        }
        sb.append("}\n");
        return sb.toString();
    }
    @Nullable
    public static SimpleJSONElement deserialize(String s){
        s=s.trim();
        if (!(s.startsWith("{")&&s.endsWith("}"))) return null;
        String[] lines = s.split("\n");
        SimpleJSONElement element = new SimpleJSONElement();
        for (String line : lines){
            if (line.contains(" : ")){
                String[] arr = line.split(" : ");
                if (arr.length == 2){
                    element.addValue(arr[0].substring(1, arr[0].length()-2), arr[1].substring(1, arr[1].length()-1));
                }
            }
        }
        return element;
    }
}
