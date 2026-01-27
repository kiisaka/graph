package com.leetcode;

public class ValidIP {
    public String validIPAddress(String queryIP) {
        return isIPv4(queryIP)
                ? "IPv4"
                : isIPv6(queryIP)
                ? "IPv6"
                : "Neither";
    }

    public boolean isIPv4(final String ip) {
        int len = ip.length();
        if (len < 7 || len > 15) return false;
        final String[] parts = ip.split("\\.", -1);
        if (parts.length != 4) return false;
        for (String part: parts) {
            try {
                final int partInt = Integer.parseInt(part);
                if (partInt < 0 || partInt > 255) return false;
                if (part.length() > 1 && part.startsWith("0")) return false;
            } catch (final Exception ex) {
                return false;
            }
        }
        return true;
    }

    public boolean isIPv6(final String ip) {
        final String[] parts = ip.split("\\:", -1);
        if (parts.length != 8) return false;

        for (int i=0; i<8; i++) {
            final String part = parts[i];
            try {
                if (part.isEmpty() || part.length() > 4) return false;
                final int partInt = Integer.parseInt(part, 16);
                if (partInt < 0 || partInt > 0xFFFF) return false;
            } catch (final Exception ex) {
                return false;
            }
        }
        return true;
    }
}