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
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.request.queue.BatchReceiveMessageRequest;
import com.aliyun.mns.model.serialize.queue.MessageListDeserializer;

import java.net.URI;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;
import static com.aliyun.mns.common.MNSConstants.PARAM_WAITSECONDS;
import static com.aliyun.mns.common.MNSConstants.X_HEADER_MNS_REQUEST_ID;

public class BatchReceiveMessageAction extends
        AbstractAction<BatchReceiveMessageRequest, List<Message>> {

    public BatchReceiveMessageAction(ServiceClient client,
                                     ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "BatchReceiveMessage", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(BatchReceiveMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();

        String uri = reqObject.getRequestPath() + "/" + LOCATION_MESSAGES
                + "?numOfMessages=" + reqObject.getBatchSize();
        if (reqObject.getWaitSeconds() > 0) {
            uri += "&" + PARAM_WAITSECONDS + "=" + reqObject.getWaitSeconds();
        }

        requestMessage.setResourcePath(uri);
        return requestMessage;
    }

    @Override
    protected boolean validate(BatchReceiveMessageRequest request) {
        if (request.getBatchSize() <= 0) {
            throw new ClientException("BatchSize is invalid");
        }
        if (request.getWaitSeconds() < 0) {
            throw new ClientException("WaitSeconds is invalid");
        }
        return super.validate(request);
    }

    @Override
    protected ResultParser<List<Message>> buildResultParser() {
        return new ResultParser<List<Message>>() {
            public List<Message> parse(ResponseMessage response) throws ResultParseException {
                MessageListDeserializer deserializer = new MessageListDeserializer();
                try {
                    List<Message> msgs = deserializer.deserialize(response.getContent());
                    for (Message msg : msgs) {
                        msg.setRequestId(response.getHeader(X_HEADER_MNS_REQUEST_ID));
                    }
                    return msgs;
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }
}
