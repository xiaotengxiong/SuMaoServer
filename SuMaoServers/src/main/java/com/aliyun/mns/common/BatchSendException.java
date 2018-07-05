package com.aliyun.mns.common;

import com.aliyun.mns.model.Message;

import java.util.List;

public class BatchSendException extends ServiceException {
    /**
     *
     */
    private static final long serialVersionUID = -7705861423905005565L;
    private List<Message> messages;

    public BatchSendException(List<Message> messages) {
        this.setMessages(messages);
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
}
