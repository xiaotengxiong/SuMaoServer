package com.aliyun.mns.model;

import static com.aliyun.mns.common.MNSConstants.DEFAULT_CHARSET;

import java.io.UnsupportedEncodingException;

public class RawTopicMessage extends TopicMessage {
    public RawTopicMessage() {
        super();
    }

    /**
     * 获取文本消息体
     */
    public String getMessageBodyAsString() {
        if (getMessageBodyBytes() == null)
            return null;
        try {
            return new String(getMessageBodyBytes(), DEFAULT_CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Not support encoding: " + DEFAULT_CHARSET);
        }
    }


    /**
     * 获取消息体，文本类型
     *
     * @return
     */
    public String getMessageBody() {
        return getMessageBodyAsString();
    }

}
