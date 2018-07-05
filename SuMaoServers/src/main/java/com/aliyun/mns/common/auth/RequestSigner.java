package com.aliyun.mns.common.auth;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.http.RequestMessage;

public interface RequestSigner {

    public void sign(RequestMessage request)
            throws ClientException;
}
