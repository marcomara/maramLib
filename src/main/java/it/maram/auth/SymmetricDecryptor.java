package it.maram.auth;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.crypto.*;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;

public class SymmetricDecryptor {
    private final Cipher ciph;
    private final SecretKey sk;
    private final String method;
    public SymmetricDecryptor(final SecretKey key, final String transformation,
                              @NotNull final AlgorithmParameterSpec specs, @Nullable final SecureRandom rnd)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) this.ciph.init(Cipher.DECRYPT_MODE, key, specs);
        else this.ciph.init(Cipher.DECRYPT_MODE, key, specs, rnd);
    }
    public SymmetricDecryptor(final SecretKey key, final String transformation)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        this.ciph.init(Cipher.DECRYPT_MODE, key);
    }
    public SymmetricDecryptor(final SecretKey key, final String transformation,
                              @NotNull final SecureRandom rnd)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        this.ciph.init(Cipher.DECRYPT_MODE, key, rnd);
    }
    public SymmetricDecryptor(final SecretKey key, final String transformation,
                              @NotNull final AlgorithmParameters prms, @Nullable final SecureRandom rnd)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) this.ciph.init(Cipher.DECRYPT_MODE, key, prms);
        else ciph.init(Cipher.DECRYPT_MODE, key, prms, rnd);
    }
    public SymmetricDecryptor(final SecretKey key, final String transformation,
                              @NotNull final Certificate certificate, @Nullable final SecureRandom rnd)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) ciph.init(Cipher.DECRYPT_MODE, certificate);
        else this.ciph.init(Cipher.DECRYPT_MODE, certificate, rnd);
    }
    public byte[] decrypt(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
        return this.ciph.doFinal(message);
    }
    public byte[] decrypt(String message) throws IllegalBlockSizeException, BadPaddingException {
        return this.ciph.doFinal(message.getBytes());
    }
    public byte[] decrypt(String message, Charset charset) throws IllegalBlockSizeException, BadPaddingException {
        return this.ciph.doFinal(message.getBytes(charset));
    }
}
