package it.maram.auth;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;

public class SymmetricEncryptor {
    private final Cipher ciph;
    private final Key sk;
    private final String method;
    public SymmetricEncryptor(final Key key, final String transformation,
                              @NotNull final AlgorithmParameterSpec specs, @Nullable final SecureRandom rnd)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) this.ciph.init(Cipher.ENCRYPT_MODE, key, specs);
        else this.ciph.init(Cipher.ENCRYPT_MODE, key, specs, rnd);
    }
    public SymmetricEncryptor(final Key key, final String transformation)
            throws InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        this.ciph.init(Cipher.ENCRYPT_MODE, key);
    }
    public SymmetricEncryptor(final Key key, final String transformation,
                              @NotNull final SecureRandom rnd)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        this.ciph.init(Cipher.ENCRYPT_MODE, key, rnd);
    }
    public SymmetricEncryptor(final Key key, final String transformation, final String keySpecAlg, final IvParameterSpec iv)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        this.ciph.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key.getEncoded(), keySpecAlg), iv);
    }
    public SymmetricEncryptor(final Key key, final String transformation,
                              @NotNull final AlgorithmParameters prms, @Nullable final SecureRandom rnd)
            throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException{
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) this.ciph.init(Cipher.ENCRYPT_MODE, key, prms);
        else this.ciph.init(Cipher.ENCRYPT_MODE, key, prms, rnd);
    }
    public SymmetricEncryptor(final Key key, final String transformation,
                              @NotNull final Certificate certificate,@Nullable final SecureRandom rnd)
            throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {
        this.sk = key;
        this.method = transformation;
        this.ciph = Cipher.getInstance(method);
        if(rnd == null) this.ciph.init(Cipher.ENCRYPT_MODE, certificate);
        else this.ciph.init(Cipher.ENCRYPT_MODE, certificate, rnd);
    }
    public byte[] encrypt(byte[] message) throws IllegalBlockSizeException, BadPaddingException {
        return this.ciph.doFinal(message);
    }
    public byte[] encrypt(String message) throws IllegalBlockSizeException, BadPaddingException {
        return ciph.doFinal(message.getBytes());
    }
    public byte[] encrypt(String message, Charset charset) throws IllegalBlockSizeException, BadPaddingException {
        return ciph.doFinal(message.getBytes(charset));
    }
}
