package com.iscas.component.mqtt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: adams
 * Date:  2017/11/30
 * Time: 11:15
 */
@Configuration
public class AliConfig {
    @Value("${accessKeyId}")
    public  String accessKeyId;
    @Value("${accessKeySecret}")
    public  String accessKeySecret;
    @Value("${productKey}")
    public  String productKey;
    @Value("${deviceName}")
    public  String deviceName;
    @Value("${accountEndpoint}")
    private String accountEndpoint;
    @Value("${queueRef}")
    private String queueRef;
    @Value("${threadNum}")
    private int threadNum;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAccountEndpoint() {
        return accountEndpoint;
    }

    public void setAccountEndpoint(String accountEndpoint) {
        this.accountEndpoint = accountEndpoint;
    }

    public String getQueueRef() {
        return queueRef;
    }

    public void setQueueRef(String queueRef) {
        this.queueRef = queueRef;
    }

    public int getThreadNum() {
        return threadNum;
    }

    public void setThreadNum(int threadNum) {
        this.threadNum = threadNum;
    }
}
