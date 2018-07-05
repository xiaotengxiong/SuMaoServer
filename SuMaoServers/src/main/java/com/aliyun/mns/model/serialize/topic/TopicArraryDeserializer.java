package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.TopicMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.NEXT_MARKER_TAG;
import static com.aliyun.mns.common.MNSConstants.TOPIC_TAG;


public class TopicArraryDeserializer extends AbstractTopicMetaDeserializer<PagingListResult<TopicMeta>> {
    public PagingListResult<TopicMeta> deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);
        NodeList list = doc.getElementsByTagName(TOPIC_TAG);
        List<TopicMeta> topics = new ArrayList<TopicMeta>();

        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            topics.add(parseMeta(e));
        }

        PagingListResult<TopicMeta> result = null;
        if (topics.size() > 0) {
            result = new PagingListResult<TopicMeta>();
            list = doc.getElementsByTagName(NEXT_MARKER_TAG);
            if (list.getLength() > 0) {
                result.setMarker(list.item(0).getTextContent());
            }
            result.setResult(topics);
        }
        return result;
    }
}
