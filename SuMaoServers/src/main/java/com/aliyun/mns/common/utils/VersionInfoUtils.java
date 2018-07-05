package com.aliyun.mns.common.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.Properties;

public class VersionInfoUtils {
    private static final String VERSION_INFO_FILE = "versioninfo.properties";

    private static Log log = LogFactory.getLog(VersionInfoUtils.class);

    private static String version = null;

    private static String defaultUserAgent = null;

    public static String getVersion() {
        if (version == null) {
            initializeVersion();
        }
        return version;
    }

    public static String getDefaultUserAgent() {
        if (defaultUserAgent == null) {
            String platformInfo = System.getProperty("os.name") + "/"
                    + System.getProperty("os.version") + "/"
                    + System.getProperty("os.arch") + ";"
                    + System.getProperty("java.version");

            defaultUserAgent = "aliyun-sdk-java" + "/" + getVersion() + "("
                    + platformInfo + ")";
        }
        return defaultUserAgent;
    }

    private static void initializeVersion() {
        InputStream inputStream = VersionInfoUtils.class.getClassLoader()
                .getResourceAsStream(VERSION_INFO_FILE);
        Properties versionInfoProperties = new Properties();
        try {
            if (inputStream == null)
                throw new Exception(VERSION_INFO_FILE
                        + " not found on classpath");

            versionInfoProperties.load(inputStream);
            version = versionInfoProperties.getProperty("version");
        } catch (Exception e) {
            log.info("Unable to load version information for the running SDK: "
                    + e.getMessage());
            version = "unknown-version";
        }
    }
}
