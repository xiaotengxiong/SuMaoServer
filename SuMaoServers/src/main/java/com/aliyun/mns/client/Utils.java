package com.aliyun.mns.client;

import org.apache.log4j.Logger;

import java.net.URI;
import java.net.URISyntaxException;

public class Utils {
    public static Logger logger = Logger.getLogger(Utils.class);

    public static URI getHttpURI(String endpoint) {
        if (endpoint == null) {
            logger.warn("参数endpoint为空指针。");
            throw new NullPointerException("参数endpoint为空指针。");
        }

        try {
            if (!endpoint.startsWith("http://") && !endpoint.startsWith("https://")) {
                logger.warn("仅支持http协议。Endpoint必须以http://或https://开头。");
                throw new IllegalArgumentException(
                        "仅支持http协议。Endpoint必须以http://或https://开头。");
            }
            while (endpoint.endsWith("/")) {
                endpoint = endpoint.substring(0, endpoint.length() - 1);
            }

            if (endpoint.length() < "http://".length()) {
                logger.warn("参数endpoint地址无效.");
                throw new IllegalArgumentException("参数endpoint地址无效.");
            }
            return new URI(endpoint);

        } catch (URISyntaxException e) {
            logger.warn("uri syntax error");
            throw new IllegalArgumentException(e);
        }
    }

    public static boolean checkName(String name) {
        if (name == null) {
            //System.out.println("name is null");
            logger.debug("name is null");
            return false;
        }
        int len = name.length();
        if (len < 1 || len > 256) {
            //System.out.println("length error, should in 1~255");
            logger.debug("length error, should in 1~255");
            return false;
        }

        if (!Character.isDigit(name.charAt(0)) && !Character.isLetter(name.charAt(0))) {
            //System.out.println("name format error");
            logger.debug("name format error");
            return false;
        }

        for (int i = 1; i < len; i++) {
            if (!Character.isDigit(name.charAt(i)) && !Character.isLetter(name.charAt(i)) && name.charAt(i) != '-') {
                //System.out.println("name format error");
                logger.debug("name format error");
                return false;
            }
        }
        return true;
    }

    public static boolean checkQueueName(String name) {
        return checkName(name);
    }

    public static boolean checkTopicName(String name) {
        return checkName(name);
    }

    public static boolean checkSubscriptionName(String name) {
        return checkName(name);
    }

}
