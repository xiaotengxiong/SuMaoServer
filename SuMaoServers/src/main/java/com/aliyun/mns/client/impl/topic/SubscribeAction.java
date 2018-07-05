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
import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.request.topic.SubscribeRequest;
import com.aliyun.mns.model.serialize.topic.SubscriptionSerializer;

import java.io.InputStream;
import java.net.URI;


public class SubscribeAction extends AbstractAction<SubscribeRequest, String> {
    public SubscribeAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "Subscribe", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(SubscribeRequest reqObject) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        SubscriptionSerializer serializer = new SubscriptionSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getMeta(), MNSConstants.DEFAULT_CHARSET);
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
    protected boolean validate(SubscribeRequest request) {
        SubscriptionMeta meta = request.getMeta();
        if (meta.getEndpoint() == null) {
            logger.debug("endpoint is null");
            return false;
        }
        return Utils.checkSubscriptionName(meta.getSubscriptionName());
    }

}
