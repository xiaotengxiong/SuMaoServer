package com.aliyun.mns.model;

import org.apache.commons.codec.binary.Base64;


public class Base64TopicMessage extends TopicMessage {
    public Base64TopicMessage() {
        super();
    }

    /**
     * 获取Base64编码的消息体
     */
    public String getMessageBodyAsBase64() {
        if (getMessageBodyBytes() == null)
            return null;
        return Base64.encodeBase64String(getMessageBodyBytes());
    }

    /**
     * 获取消息体，文本类型，获取的文本为消息体的base64编码
     *
     * @return
     */
    public String getMessageBody() {
        return getMessageBodyAsBase64();
    }
}
