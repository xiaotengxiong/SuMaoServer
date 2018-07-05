package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ExceptionResultParser;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.request.queue.BatchSendMessageRequest;
import com.aliyun.mns.model.serialize.queue.ErrorMessageListDeserializer;
import com.aliyun.mns.model.serialize.queue.MessageListDeserializer;
import com.aliyun.mns.model.serialize.queue.MessageListSerializer;

import java.io.InputStream;
import java.net.URI;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.DEFAULT_CHARSET;
import static com.aliyun.mns.common.MNSConstants.LOCATION_MESSAGES;


public class BatchSendMessageAction extends
        AbstractAction<BatchSendMessageRequest, List<Message>> {

    public BatchSendMessageAction(ServiceClient client,
                                  ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.POST, "BatchSendMessage", client, credentials,
                endpoint);
    }

    @Override
    protected RequestMessage buildRequest(BatchSendMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath() + "/"
                + LOCATION_MESSAGES);
        MessageListSerializer serializer = new MessageListSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getMessages(),
                    DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
        return requestMessage;
    }

    @Override
    protected boolean validate(BatchSendMessageRequest request)
            throws ClientException {
        for (Message message : request.getMessages()) {
            if (message == null || message.getMessageBodyAsString() == null) {
                throw new ClientException("MessageBody can not be null");
            }
        }
        return super.validate(request);
    }

    @Override
    protected ResultParser<List<Message>> buildResultParser() {
        return new ResultParser<List<Message>>() {
            public List<Message> parse(ResponseMessage response)
                    throws ResultParseException {
                MessageListDeserializer deserializer = new MessageListDeserializer();
                try {
                    return deserializer.deserialize(response
                            .getContent());
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }

    @Override
    protected ResultParser<Exception> buildExceptionParser() {
        return new ResultParser<Exception>() {
            public Exception parse(ResponseMessage response)
                    throws ResultParseException {
                ErrorMessageListDeserializer deserializer = new ErrorMessageListDeserializer();
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    return new ExceptionResultParser().parse(response); //TODO right?
                }
            }
        };
    }
}
