package com.aliyun.mns.model;


public abstract class TopicMessage extends BaseMessage {
    public static enum BodyType {
        UNKNOW, STRING, BASE64
    }
    
    private String messageTag;

    public TopicMessage() {
        super();
    }

    /**
     * 设置消息体，二进制类型
     *
     * @param messageBody
     */
    public void setMessageBody(byte[] messageBody) {
        setBaseMessageBody(messageBody);
    }


    /**
     * 设置消息体，文本类型，文本编码utf-8
     *
     * @param messageBody
     */
    public void setMessageBody(String messageBody) {
        setBaseMessageBody(messageBody);

    }

    /**
     * 获取二进制消息体
     */
    public byte[] getMessageBodyAsBytes() {
        return getMessageBodyBytes();
    }

    public String getMessageTag() {
        return messageTag;
    }

    /*
     * 最多16个字符
     */
    public void setMessageTag(String messageTag) {
        this.messageTag = messageTag;
    }

}
