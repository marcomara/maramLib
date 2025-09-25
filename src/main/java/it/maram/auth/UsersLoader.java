package it.maram.auth;
import it.maram.logging.JSONFormatException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class UsersLoader {
    public static final String usr = "Username";
    public static final String s1 = "Secret1";
    public static final String s2 = "Secret2";
    public static final SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
    public static List<User> getUsersE(final byte[] bytes) throws JSONFormatException {
        final List<it.maram.auth.UPAE.User> list = new ArrayList<>();
        for(int i = 0; i<bytes.length; i++){
            char c = (char)bytes[i];
            if(c == '{'){
                String usrf = null, s1f = null ,s2f = null;
                StringBuilder field = new StringBuilder();
                StringBuilder value = new StringBuilder();
                boolean inn=false;
                boolean ended = false;
                for(int j = ++i; i<bytes.length; j++, i++){
                    char cn = (char)bytes[j];
                    if(cn == '\n' || cn == '\t' || cn == ' ') continue;
                    if(cn == ',' || cn == '}'){
                        inn = false;
                        String fieldStr = field.toString().trim();
                        String valueStr = value.toString().trim();
                        if(fieldStr.equalsIgnoreCase(usr)) {
                            usrf = valueStr;
                        } else if(fieldStr.equalsIgnoreCase(s1)) {
                            s1f = valueStr;
                        } else if(fieldStr.equalsIgnoreCase(s2)) {
                            s2f = valueStr;
                        }
                        System.out.println(field + " : " + value);
                        if(cn == '}'){
                            ended = true;
                            break;
                        }
                        field = new StringBuilder();
                        value = new StringBuilder();
                    } else{
                        if(inn){
                            value.append(cn);
                        }else{
                            if(cn == ':') inn = true;
                            else field.append(cn);
                        }
                    }
                }
                if(!ended) throw new JSONFormatException("File does not comply with the JSON format");
                if(usrf != null && s1f != null && s2f != null){
                    list.add(new it.maram.auth.UPAE.User(usrf, s1f, s2f));
                    System.out.println("User " + usrf + " registered");
                }else{
                    System.out.println(usrf + ", " + s1f + ", " + s2f);
                    System.out.println();
                }

            }
        }
        return Collections.unmodifiableList(list);
    }
    public static List<User> getUsersUE(final byte[] bytes) throws IOException, JSONFormatException {
        final List<it.maram.auth.UPAUE.User> list = new ArrayList<>();
        for(int i = 0; i<bytes.length; i++){
            char c = (char)bytes[i];
            if(c == '{'){
                String usrf = null, s1f = null ,s2f = null;
                StringBuilder field = new StringBuilder();
                StringBuilder value = new StringBuilder();
                boolean inn=false;
                boolean ended = false;
                for(int j = ++i; i<bytes.length; j++, i++){
                    char cn = (char)bytes[j];
                    if(cn == '\n' || cn == '\t' || cn == ' ') continue;
                    if(cn == ',' || cn == '}'){
                        inn = false;
                        String fieldStr = field.toString().trim();
                        String valueStr = value.toString().trim();
                        if(fieldStr.equalsIgnoreCase(usr)) {
                            usrf = valueStr;
                        } else if(fieldStr.equalsIgnoreCase(s1)) {
                            s1f = valueStr;
                        } else if(fieldStr.equalsIgnoreCase(s2)) {
                            s2f = valueStr;
                        }
                        System.out.println(field + " : " + value);
                        if(cn == '}'){
                            ended = true;
                            break;
                        }
                        field = new StringBuilder();
                        value = new StringBuilder();
                    } else{
                        if(inn){
                            value.append(cn);
                        }else{
                            if(cn == ':') inn = true;
                            else field.append(cn);
                        }
                    }
                }
                if(!ended) throw new JSONFormatException("File does not comply with the JSON format");
                if(usrf != null && s1f != null && s2f != null){
                    list.add(new it.maram.auth.UPAUE.User(usrf, s1f, s2f));
                    System.out.println("User " + usrf + " registered");
                }else{
                    System.out.println(usrf + ", " + s1f + ", " + s2f);
                    System.out.println();
                }

            }
        }
        return Collections.unmodifiableList(list);
    }
}
