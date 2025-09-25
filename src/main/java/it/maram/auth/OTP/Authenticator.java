package it.maram.auth.OTP;

import org.apache.commons.codec.binary.Base32;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.Random;

public class Authenticator {
    public static byte[] decodeSecret32(final String secret){
        Base32 codec32 = new Base32();
        return codec32.decode(secret.toUpperCase());
    }
    public static byte[] encodeSecret32(final String phrase){
        Base32 codec32 = new Base32();
        return codec32.encode(phrase.getBytes());
    }
    public static byte[] decodeSecret64(String secret){
        return Base64.getDecoder().decode(secret);
    }
    private static String encodedHex(byte[] digest){
        StringBuilder sb = new StringBuilder();
        for (byte b : digest) {
            sb.append(Integer.toString((b & 0xFF) + 0x100, 16));
        }
        return sb.toString();
    }
    private static String calculateCode(byte[] key, long tm, String algo) throws NoSuchAlgorithmException, InvalidKeyException {
        byte[] tmb = new byte[8];
        for(int  i = 7; i>=0; i--){
            tmb[i]=(byte) (tm & 0xFF);
            tm>>=8;
        }
        Mac hmac = Mac.getInstance(algo);
        hmac.init(new SecretKeySpec(key, algo));
        byte[] hash = hmac.doFinal(tmb);
        int offset = hash[hash.length-1] & 0xF;
        long mostSignificantByte = (hash[offset] & 0x7F) << 24;
        long secondMostSignificantByte = (hash[offset + 1] & 0xFF) << 16;
        long thirdMostSignificantByte = (hash[offset + 2] & 0xFF) << 8;
        long leastSignificantByte = hash[offset + 3] & 0xFF;
        long binaryCode = mostSignificantByte
                | secondMostSignificantByte
                | thirdMostSignificantByte
                | leastSignificantByte;
        int totp = (int) (binaryCode % Math.pow(10, 6));
        return String.format("%0" + 6 + "d", totp);
    }
    public static String F32(String secret,long instant, int off, String algo) throws Exception{
        return calculateCode(decodeSecret32(secret), instant+off, algo);
    }
    public static byte[] createEncodedPhrase(){
        String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder phraseBuilder = new StringBuilder();
        Random rand = new Random();
        while(phraseBuilder.length()<24){
            int index = (int) (rand.nextFloat() * CHARS.length());
            phraseBuilder.append(CHARS.charAt(index));
        }
        final String phrase = phraseBuilder.toString();
        return encodeSecret32(phrase);
    }
}
