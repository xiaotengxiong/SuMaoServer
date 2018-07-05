package com.aliyun.mns.model.serialize.topic;


import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.model.Base64TopicMessage;
import com.aliyun.mns.model.RawTopicMessage;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.mns.model.serialize.XMLDeserializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;
import java.security.InvalidParameterException;

import static com.aliyun.mns.common.MNSConstants.MESSAGE_BODY_MD5_TAG;
import static com.aliyun.mns.common.MNSConstants.MESSAGE_ID_TAG;

public class TopicMessageDeserializer extends XMLDeserializer<TopicMessage> {
    private TopicMessage.BodyType MessageType;

    public TopicMessageDeserializer(TopicMessage.BodyType type) {
        this.MessageType = type;
    }

    public TopicMessage deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);

        Element root = doc.getDocumentElement();
        return parseMessage(root);


    }

    private TopicMessage parseMessage(Element root) throws ClientException {
        TopicMessage message = null;
        if (MessageType == TopicMessage.BodyType.BASE64) {
            message = new Base64TopicMessage();
        } else if (MessageType == TopicMessage.BodyType.STRING) {
            message = new RawTopicMessage();
        } else {
            throw new InvalidParameterException("unknow message type: " + MessageType);
        }

        String messageId = safeGetElementContent(root, MESSAGE_ID_TAG, null);
        message.setMessageId(messageId);

        String messageBodyMD5 = safeGetElementContent(root,
                MESSAGE_BODY_MD5_TAG, null);
        message.setMessageBodyMD5(messageBodyMD5);

        return message;
    }
}
