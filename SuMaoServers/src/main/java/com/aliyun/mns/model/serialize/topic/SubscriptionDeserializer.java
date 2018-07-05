package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.SubscriptionMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;


public class SubscriptionDeserializer extends AbstractSubscriptionDeserializer<SubscriptionMeta> {
    public SubscriptionDeserializer() {
        super();
    }

    public SubscriptionMeta deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);
        Element root = doc.getDocumentElement();
        return parseMeta(root);
    }
}
