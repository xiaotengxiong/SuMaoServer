package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.request.queue.DeleteQueueRequest;

import java.net.URI;

public class DeleteQueueAction extends AbstractAction<DeleteQueueRequest, Void> {

    public DeleteQueueAction(ServiceClient client,
                             ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.DELETE, "DeleteQueue", client, credentials, endpoint);
    }


    @Override
    protected RequestMessage buildRequest(DeleteQueueRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        return requestMessage;
    }

}
