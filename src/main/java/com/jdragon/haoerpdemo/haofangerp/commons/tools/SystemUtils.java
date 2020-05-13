package com.jdragon.haoerpdemo.haofangerp.commons.tools;

import java.util.Properties;

/**
 * @author zhu
 * @version 1.0
 * @date 2020/5/13 下午3:41
 * @Description
 */
public class SystemUtils {
    public static String getSystemName(){
        Properties properties = System.getProperties();
        String systemName = properties.getProperty("os.name");
        return systemName;
    }

    public static boolean isLinux(){
        String systemName = getSystemName();
        return systemName != null && systemName.toLowerCase().contains("linux");
    }

    public static boolean isWindows(){
        String systemName = getSystemName();
        return systemName != null && systemName.length() >= 7 && "Windows".equals(systemName.substring(0, 7));
    }
}
