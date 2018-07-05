package com.aliyun.mns.model;

import java.io.Serializable;

public class AccountAttributes implements Serializable {
    private static final long serialVersionUID = 1159160616345899035L;

    private String loggingBucket;

    public String getLoggingBucket() {
        return loggingBucket;
    }

    public void setLoggingBucket(String loggingBucket) {
        this.loggingBucket = loggingBucket;
    }
}
