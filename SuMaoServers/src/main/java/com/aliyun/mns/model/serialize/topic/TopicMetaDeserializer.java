package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.TopicMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.InputStream;


public class TopicMetaDeserializer extends AbstractTopicMetaDeserializer<TopicMeta> {
    public TopicMetaDeserializer() {
        super();
    }

    @Override
    public TopicMeta deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);
        Element root = doc.getDocumentElement();
        return parseMeta(root);
    }
}
