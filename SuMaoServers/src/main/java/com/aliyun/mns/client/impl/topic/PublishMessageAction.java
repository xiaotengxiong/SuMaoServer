package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.Base64TopicMessage;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.mns.model.request.topic.PublishMessageRequest;
import com.aliyun.mns.model.serialize.topic.TopicMessageDeserializer;
import com.aliyun.mns.model.serialize.topic.TopicMessageSerializer;

import java.io.InputStream;
import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.DEFAULT_CHARSET;


public class PublishMessageAction extends AbstractAction<PublishMessageRequest, TopicMessage> {
    private TopicMessage.BodyType MessageType;

    public PublishMessageAction(ServiceClient client,
                                ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.POST, "PublishMessage", client, credentials, endpoint);
        MessageType = TopicMessage.BodyType.UNKNOW;

    }

    @Override
    protected RequestMessage buildRequest(PublishMessageRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        TopicMessageSerializer serializer = new TopicMessageSerializer();

        try {
            InputStream is = serializer.serialize(reqObject,
                    DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
        return requestMessage;
    }

    @Override
    protected boolean validate(PublishMessageRequest request)
            throws ClientException {
        TopicMessage message = request.getMessage();
        if (message == null) {
            logger.debug("message is null");
            return false;
        }
        if (message instanceof RawTopicMessage) {
            MessageType = TopicMessage.BodyType.STRING;
            RawTopicMessage rm = (RawTopicMessage) message;
            if (rm.getMessageBodyAsBytes() == null)
                return false;
        } else if (message instanceof Base64TopicMessage) {
            MessageType = TopicMessage.BodyType.BASE64;
            Base64TopicMessage bm = (Base64TopicMessage) message;
            if (bm.getMessageBodyAsBytes() == null)
                return false;
        } else {
            logger.warn("unknow message type");
            return false;
        }
        return super.validate(request);
    }

    @Override
    protected ResultParser<TopicMessage> buildResultParser() {
        return new ResultParser<TopicMessage>() {
            public TopicMessage parse(ResponseMessage response) throws ResultParseException {

                TopicMessageDeserializer deserializer = new TopicMessageDeserializer(MessageType);
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    logger.warn("Unmarshal error,cause by:" + e.getMessage());
                    throw new ResultParseException(
                            "Unmarshal error,cause by:" + e.getMessage(), e);
                }
            }
        };
    }
}
