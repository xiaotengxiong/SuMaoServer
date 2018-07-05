package com.aliyun.mns.client.impl.topic;

import com.aliyun.mns.client.Utils;
import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.SubscriptionMeta;
import com.aliyun.mns.model.request.topic.SetSubscriptionAttrRequest;
import com.aliyun.mns.model.serialize.topic.SubscriptionSerializer;
import com.aliyun.mns.model.serialize.topic.UpdateSubscriptionSerializer;

import java.io.InputStream;
import java.net.URI;


public class SetSubscriptionAttrAction extends AbstractAction<SetSubscriptionAttrRequest, Void> {
    public SetSubscriptionAttrAction(ServiceClient client, ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "SetSubscriptionAttributes", client, credentials, endpoint);
    }

    @Override
    protected boolean validate(SetSubscriptionAttrRequest request) {
        SubscriptionMeta meta = request.getMeta();
        return Utils.checkSubscriptionName(meta.getSubscriptionName());
    }

    @Override
    protected RequestMessage buildRequest(SetSubscriptionAttrRequest reqObject) {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath(reqObject.getRequestPath() + "?metaoverride=true");
        UpdateSubscriptionSerializer serializer = new UpdateSubscriptionSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getMeta(), MNSConstants.DEFAULT_CHARSET);
            requestMessage.setContent(is);
            requestMessage.setContentLength(is.available());
        } catch (Exception e) {
            e.printStackTrace();
            throw new ClientException(e.getMessage(), e);
        }
        return requestMessage;
    }
}
