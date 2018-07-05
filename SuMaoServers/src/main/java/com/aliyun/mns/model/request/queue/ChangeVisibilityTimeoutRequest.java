package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

public class ChangeVisibilityTimeoutRequest extends AbstractRequest {
    private String receiptHandle;
    private int visibilityTimeout;

    public String getReceiptHandle() {
        return receiptHandle;
    }

    public void setReceiptHandle(String receiptHandle) {
        this.receiptHandle = receiptHandle;
    }

    public int getVisibilityTimeout() {
        return visibilityTimeout;
    }

    public void setVisibilityTimeout(int visibilityTimeout) {
        this.visibilityTimeout = visibilityTimeout;
    }

}
