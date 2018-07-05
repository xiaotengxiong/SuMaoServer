package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.w3c.dom.Element;

import static com.aliyun.mns.common.MNSConstants.*;


public abstract class AbstractTopicMetaDeserializer<T> extends XMLDeserializer<T> {
    public AbstractTopicMetaDeserializer() {
        super();
    }

    protected TopicMeta parseMeta(Element root) {
        TopicMeta meta = new TopicMeta();
        String topicName = safeGetElementContent(root, TOPIC_NAME_TAG, null);
        meta.setTopicName(topicName);

        String messageCount = safeGetElementContent(root,
                MESSAGE_COUNT_TAG, "0");
        meta.setMessageCount(Long.parseLong(messageCount));

        String createTime = safeGetElementContent(root, CREATE_TIME_TAG, "0");
        meta.setCreateTime(Long.parseLong(createTime));

        String lastModifyTime = safeGetElementContent(root, LASTMODIFYTIME_TAG, "0");
        meta.setLastModifyTime(Long.parseLong(lastModifyTime));

        String maxMessageSize = safeGetElementContent(root,
                MAX_MESSAGE_SIZE_TAG, "0");
        meta.setMaxMessageSize(Long.parseLong(maxMessageSize));

        String messageRetentionPeriod = safeGetElementContent(root,
                MESSAGE_RETENTION_PERIOD_TAG, "0");
        meta.setMessageRetentionPeriod(Long.parseLong(messageRetentionPeriod));

        String topicURL = safeGetElementContent(root, TOPIC_URL_TAG, null);
        meta.setTopicURL(topicURL);

        String loggingEnabled = safeGetElementContent(root,
                LOGGING_ENABLED_TAG, "false");
        meta.setLoggingEnabled(Boolean.parseBoolean(loggingEnabled));

        return meta;
    }
}
