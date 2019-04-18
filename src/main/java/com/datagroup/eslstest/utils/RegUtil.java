package com.datagroup.eslstest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUtil {
    /** * 判断是否为合法IP * @return the ip */
    public static boolean isboolIp(String ipAddress) {
        String reg = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(ipAddress);
        return matcher.matches();
    }
    public static boolean isboolHardVersion(String hardVersion) {
        String reg = "^[a-zA-Z][0-9].[0-9][0-9]$";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(hardVersion);
        return matcher.matches();
    }
}
