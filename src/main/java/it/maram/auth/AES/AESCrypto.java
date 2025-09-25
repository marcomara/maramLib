package it.maram.auth.AES;

import it.maram.auth.EncriptionAlgs;
import it.maram.auth.Password;
import it.maram.auth.SymmetricDecryptor;
import it.maram.auth.SymmetricEncryptor;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AESCrypto {
    public static String encrypt(final String data, final SecretKey key, final int ivLen)
            throws NoSuchPaddingException
            , NoSuchAlgorithmException
            , InvalidKeyException
            , InvalidAlgorithmParameterException
            , IllegalBlockSizeException
            , BadPaddingException {
        final byte[] iv = Password.generateSalt(ivLen);
        final SymmetricEncryptor se = new SymmetricEncryptor(key, EncriptionAlgs.AESCBCPK5P, EncriptionAlgs.AES, new IvParameterSpec(iv));
        final byte[] encrypted = se.encrypt(data);
        return Base64.getEncoder().encodeToString(iv) + ":" + Base64.getEncoder().encodeToString(encrypted);
    }
    public static String decrypt(final String data, SecretKey key) throws InvalidAlgorithmParameterException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        final String[] parts = data.split(":");
        final byte[] iv = Base64.getDecoder().decode(parts[0]);
        final byte[] cText = Base64.getDecoder().decode(parts[1]);
        final SymmetricDecryptor sd = new SymmetricDecryptor(key, EncriptionAlgs.AESCBCPK5P, EncriptionAlgs.AES, new IvParameterSpec(iv));
        return new String(sd.decrypt(cText));
    }
}
