package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.request.topic.GetSubscriptionAttrRequest;
import com.aliyun.mns.model.serialize.topic.SubscriptionDeserializer;

import java.net.URI;


public class GetSubscriptionAttrAction extends AbstractAction<GetSubscriptionAttrRequest, SubscriptionMeta> {
    public GetSubscriptionAttrAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "GetSubscriptionAttribute", client, credentials, endpoint);
    }

    public RequestMessage buildRequest(GetSubscriptionAttrRequest request) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(request.getRequestPath());
        return requestMessage;
    }

    @Override
    protected ResultParser<SubscriptionMeta> buildResultParser() {
        return new ResultParser<SubscriptionMeta>() {
            @Override
            public SubscriptionMeta parse(ResponseMessage response) throws ResultParseException {
                SubscriptionDeserializer deserializer = new SubscriptionDeserializer();
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
