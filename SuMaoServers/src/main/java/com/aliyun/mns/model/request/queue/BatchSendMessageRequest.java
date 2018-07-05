package com.aliyun.mns.model.request.queue;

import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.Message;

import java.util.List;

public class BatchSendMessageRequest extends AbstractRequest {
    private List<Message> messages;

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
