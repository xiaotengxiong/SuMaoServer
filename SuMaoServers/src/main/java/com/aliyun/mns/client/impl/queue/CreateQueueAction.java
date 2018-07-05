package com.aliyun.mns.client.impl.queue;

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
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.request.queue.CreateQueueRequest;
import com.aliyun.mns.model.serialize.queue.QueueMetaSerializer;

import java.io.InputStream;
import java.net.URI;

public class CreateQueueAction extends
        AbstractAction<CreateQueueRequest, String> {

    public CreateQueueAction(ServiceClient client,
                             ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "CreateQueue", client, credentials, endpoint);

    }

    @Override
    protected RequestMessage buildRequest(CreateQueueRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());

        QueueMetaSerializer serializer = new QueueMetaSerializer();

        try {
            InputStream is = serializer.serialize(reqObject.getQueueMeta(),
                    MNSConstants.DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }

        return requestMessage;
    }

    @Override
    protected ResultParser<String> buildResultParser() {
        return new ResultParser<String>() {
            public String parse(ResponseMessage response)
                    throws ResultParseException {
                return response.getHeaders().get(MNSConstants.LOCATION);
            }
        };
    }

    @Override
    protected boolean validate(CreateQueueRequest request) {
        QueueMeta meta = request.getQueueMeta();
        return Utils.checkQueueName(meta.getQueueName());
    }


}
