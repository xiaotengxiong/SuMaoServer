package com.aliyun.mns.client.impl.queue;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.PagingListResult;
import com.aliyun.mns.model.QueueMeta;
import com.aliyun.mns.model.request.queue.ListQueueRequest;
import com.aliyun.mns.model.serialize.queue.QueueArrayDeserializer;

import java.net.URI;

import static com.aliyun.mns.common.MNSConstants.*;

public class ListQueueAction extends AbstractAction<ListQueueRequest, PagingListResult<QueueMeta>> {

    public ListQueueAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "ListQueue", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(ListQueueRequest reqObject)
            throws ClientException {
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


    @Override
    protected ResultParser<PagingListResult<QueueMeta>> buildResultParser() {
        return new ResultParser<PagingListResult<QueueMeta>>() {
            public PagingListResult<QueueMeta> parse(ResponseMessage response)
                    throws ResultParseException {
                QueueArrayDeserializer deserializer = new QueueArrayDeserializer();
                try {
                    return deserializer.deserialize(response.getContent());
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }

}
