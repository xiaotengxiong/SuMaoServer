package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

import java.util.List;

public class BatchDeleteMessageRequest extends AbstractRequest {
    private List<String> receiptHandles;

    public List<String> getReceiptHandles() {
        return receiptHandles;
    }

    public void setReceiptHandles(List<String> receiptHandles) {
        this.receiptHandles = receiptHandles;
    }
}
