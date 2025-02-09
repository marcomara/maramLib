package it.maram.auth.OTP;

import javax.swing.*;
import java.nio.charset.StandardCharsets;

public class OTPAuth {
    public static boolean authorize(String key, String secret)  throws Exception{
        for(int i = -2; i<=2; i++) {
            String code = Authenticator.F32(secret, i, "HmacSHA1");
            if(key.equals(code)) return true;
        }
        return false;
    }
    public static boolean gui(String secret,boolean repeat){
        try {
            if (!repeat) return authorize(getCode(), secret);
            else {
                boolean passed;
                do {
                    passed = authorize(getCode(), secret);
                } while (!passed);
                return true;
            }
        }catch (Exception e){
            //ExceptionHandler.handleLoudly(e);
            return false;
        }
    }
    private static String getCode(){
        byte[] bytes;
        do {
            bytes = JOptionPane.showInputDialog(null, "Inserisci codice OTP:", "Autorizzazione 2FA", JOptionPane.WARNING_MESSAGE).getBytes(StandardCharsets.UTF_8);
        }while (bytes.length!=6);
        StringBuilder sb = new StringBuilder();
        for(byte b : bytes){
            sb.append((char) b);
        }
        return sb.toString();
    }
}
