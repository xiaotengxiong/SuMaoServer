package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.common.utils.BooleanSerializer;
import com.aliyun.mns.model.MessageAttributes;
import com.aliyun.mns.model.TopicMessage;
import com.aliyun.mns.model.request.topic.PublishMessageRequest;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.*;

public class TopicMessageSerializer extends XMLSerializer<PublishMessageRequest> {
    private static Gson gson = null;

    private synchronized Gson getGson() {
        if (gson == null) {
            GsonBuilder b = new GsonBuilder();
            b.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE);
            BooleanSerializer serializer = new BooleanSerializer();
            b.registerTypeAdapter(Boolean.class, serializer);
            b.registerTypeAdapter(boolean.class, serializer);
            gson = b.create();
        }
        return gson;
    }

    @Override
    public InputStream serialize(PublishMessageRequest request, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        TopicMessage msg = request.getMessage();
        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, MESSAGE_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, MESSAGE_BODY_TAG,
                msg.getMessageBody(), "");
        if (node != null) {
            root.appendChild(node);
        }
        
        node = safeCreateContentElement(doc, MESSAGE_TAG_TAG, msg.getMessageTag(), null);
        if (node != null) {
            root.appendChild(node);
        }

        MessageAttributes messageAttributes = request.getMessageAttributes();
        if (messageAttributes != null && messageAttributes.getMailAttributes() != null)
        {
            Element attributesNode = doc.createElement(MESSAGE_ATTRIBUTES_TAG);
            root.appendChild(attributesNode);

            node = safeCreateContentElement(doc, DIRECT_MAIL_TAG, messageAttributes.getMailAttributes().toJson(getGson()), null);
            if (node != null) {
                attributesNode.appendChild(node);
            }
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
