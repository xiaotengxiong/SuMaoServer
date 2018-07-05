package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Element;

import java.util.Date;

import static com.aliyun.mns.common.MNSConstants.*;

public abstract class AbstractQueueMetaDeserializer<T> extends
        XMLDeserializer<T> {

    public AbstractQueueMetaDeserializer() {
        super();
    }

    protected QueueMeta parseQueueMeta(Element root) {
        QueueMeta meta = new QueueMeta();

        String queueName = safeGetElementContent(root, QUEUE_NAME_TAG, null);
        meta.setQueueName(queueName);

        String delaySeconds = safeGetElementContent(root, DELAY_SECONDS_TAG,
                "0");
        meta.setDelaySeconds(Long.parseLong(delaySeconds));

        String maxMessageSize = safeGetElementContent(root,
                MAX_MESSAGE_SIZE_TAG, "0");
        meta.setMaxMessageSize(Long.parseLong(maxMessageSize));

        String messageRetentionPeriod = safeGetElementContent(root,
                MESSAGE_RETENTION_PERIOD_TAG, "0");
        meta.setMessageRetentionPeriod(Long.parseLong(messageRetentionPeriod));

        String visibiltyTimeout = safeGetElementContent(root,
                VISIBILITY_TIMEOUT, "0");
        meta.setVisibilityTimeout(Long.parseLong(visibiltyTimeout));

        String createTime = safeGetElementContent(root, CREATE_TIME_TAG, "0");
        meta.setCreateTime(new Date(Long.parseLong(createTime) * 1000));

        String lastModifyTime = safeGetElementContent(root, LASTMODIFYTIME_TAG,
                "0");
        meta.setLastModifyTime(new Date(Long.parseLong(lastModifyTime) * 1000));

        String waitSeconds = safeGetElementContent(root, POLLING_WAITSECONDS_TAG,
                "0");
        meta.setPollingWaitSeconds(Integer.parseInt(waitSeconds));

        String activeMessages = safeGetElementContent(root,
                ACTIVE_MESSAGES_TAG, "0");
        meta.setActiveMessages(Long.parseLong(activeMessages));

        String inactiveMessages = safeGetElementContent(root,
                INACTIVE_MESSAGES_TAG, "0");
        meta.setInactiveMessages(Long.parseLong(inactiveMessages));

        String delayMessages = safeGetElementContent(root, DELAY_MESSAGES_TAG,
                "0");
        meta.setDelayMessages(Long.parseLong(delayMessages));

        String queueURL = safeGetElementContent(root, QUEUE_URL_TAG, null);
        meta.setQueueURL(queueURL);

        String loggingEnabled = safeGetElementContent(root, LOGGING_ENABLED_TAG,
                "false");
        meta.setLoggingEnabled(Boolean.parseBoolean(loggingEnabled));

        return meta;
    }

}