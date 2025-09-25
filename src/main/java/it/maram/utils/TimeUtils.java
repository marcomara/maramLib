package it.maram.utils;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

public class TimeUtils {
    private static final String UTCServer = "";
    public static Date getDate(final String server) throws IOException {
        final NTPUDPClient tc = new NTPUDPClient();
        final InetAddress ia = InetAddress.getByName(server);
        final TimeInfo ti = tc.getTime(ia);
        return new Date(ti.getReturnTime());
    }
    public static Date getUTCDate() throws IOException{
        return getDate(UTCServer);
    }
}
