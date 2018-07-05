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
import com.aliyun.mns.model.request.queue.ReceiveMessageRequest;
import com.aliyun.mns.model.serialize.queue.MessageDeserializer;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;
import static com.aliyun.mns.common.MNSConstants.PARAM_WAITSECONDS;
import static com.aliyun.mns.common.MNSConstants.X_HEADER_MNS_REQUEST_ID;

public class ReceiveMessageAction extends
        AbstractAction<ReceiveMessageRequest, Message> {

    public ReceiveMessageAction(ServiceClient client,
                                ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "ReceiveMessage", client, credentials, endpoint);

    }

    @Override
    protected RequestMessage buildRequest(ReceiveMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();

        String uri = reqObject.getRequestPath() + "/" + LOCATION_MESSAGES;
        if (reqObject.getWaitSeconds() != null && reqObject.getWaitSeconds() >= 0) {
            uri += "?" + PARAM_WAITSECONDS + "=" + reqObject.getWaitSeconds();
        }

        requestMessage.setResourcePath(uri);
        return requestMessage;
    }

    @Override
    protected ResultParser<Message> buildResultParser() {
        return new ResultParser<Message>() {
            public Message parse(ResponseMessage response) throws ResultParseException {
                MessageDeserializer deserializer = new MessageDeserializer();
                try {
                    Message msg = deserializer.deserialize(response.getContent());
                    msg.setRequestId(response.getHeader(X_HEADER_MNS_REQUEST_ID));
                    return msg;
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }

}
