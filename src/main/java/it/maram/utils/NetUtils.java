package it.maram.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class NetUtils {
    public static byte[] getMacAddress() throws SocketException {
        Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
        while (interfaces.hasMoreElements()) {
            NetworkInterface ni = interfaces.nextElement();
            if (ni.isVirtual()) continue;
            byte[] mac = ni.getHardwareAddress();
            if (mac != null && ni.isUp() && !ni.isLoopback() && !ni.isVirtual()) {
                return mac;
            }
        }
        throw new NullPointerException();
    }
    public static byte[] getMacAddress(String interfaceName) throws SocketException {
        return NetworkInterface.getByName(interfaceName).getHardwareAddress();
    }

    public static byte[] getMacAddress(int index) throws SocketException {
        return NetworkInterface.getByIndex(index).getHardwareAddress();
    }

    public static byte[] getMacAddress(InetAddress address) throws SocketException {
        return NetworkInterface.getByInetAddress(address).getHardwareAddress();
    }

}
