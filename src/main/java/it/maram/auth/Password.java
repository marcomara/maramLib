package it.maram.auth;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class Password {
    public static SecretKey toSecretKey(final char[] pwd, final String method) throws Exception{
        final PBEKeySpec keySpec = new PBEKeySpec(pwd);
        final SecretKeyFactory keyf = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
        return keyf.generateSecret(keySpec);
    }
}
