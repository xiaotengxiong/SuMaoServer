package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.Utils;
import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.request.topic.CreateTopicRequest;
import com.aliyun.mns.model.serialize.topic.TopicMetaSerializer;

import java.io.InputStream;
import java.net.URI;


public class CreateTopicAction extends AbstractAction<CreateTopicRequest, String> {
    public CreateTopicAction(ServiceClient client,
                             ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "CreateTopic", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(CreateTopicRequest reqObject) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        TopicMetaSerializer serializer = new TopicMetaSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getTopicMeta(), MNSConstants.DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientException(e.getMessage(), e);
        }

        return requestMessage;
    }

    @Override
    protected ResultParser<String> buildResultParser() {
        return new ResultParser<String>() {
            @Override
            public String parse(ResponseMessage response) throws ResultParseException {
                return response.getHeaders().get(MNSConstants.LOCATION);
            }
        };
    }

    @Override
    protected boolean validate(CreateTopicRequest request) {
        TopicMeta meta = request.getTopicMeta();
        if (!Utils.checkTopicName(meta.getTopicName())) {
            return false;
        }
        Long maxSize = meta.getMaxMessageSize();
        if (maxSize != null
                && (maxSize < 0
                    || maxSize > MNSConstants.MAX_MESSAGE_SIZE)) {
            logger.debug("max message size error, not in 1~" + MNSConstants.MAX_MESSAGE_SIZE);
            return false;
        }
        Long period = meta.getMessageRetentionPeriod();
        if (period != null
                && (period < MNSConstants.MIN_MESSAGE_RETENTION_PERIOD
                    || period > MNSConstants.MAX_MESSAGE_RETENTION_PERIOD)) {
            logger.debug("message retention period error, not in " + MNSConstants.MIN_MESSAGE_RETENTION_PERIOD + "~" + MNSConstants.MAX_MESSAGE_RETENTION_PERIOD);
            return false;
        }
        return true;
    }
}
