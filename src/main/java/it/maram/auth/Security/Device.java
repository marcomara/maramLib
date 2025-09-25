package it.maram.auth.Security;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Device {
    public static SecretKey getDeviceKey() throws SocketException, NoSuchAlgorithmException {
        final String userInfo = getDeviceUUID();
        return getCustomKey(userInfo);
    }
    public static String getDeviceUUID() throws SocketException {
        return System.getProperty("user.name") + new String(NetworkInterface.getByIndex(0).getHardwareAddress());
    }
    public static SecretKey getCustomKey(final String secret) throws NoSuchAlgorithmException {
        final byte[] keyBytes = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
        return new SecretKeySpec(keyBytes, 0, 16, "AES");
    }
}
