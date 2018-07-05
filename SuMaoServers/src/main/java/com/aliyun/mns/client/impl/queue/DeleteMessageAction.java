package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.request.queue.DeleteMessageRequest;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;

public class DeleteMessageAction extends
        AbstractAction<DeleteMessageRequest, Void> {

    public DeleteMessageAction(ServiceClient client,
                               ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.DELETE, "DeleteMessage", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(DeleteMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath() + "/"
                + LOCATION_MESSAGES + "?ReceiptHandle="
                + reqObject.getReceiptHandle());

        return requestMessage;
    }

    @Override
    protected ResultParser<Void> buildResultParser() {
        return null;
    }
}
