package it.maram.auth;

import it.maram.logging.InvalidParametersException;
import it.maram.utils.ArrayUtils;
import it.maram.utils.NetUtils;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

public class Password {
    public static SecretKey getUserDeviceKey() throws SocketException, NoSuchAlgorithmException {
        final String info = System.getProperty("user.name") + new String(NetUtils.getMacAddress());
        final byte[] keyBytes = MessageDigest.getInstance(EncriptionAlgs.SHA256).digest(info.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, 0 ,16, EncriptionAlgs.AES);
    }
    public static SecretKey toSecretKey(final char[] pwd, final String method) throws Exception{
        final PBEKeySpec keySpec = new PBEKeySpec(pwd);
        final SecretKeyFactory keyf = SecretKeyFactory.getInstance(method);
        return keyf.generateSecret(keySpec);
    }
    public static byte[] generateSalt(final int lenght){
        final SecureRandom rnd = new SecureRandom();
        final byte[] salt = new byte[lenght];
        rnd.nextBytes(salt);
        return salt;
    }
    public static byte[] getSalt(final String salt){
        return Base64.getDecoder().decode(salt);
    }
    public static byte[] hashPasswordSHA512(final byte[] password, final byte[] salt) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(EncriptionAlgs.SHA512);
        md.update(salt);
        return md.digest(password);
    }
    public static byte[] hashPasswordSHA512(final byte[] password) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance(EncriptionAlgs.SHA512);
        return md.digest(password);
    }
    public static SecretKey generateKey(final char[] password, final byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException{
        final KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        final SecretKeyFactory factory = SecretKeyFactory.getInstance(EncriptionAlgs.PBKDF2SHA1);
        return factory.generateSecret(spec);
    }
    public static SecretKey generateKey(final char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException{
        final KeySpec spec = new PBEKeySpec(password);
        final SecretKeyFactory factory = SecretKeyFactory.getInstance(EncriptionAlgs.PBKDF2SHA1);
        return factory.generateSecret(spec);
    }
    public static byte[] hashPasswordPBKDF2(final char[] password, final byte[] salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final SecretKey key = generateKey(password, salt);
        return key.getEncoded();
    }
    public static byte[] hashPasswordPBKDF2(final char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final SecretKey key = generateKey(password);
        return key.getEncoded();
    }
    public static String getPwdSecret(final byte[] salt, final byte[] hash){
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }
    public static String getPwdSecret(final char[] pass, final String alg, final int saltLen) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] salt = generateSalt(saltLen);
        final byte[] hash;
        if(alg.equals(EncriptionAlgs.SHA512)){
            hash = hashPasswordSHA512(ArrayUtils.getFromChars(pass), salt);
        }else if(alg.equals(EncriptionAlgs.PBKDF2SHA1)){
            hash = hashPasswordPBKDF2(pass, salt);
        }else throw new InvalidParametersException(alg + " is not a valid password hashing algorithm");
        return Base64.getEncoder().encodeToString(salt) + ":" + Base64.getEncoder().encodeToString(hash);
    }
    public static String getPwdSecret(final char[] pass, final String alg) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] hash;
        if(alg.equals(EncriptionAlgs.SHA512)){
            hash = hashPasswordSHA512(ArrayUtils.getFromChars(pass));
        }else if(alg.equals(EncriptionAlgs.PBKDF2SHA1)){
            hash = hashPasswordPBKDF2(pass);
        }else throw new InvalidParametersException(alg + " is not a valid password hashing algorithm");
        return Base64.getEncoder().encodeToString(hash);
    }
    public static boolean verifyPassword(final char[] pass, final String secretHash, final String alg) throws InvalidParametersException, NoSuchAlgorithmException, InvalidKeySpecException {
        if(alg.equals(EncriptionAlgs.PBKDF2SHA1) || alg.equals(EncriptionAlgs.SHA512)){
            if(secretHash.contains(":")){
                final String[] parts = secretHash.split(":");
                if(parts.length==2){
                    final byte[] salt = Base64.getDecoder().decode(parts[0]);
                    final String hash;
                    if(alg.equals(EncriptionAlgs.PBKDF2SHA1)){
                        hash = Base64.getEncoder().encodeToString(hashPasswordPBKDF2(pass, salt));
                    }else{
                        hash = Base64.getEncoder().encodeToString(hashPasswordSHA512(ArrayUtils.getFromChars(pass), salt));
                    }
                    return hash.equals(parts[1]);
                } else throw new InvalidParametersException("Password + Salt hash is not valid");
            }else{
                final String hash;
                if(alg.equals(EncriptionAlgs.PBKDF2SHA1)){
                    hash = Base64.getEncoder().encodeToString(hashPasswordPBKDF2(pass));
                }else{
                    hash = Base64.getEncoder().encodeToString(hashPasswordSHA512(ArrayUtils.getFromChars(pass)));
                }
                return hash.equals(secretHash);
            }

        }else throw new InvalidParametersException(alg + " is not a valid password hashing algorithm");
    }

}
