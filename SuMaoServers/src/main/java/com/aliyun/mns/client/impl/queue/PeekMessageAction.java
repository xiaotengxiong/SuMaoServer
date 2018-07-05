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
import com.aliyun.mns.model.request.queue.PeekMessageRequest;
import com.aliyun.mns.model.serialize.queue.MessageDeserializer;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;
import static com.aliyun.mns.common.MNSConstants.X_HEADER_MNS_REQUEST_ID;

public class PeekMessageAction extends
        AbstractAction<PeekMessageRequest, Message> {

    public PeekMessageAction(ServiceClient client,
                             ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "PeekMessage", client, credentials, endpoint);

    }

    @Override
    protected RequestMessage buildRequest(PeekMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        String uri = reqObject.getRequestPath() + "/" + LOCATION_MESSAGES + "?peekonly=true";

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
