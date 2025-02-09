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
}
