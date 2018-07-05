package com.aliyun.mns.client.impl.account;

import com.aliyun.mns.client.impl.AbstractAction;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.AccountAttributes;
import com.aliyun.mns.model.request.account.GetAccountAttributesRequest;
import com.aliyun.mns.model.serialize.account.AccountAttributesDeserializer;

import java.net.URI;

public class GetAccountAttributesAction  extends
        AbstractAction<GetAccountAttributesRequest, AccountAttributes> {

    public GetAccountAttributesAction(ServiceClient client,
                              ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "GetAccountAttributes", client, credentials,
                endpoint);
    }

    @Override
    protected RequestMessage buildRequest(GetAccountAttributesRequest reqObject)
            throws ClientException {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setResourcePath("?accountmeta=true");
        return requestMessage;
    }

    @Override
    protected ResultParser<AccountAttributes> buildResultParser() {

        return new ResultParser<AccountAttributes>() {
            @Override
            public AccountAttributes parse(ResponseMessage response)
                    throws ResultParseException {
                AccountAttributesDeserializer deserializer = new AccountAttributesDeserializer();
                try {
                    AccountAttributes accountAttributes = deserializer.deserialize(response
                            .getContent());
                    return accountAttributes;
                } catch (Exception e) {
                    throw new ResultParseException("Unmarshal error,cause by:"
                            + e.getMessage(), e);
                }
            }
        };
    }
}
