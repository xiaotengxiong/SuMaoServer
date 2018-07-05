package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.QueueMeta;

public class CreateQueueRequest extends AbstractRequest {
    private QueueMeta queueMeta;

    public QueueMeta getQueueMeta() {
        return queueMeta;
    }

    public void setQueueMeta(QueueMeta queueMeta) {
        this.queueMeta = queueMeta;
    }
}
