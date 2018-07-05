package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;

public class ReceiveMessageRequest extends AbstractRequest {
    private Integer waitSeconds;

    public Integer getWaitSeconds() {
        return waitSeconds;
    }

    public void setWaitSeconds(Integer waitSeconds) {
        this.waitSeconds = waitSeconds;
    }
}
