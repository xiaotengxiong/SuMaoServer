package com.aliyun.mns.model.request.topic;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.SubscriptionMeta;

public class SubscribeRequest extends AbstractRequest {
    private SubscriptionMeta meta;

    public SubscriptionMeta getMeta() {
        return meta;
    }

    public void setMeta(SubscriptionMeta meta) {
        this.meta = meta;
    }
}
