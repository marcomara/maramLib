package it.maram.auth.RSA;

import it.maram.logging.InvalidParametersException;
import it.maram.utils.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public class RSAUtils {
    public static void generatePublicFile(File privateKeyFile, File publicKeyOutputFile) throws Exception{
        final char[] cha = Base64.getEncoder().encodeToString(convert(load(privateKeyFile)).getEncoded()).toCharArray();
        PrintStream ps = new PrintStream(publicKeyOutputFile);
        generatePublicFile(cha, ps);
    }
    public static void generatePublicFile(String privateKeyResourceFile, File publicKeyOutputFile) throws Exception{
        final InputStream is = RSAUtils.class.getResourceAsStream(privateKeyResourceFile);
        if(is==null) throw new InvalidParametersException("Resource file not found");
        final String pks = StringUtils.readAll(is);
        final char[] cha = Base64.getEncoder().encodeToString(convert(load(pks)).getEncoded()).toCharArray();
        PrintStream ps = new PrintStream(publicKeyOutputFile);
        generatePublicFile(cha, ps);
    }
    public static void generatePublicFile(char[] charArray, PrintStream printStream){
        int a = 0;
        printStream.println("-----BEGIN PUBLIC KEY-----");
        for (char c : charArray){
            printStream.print(c);
            a++;
            if(a==64){
                printStream.print("\n");
                printStream.flush();
                a=0;
            }
        }
        printStream.println("\n-----END PUBLIC KEY-----");
        printStream.flush();
        printStream.close();
    }
    public static PublicKey convert(PrivateKey pk) throws Exception{
        final RSAPrivateCrtKey rpck = (RSAPrivateCrtKey) pk;
        final RSAPublicKeySpec rpks = new RSAPublicKeySpec(rpck.getModulus(), rpck.getPublicExponent());
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(rpks);
    }
    public static PrivateKey load(File privateKeyFile) throws Exception{
        final String s = new String(Files.readAllBytes(privateKeyFile.toPath()));
        return load(s);
    }
    public static PrivateKey load(String privateKeyString) throws Exception{
        final String pem = privateKeyString.replace("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PRIVATE KEY-----", "");
        final byte[] encd = Base64.getMimeDecoder().decode(pem);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(encd);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }
    public static PublicKey loadPublicFromEncoded(final byte[] publicKeyBytes) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeyBytes);
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
    public static PublicKey loadPublic(final File publicKeyFile) throws Exception{
        final String s = new String(Files.readAllBytes(publicKeyFile.toPath()));
        return loadPublic(s);
    }
    public static PublicKey loadPublic(final String publicKeyString) throws Exception{
        final String pem = publicKeyString.replace("-----BEGIN PUBLIC KEY-----", "")
                .replaceAll(System.lineSeparator(), "")
                .replace("-----END PUBLIC KEY-----", "");
        final byte[] encd = Base64.getMimeDecoder().decode(pem);
        return loadPublicFromEncoded(encd);
    }
    public static PublicKey loadPublic(final byte[] publicKeyData) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final byte[] encd = Base64.getMimeDecoder().decode(publicKeyData);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encd);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }
}
