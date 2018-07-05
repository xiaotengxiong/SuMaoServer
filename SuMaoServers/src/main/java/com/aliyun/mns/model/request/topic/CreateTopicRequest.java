package com.aliyun.mns.model.request.topic;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.TopicMeta;


public class CreateTopicRequest extends AbstractRequest {
    private TopicMeta meta;

    public TopicMeta getTopicMeta() {
        return this.meta;
    }

    public void setTopicMeta(TopicMeta meta) {
        this.meta = meta;
    }
}
