package com.aliyun.mns.client.impl;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.common.parser.ResultParseException;
import com.aliyun.mns.common.parser.ResultParser;
import com.aliyun.mns.model.AccountDeserializer;
import com.aliyun.mns.model.GetAccountRequest;

import java.net.URI;

public class GetAccountAction extends AbstractAction<GetAccountRequest, String> {

    public GetAccountAction(ServiceClient client,
                            ServiceCredentials credentials, URI endpoint) {
        super(HttpMethod.GET, "GetAccount", client, credentials, endpoint);
    }

    @Override
    protected RequestMessage buildRequest(GetAccountRequest reqObject)
            throws ClientException {
        RequestMessage request = new RequestMessage();
        request.setResourcePath(reqObject.getRequestPath() + "?account");
        return request;
    }

    @Override
    protected ResultParser<String> buildResultParser() {
        return new ResultParser<String>() {
            public String parse(ResponseMessage response)
                    throws ResultParseException {
                AccountDeserializer deserializer = new AccountDeserializer();
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
