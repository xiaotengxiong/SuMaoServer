package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.Utils;
import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.request.topic.SetTopicAttrRequest;
import com.aliyun.mns.model.serialize.topic.TopicMetaSerializer;

import java.io.InputStream;
import java.net.URI;


public class SetTopicAttrAction extends AbstractAction<SetTopicAttrRequest, Void> {
    public SetTopicAttrAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "SetTopicAttributes", client, credentials, endpoint);
    }

    @Override
    protected boolean validate(SetTopicAttrRequest request) {
        TopicMeta meta = request.getTopicMeta();
        if (!Utils.checkTopicName(meta.getTopicName())) {
            logger.debug("topic name format error");
            return false;
        }
        Long maxSize = meta.getMaxMessageSize();
        if (maxSize != null
                && (maxSize < 0
                    || maxSize > MNSConstants.MAX_MESSAGE_SIZE)) {
            logger.debug("max message size error");
            return false;
        }
        Long period = meta.getMessageRetentionPeriod();
        if (period != null
                && (period < MNSConstants.MIN_MESSAGE_RETENTION_PERIOD
                    || period > MNSConstants.MAX_MESSAGE_RETENTION_PERIOD)) {
            logger.debug("message retention period error");
            return false;
        }
        return true;
    }

    @Override
    protected RequestMessage buildRequest(SetTopicAttrRequest reqObject) throws ClientException {
        RequestMessage message = new RequestMessage();
        message.setResourcePath(reqObject.getRequestPath() + "?metaoverride=true");
        TopicMetaSerializer serializer = new TopicMetaSerializer();
        TopicMeta meta = reqObject.getTopicMeta();
        try {
            InputStream is = serializer.serialize(meta, MNSConstants.DEFAULT_CHARSET);
            message.setContent(is);
            message.setContentLength(is.available());
            return message;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }

}
