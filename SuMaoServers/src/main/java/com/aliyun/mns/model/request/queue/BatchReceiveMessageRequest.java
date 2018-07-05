package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

public class BatchReceiveMessageRequest extends AbstractRequest {
    private int waitSeconds = 0;
    private int batchSize = -1;

    public int getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }
}
