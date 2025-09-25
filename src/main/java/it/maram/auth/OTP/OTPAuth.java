package it.maram.auth.OTP;

import com.sun.istack.internal.Nullable;
import it.maram.logging.ExceptionHandler;
import it.maram.utils.QRCode;
import it.maram.utils.StringUtils;
import org.apache.commons.codec.binary.Base32;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.time.Instant;

public class OTPAuth {
    public static boolean authorize(String key, String secret, int range)  throws Exception{
        final long instant = Instant.now().getEpochSecond() / 30;
        for(int i = -range; i<=range; i++) {
            String code = Authenticator.F32(secret, instant, i, "HmacSHA1");
            System.out.println(key + " : " + code);
            if(key.equals(code)) return true;
        }
        return false;
    }
    public static boolean gui(String secret, boolean repeat, int range){
        try {
            if (!repeat){
                final String code = getCode();
                if(code==null) return false;
                return authorize(code , secret, range);
            }
            else {
                boolean passed;
                do {
                    passed = authorize(getCode(), secret, range);
                } while (!passed);
                return true;
            }
        }catch (Exception e){
            //ExceptionHandler.handleLoudly(e);
            return false;
        }
    }
    @Nullable
    private static String getCode(){
        byte[] bytes = null;
        JLabel label = new JLabel("Inserisci codice OTP:");
        int i;
        do {
            JTextField field = new JTextField();
            JComponent[] comps = new JComponent[]{label, field};
            i = JOptionPane.showConfirmDialog(null, comps, "Autorizzazione 2FA", JOptionPane.OK_CANCEL_OPTION);
            if(i==JOptionPane.CANCEL_OPTION||i==JOptionPane.CLOSED_OPTION) return null;
            if (field.getText().length()==6) bytes = field.getText().getBytes(StandardCharsets.UTF_8);
            else i = -1;
        }while (i!=JOptionPane.OK_OPTION);
        System.out.println("Proceeding");
        return StringUtils.fromBytes(bytes);
    }
    public static Icon generateQRCode(final String token, final String account,@Nullable String issuer){
        if(issuer==null) issuer= "maramLib";
        final String tok = new Base32().encodeToString(token.getBytes());
        final String data = "otpauth://totp/" + account + "?secret=" + tok
                + "&issuer=" + issuer + "&algorithm=SHA1&digits=6&period=30";
        try {
            return new ImageIcon(QRCode.generateQRCode(data));
        }catch (Exception e){
            ExceptionHandler.handleLoudly(e);
        }
        return null;
    }
}
