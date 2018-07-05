package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.request.topic.UnsubscribeRequest;

import java.net.URI;


public class UnsubscribeAction extends AbstractAction<UnsubscribeRequest, Void> {
    public UnsubscribeAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.DELETE, "Unsubscribe", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(UnsubscribeRequest request) throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(request.getRequestPath());
        return requestMessage;
    }
}
