package com.aliyun.mns.model.request.queue;


import com.aliyun.mns.model.AbstractRequest;
import com.aliyun.mns.model.Message;

public class SendMessageRequest extends AbstractRequest {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

}
