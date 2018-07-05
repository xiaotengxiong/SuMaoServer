package com.aliyun.mns.model;


import com.aliyun.mns.common.MNSConstants;

public class TopicMeta {
    private String topicName;
    private Long messageCount;
    private Long maxMessageSize;
    private Long messageRetentionPeriod;
    private Long createTime;
    private Long lastModifyTime;
    private String topicURL;
    protected boolean loggingEnabled;

    public TopicMeta() {
        this.topicName = null;
        this.topicURL = null;
        this.maxMessageSize = null;
        this.messageRetentionPeriod = null;
        this.createTime = 0L;
        this.lastModifyTime = 0L;
    }

    public Long getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Long messageCount) {
        this.messageCount = messageCount;
    }

    public String getTopicName() {
        return this.topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public String getTopicURL() {
        return this.topicURL;
    }

    public void setTopicURL(String topicURL) {
        this.topicURL = topicURL;
    }

    public Long getMaxMessageSize() {
        return this.maxMessageSize;
    }

    public void setMaxMessageSize(Long maxMessageSize) {
        this.maxMessageSize = maxMessageSize;
    }

    public Long getMessageRetentionPeriod() {
        return messageRetentionPeriod;
    }

    public void setMessageRetentionPeriod(Long period) {
        this.messageRetentionPeriod = period;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Long lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }
}
