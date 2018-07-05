package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static com.aliyun.mns.common.MNSConstants.*;

public class SubscriptionSerializer extends XMLSerializer<SubscriptionMeta> {

    public InputStream serialize(SubscriptionMeta obj, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();
        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, SUBSCRIPTION_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, NOTIFY_STRATEGY_TAG, obj.getNotifyStrategy(), null);
        if (node != null) {
            root.appendChild(node);
        }

        node = safeCreateContentElement(doc, ENDPOINT_TAG, obj.getEndpoint(), null);
        if (node != null) {
            root.appendChild(node);
        }
        
        node = safeCreateContentElement(doc, NOTIFY_CONTENT_FORMAT_TAG, obj.getNotifyContentFormat(), null);
        if (node != null) {
            root.appendChild(node);
        }
        
        node = safeCreateContentElement(doc, FILTER_TAG_TAG, obj.getFilterTag(), null);
        if (node != null) {
            root.appendChild(node);
        }
        
        String xml = XmlUtil.xmlNodeToString(doc, encoding);
        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
