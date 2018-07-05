package com.aliyun.mns.common.comm;

import com.aliyun.mns.common.http.RequestMessage;
import com.aliyun.mns.common.http.ResponseMessage;

public class NoRetryStrategy extends RetryStrategy {

    @Override
    public boolean shouldRetry(Exception ex, RequestMessage request,
                               ResponseMessage response, int retries) {
        return false;
    }

}
