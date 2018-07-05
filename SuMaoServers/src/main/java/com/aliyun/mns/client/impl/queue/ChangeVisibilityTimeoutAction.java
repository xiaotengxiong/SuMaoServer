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
import com.aliyun.mns.model.request.queue.ChangeVisibilityTimeoutRequest;
import com.aliyun.mns.model.serialize.queue.MessageDeserializer;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;

public class ChangeVisibilityTimeoutAction extends
        AbstractAction<ChangeVisibilityTimeoutRequest, String> {

    public ChangeVisibilityTimeoutAction(ServiceClient client,
                                         ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "ChangeVisibilityTimeout", client, credentials,
                endpoint);
    }

    @Override
    protected RequestMessage buildRequest(
            ChangeVisibilityTimeoutRequest reqObject) throws ClientException {

        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath() + "/"
                + LOCATION_MESSAGES + "?ReceiptHandle="
                + reqObject.getReceiptHandle() + "&VisibilityTimeout="
                + reqObject.getVisibilityTimeout());
        return requestMessage;
    }


    @Override
    protected ResultParser<String> buildResultParser() {
        return new ResultParser<String>() {
            public String parse(ResponseMessage response)
                    throws ResultParseException {
                MessageDeserializer deserializer = new MessageDeserializer();
                try {
                    Message msg = deserializer.deserialize(response.getContent());

                    return msg.getReceiptHandle();
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }

}
