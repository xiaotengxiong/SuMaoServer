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
import com.aliyun.mns.model.request.queue.BatchDeleteMessageRequest;
import com.aliyun.mns.model.serialize.queue.ErrorReceiptHandleListDeserializer;
import com.aliyun.mns.model.serialize.queue.ReceiptHandleListSerializer;

import java.io.InputStream;
import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.DEFAULT_CHARSET;
import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;

public class BatchDeleteMessageAction extends
        AbstractAction<BatchDeleteMessageRequest, Void> {

    public BatchDeleteMessageAction(ServiceClient client,
                                    ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.DELETE, "BatchDeleteMessage", client, credentials,
                endpoint);
    }

    @Override
    protected RequestMessage buildRequest(BatchDeleteMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath() + "/"
                + LOCATION_MESSAGES);

        try {
            ReceiptHandleListSerializer serializer = new ReceiptHandleListSerializer();
            InputStream is = serializer.serialize(
                    reqObject.getReceiptHandles(), DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
        return requestMessage;
    }

    @Override
    protected ResultParser<Void> buildResultParser() {
        return null;
    }

    @Override
    protected ResultParser<Exception> buildExceptionParser() {
        return new ResultParser<Exception>() {
            public Exception parse(ResponseMessage response)
                    throws ResultParseException {
                ErrorReceiptHandleListDeserializer deserializer = new ErrorReceiptHandleListDeserializer();
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:" + e.getMessage(), e);
                }
            }
        };
    }
}
