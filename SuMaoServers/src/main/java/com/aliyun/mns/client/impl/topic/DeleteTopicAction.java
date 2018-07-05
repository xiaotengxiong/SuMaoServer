package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.request.topic.DeleteTopicRequest;

import java.net.URI;


public class DeleteTopicAction extends AbstractAction<DeleteTopicRequest, Void> {
    public DeleteTopicAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.DELETE, "DeleteTopic", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(DeleteTopicRequest request) throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(request.getRequestPath());
        return requestMessage;
    }

}
