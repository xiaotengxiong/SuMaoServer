package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

public class DeleteMessageRequest extends AbstractRequest {
    private String receiptHandle;

    public String getReceiptHandle() {
        return receiptHandle;
    }

    public void setReceiptHandle(String receiptHandle) {
        this.receiptHandle = receiptHandle;
    }
}
