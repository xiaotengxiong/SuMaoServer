package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.TopicMeta;
import com.aliyun.mns.model.request.topic.ListTopicRequest;
import com.aliyun.mns.model.serialize.topic.TopicArraryDeserializer;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.*;

public class ListTopicAction extends AbstractAction<ListTopicRequest, PagingListResult<TopicMeta>> {
    public ListTopicAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "ListTopic", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(ListTopicRequest reqObject) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath());
        if (reqObject.getPrefix() != null && !"".equals(reqObject.getPrefix())) {
            requestMessage.addHeader(X_HEADER_MNS_QUEUE_PREFIX, reqObject.getPrefix());
        }
        if (reqObject.getMarker() != null && !"".equals(reqObject.getMarker())) {
            requestMessage.addHeader(X_HEADER_MNS_MARKER, reqObject.getMarker());
        }
        if (reqObject.getMaxRet() != null) {
            requestMessage.addHeader(X_HEADER_MNS_RET_NUMBERS, reqObject.getMaxRet().toString());
        }
        if (reqObject.getWithMeta() != null) {
            requestMessage.addHeader(X_HEADER_MNS_WITH_META, reqObject.getWithMeta().toString());
        }

        return requestMessage;
    }

    protected ResultParser<PagingListResult<TopicMeta>> buildResultParser() {
        return new ResultParser<PagingListResult<TopicMeta>>() {
            @Override
            public PagingListResult<TopicMeta> parse(ResponseMessage response) throws ResultParseException {
                TopicArraryDeserializer deserializer = new TopicArraryDeserializer();
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    logger.warn("Unmarshal error,cause by:" + e.getMessage());
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }

            }
        };
    }
}