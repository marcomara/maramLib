package it.maram.utils;

import java.util.HashMap;
import java.util.Map;

public class SimpleFileConfiguration {
    private Map<String, Object> tree;
    public SimpleFileConfiguration(String s){
        String[] lines = s.split("\n");
        tree = new HashMap<>();
        registerParent(lines);
    }
    private void registerParent(String[] lines){
        for(int i = 0; i<lines.length; i++){
            String line = lines[i];
            String[] vals = line.split(":");
            if(vals.length<2||vals[1].isEmpty()){
                Map<String, Object> child = new HashMap<>();
                for(int j = i; j<lines.length; j++, i++){
                    String subline = lines[j];
                    if(subline.startsWith("\t")){
                        String[] subvals = subline.substring(1).split(":");
                        child.put(subvals[0], subvals[1]);
                    }else{
                        i--;
                        break;
                    }

                }



                tree.put(vals[0], child);
            }else tree.put(vals[0], vals[1]);
        }
    }
}
