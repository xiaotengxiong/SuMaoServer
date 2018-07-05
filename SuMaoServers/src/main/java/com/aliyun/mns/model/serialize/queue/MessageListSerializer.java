package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.model.Message;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.*;

public class MessageListSerializer extends XMLSerializer<List<Message>> {

    @Override
    public InputStream serialize(List<Message> msgs, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element messages = doc.createElementNS(DEFAULT_XML_NAMESPACE, MESSAGE_LIST_TAG);

        doc.appendChild(messages);

        for (Message msg : msgs) {
            Element root = doc.createElement(MESSAGE_TAG);
            messages.appendChild(root);

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

        }
        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
