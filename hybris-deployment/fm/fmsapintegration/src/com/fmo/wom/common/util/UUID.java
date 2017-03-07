package com.fmo.wom.common.util;

import java.net.InetAddress;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.log4j.Logger;




public class UUID {
    
    private static final Logger logger = Logger.getLogger(UUID.class);
    private static String ipAddrSegment = "";
    
    // assuming the last portion of the ips in the cluster will be different.
    // Set the ip portion to the hex value of the last ip byte
    static {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            byte[] ipaddr = addr.getAddress();
            Byte b = new Byte(ipaddr[3]);
            String strTemp = Integer.toHexString(b.intValue() & 0x000000ff);
            ipAddrSegment += strTemp;
            logger.info("UUID on "+ addr.getHostAddress()+ " set ip segment to "+ ipAddrSegment);
        } catch (java.net.UnknownHostException ex) {
            logger.error("Unknown Host Exception Caught: " + ex.getMessage());
            // something bad happened here.  lets make a random attempt at it
            // This will have a minor chance of failing if all machines fail and 2 get the same 
            // following random value
            ipAddrSegment += RandomStringUtils.randomAlphanumeric(2);
            logger.info("UUID randomly set ip segment to "+ ipAddrSegment);
        }
    }
    
    public static String getUUID() {
        StringBuffer uuidString = new StringBuffer(ipAddrSegment);
        uuidString.append(RandomStringUtils.randomAlphanumeric(6));
        return uuidString.toString();
    }

}