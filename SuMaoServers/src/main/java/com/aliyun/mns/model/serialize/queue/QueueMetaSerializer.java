package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.*;

public class QueueMetaSerializer extends XMLSerializer<QueueMeta> {

    @Override
    public InputStream serialize(QueueMeta obj, String encoding)
            throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, QUEUE_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, DELAY_SECONDS_TAG,
                obj.getDelaySeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, VISIBILITY_TIMEOUT,
                obj.getVisibilityTimeout(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MAX_MESSAGE_SIZE_TAG,
                obj.getMaxMessageSize(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, MESSAGE_RETENTION_PERIOD_TAG,
                obj.getMessageRetentionPeriod(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, POLLING_WAITSECONDS_TAG,
                obj.getPollingWaitSeconds(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, LOGGING_ENABLED_TAG,
                obj.isLoggingEnabled(), null);
        if (node != null) {
            root.appendChild(node);
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }

}
