package com.aliyun.mns.model.serialize.account;

import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.serialize.XMLSerializer;
import com.aliyun.mns.model.serialize.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static com.aliyun.mns.common.MNSConstants.*;

public class AccountAttributesSerializer extends XMLSerializer<AccountAttributes> {

    @Override
    public InputStream serialize(AccountAttributes obj, String encoding)
            throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, ACCOUNT_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, LOGGING_BUCKET_TAG,
                obj.getLoggingBucket(), null);
        if (node != null) {
            root.appendChild(node);
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);
        return new ByteArrayInputStream(xml.getBytes(encoding));
    }
}
