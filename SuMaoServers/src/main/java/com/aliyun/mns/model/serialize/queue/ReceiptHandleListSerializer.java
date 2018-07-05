package com.aliyun.mns.model.serialize.queue;

import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.*;

public class ReceiptHandleListSerializer extends XMLSerializer<List<String>> {

    @Override
    public InputStream serialize(List<String> receipts, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, RECEIPT_HANDLE_LIST_TAG);

        doc.appendChild(root);

        for (String receipt : receipts) {
            Element node = safeCreateContentElement(doc, RECEIPT_HANDLE_TAG,
                    receipt, "");

            if (node != null) {
                root.appendChild(node);
            }
        }
        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
