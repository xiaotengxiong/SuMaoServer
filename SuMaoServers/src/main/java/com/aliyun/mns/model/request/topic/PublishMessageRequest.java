package com.aliyun.mns.model.request.topic;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.TopicMessage;


public class PublishMessageRequest extends AbstractRequest {
    private TopicMessage message;
    private MessageAttributes messageAttributes;

    public TopicMessage getMessage() {
        return message;
    }

    public void setMessage(TopicMessage message) {
        this.message = message;
    }

    public MessageAttributes getMessageAttributes() {
        return messageAttributes;
    }

    public void setMessageAttributes(MessageAttributes messageAttributes) {
        this.messageAttributes = messageAttributes;
    }
}
