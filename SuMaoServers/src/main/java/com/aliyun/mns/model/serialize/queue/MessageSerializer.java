package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.*;

public class MessageSerializer extends XMLSerializer<Message> {

    public MessageSerializer() {
        super();
    }

    @Override
    public InputStream serialize(Message msg, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, MESSAGE_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, MESSAGE_BODY_TAG,
                msg.getMessageBodyAsRawString(), "");

        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, DELAY_SECONDS_TAG,
                msg.getDelaySeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }


        node = safeCreateContentElement(doc, PRIORITY_TAG,
                msg.getPriority(), null);
        if (node != null) {
            root.appendChild(node);
        }


        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
