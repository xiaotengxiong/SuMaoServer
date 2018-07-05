package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.*;

public class TopicMetaSerializer extends XMLSerializer<TopicMeta> {
    @Override
    public InputStream serialize(TopicMeta obj, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();
        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, TOPIC_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, MAX_MESSAGE_SIZE_TAG, obj.getMaxMessageSize(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, LOGGING_ENABLED_TAG, obj.isLoggingEnabled(), null);
        if (node != null) {
            root.appendChild(node);
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
