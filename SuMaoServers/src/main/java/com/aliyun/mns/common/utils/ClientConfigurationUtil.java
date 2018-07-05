package com.aliyun.mns.common.utils;

import com.aliyun.mns.common.http.ClientConfiguration;

public class ClientConfigurationUtil {
    public static void copyConfig(ClientConfiguration source, ClientConfiguration target) {
        target.setConnectionTimeout(source.getConnectionTimeout());
        target.setMaxConnections(source.getMaxConnections());
        target.setProxyDomain(source.getProxyDomain());
        target.setProxyHost(source.getProxyHost());
        target.setProxyPassword(source.getProxyPassword());
        target.setProxyPort(source.getProxyPort());
        target.setProxyUsername(source.getProxyUsername());
        target.setProxyWorkstation(source.getProxyWorkstation());
        target.setSocketTimeout(source.getSocketTimeout());
        target.setUserAgent(source.getUserAgent());
    }
}
