package com.aliyun.mns.model.serialize.topic;

import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.SubscriptionMeta;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.aliyun.mns.common.MNSConstants.NEXT_MARKER_TAG;
import static com.aliyun.mns.common.MNSConstants.SUBSCRIPTION_TAG;


public class SubscriptionArraryDeserializer extends AbstractSubscriptionDeserializer<PagingListResult<SubscriptionMeta>> {
    public PagingListResult<SubscriptionMeta> deserialize(InputStream stream) throws Exception {
        Document doc = getDocmentBuilder().parse(stream);
        NodeList list = doc.getElementsByTagName(SUBSCRIPTION_TAG);
        List<SubscriptionMeta> topics = new ArrayList<SubscriptionMeta>();

        for (int i = 0; i < list.getLength(); i++) {
            Element e = (Element) list.item(i);
            topics.add(parseMeta(e));
        }

        PagingListResult<SubscriptionMeta> result = null;
        if (topics.size() > 0) {
            result = new PagingListResult<SubscriptionMeta>();
            list = doc.getElementsByTagName(NEXT_MARKER_TAG);
            if (list.getLength() > 0) {
                result.setMarker(list.item(0).getTextContent());
            }
            result.setResult(topics);
        }
        return result;
    }
}
