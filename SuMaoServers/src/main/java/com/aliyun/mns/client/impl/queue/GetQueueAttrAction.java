package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.request.queue.GetQueueAttrRequest;
import com.aliyun.mns.model.serialize.queue.QueueMetaDeserializer;

import java.net.URI;

public class GetQueueAttrAction extends
        AbstractAction<GetQueueAttrRequest, QueueMeta> {

    public GetQueueAttrAction(ServiceClient client,
                              ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "GetQueueAttribute", client, credentials,
                endpoint);
    }

    @Override
    protected RequestMessage buildRequest(GetQueueAttrRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        return requestMessage;
    }

    @Override
    protected ResultParser<QueueMeta> buildResultParser() {
        return new ResultParser<QueueMeta>() {

            @Override
            public QueueMeta parse(ResponseMessage response)
                    throws ResultParseException {
                QueueMetaDeserializer deserializer = new QueueMetaDeserializer();
                try {
                    QueueMeta queueMeta = deserializer.deserialize(response
                            .getContent());

                    return queueMeta;
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }

}
