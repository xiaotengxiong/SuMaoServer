package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

public class BatchPeekMessageRequest extends AbstractRequest {
    private int batchSize = -1;

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
