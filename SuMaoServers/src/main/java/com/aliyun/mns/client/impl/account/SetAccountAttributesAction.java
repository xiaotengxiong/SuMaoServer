package com.aliyun.mns.client.impl.account;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.MNSConstants;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.request.account.SetAccountAttributesRequest;
import com.aliyun.mns.model.serialize.account.AccountAttributesDeserializer;
import com.aliyun.mns.model.serialize.account.AccountAttributesSerializer;

import java.io.InputStream;
import java.net.URI;

public class SetAccountAttributesAction extends
        AbstractAction<SetAccountAttributesRequest, String> {

    public SetAccountAttributesAction(ServiceClient client,
                              ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.PUT, "SetAccountAttributes", client, credentials,
                endpoint);
    }

    @Override
    protected boolean validate(SetAccountAttributesRequest request) throws ClientException {
        //TODO validate queue attribute's value
        return true;
    }

    @Override
    protected RequestMessage buildRequest(SetAccountAttributesRequest reqObject)
            throws ClientException {
        RequestMessage message = new RequestMessage();
        message.setResourcePath("?accountmeta=true");
        AccountAttributesSerializer serializer = new AccountAttributesSerializer();
        try {
            InputStream is = serializer.serialize(reqObject.getAccountAttributes(), MNSConstants.DEFAULT_CHARSET);
            message.setContent(is);
            message.setContentLength(is.available());
            return message;
        } catch (Exception e) {
            throw new ClientException(e.getMessage(), e);
        }
    }
}
