package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.request.queue.SetQueueAttrRequest;
import com.aliyun.mns.model.serialize.queue.QueueMetaSerializer;

import java.io.InputStream;
import java.net.URI;

public class SetQueueAttrAction extends
        AbstractAction<SetQueueAttrRequest, String> {

    public SetQueueAttrAction(ServiceClient client,
                              ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "SetQueueAttributes", client, credentials,
                endpoint);
    }

    @Override
    protected boolean validate(SetQueueAttrRequest request) throws ClientException {
        //TODO validate queue attribute's value
        return true;
    }

    @Override
    protected RequestMessage buildRequest(SetQueueAttrRequest reqObject)
            throws ClientException {
        RequestMessage message = new RequestMessage();
        message.setResourcePath(reqObject.getRequestPath() + "?metaoverride=true");
        QueueMetaSerializer serializer = new QueueMetaSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getQueueMeta(), MNSConstants.DEFAULT_CHARSET);
            message.setContent(is);
            message.setContentLength(is.available());
            return message;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }

}
