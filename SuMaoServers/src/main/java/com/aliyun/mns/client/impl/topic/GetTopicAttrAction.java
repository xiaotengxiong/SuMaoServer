package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.request.topic.GetTopicAttrRequest;
import com.aliyun.mns.model.serialize.topic.TopicMetaDeserializer;

import java.net.URI;


public class GetTopicAttrAction extends AbstractAction<GetTopicAttrRequest, TopicMeta> {
    public GetTopicAttrAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "GetTopicAttribute", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(GetTopicAttrRequest request) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(request.getRequestPath());
        return requestMessage;
    }

    @Override
    protected ResultParser<TopicMeta> buildResultParser() {
        return new ResultParser<TopicMeta>() {
            @Override
            public TopicMeta parse(ResponseMessage response) throws ResultParseException {
                TopicMetaDeserializer deserializer = new TopicMetaDeserializer();
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    logger.warn("Unmarshal error,cause by:" + e.getMessage());
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }
}
