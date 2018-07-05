package com.aliyun.mns.client.impl;

import com.aliyun.mns.client.AsyncCallback;
import com.aliyun.mns.client.AsyncResult;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.HttpMethod;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.auth.ServiceCredentials;
import com.aliyun.mns.common.http.ServiceClient;
import com.aliyun.mns.model.AbstractRequest;


public interface Action<T extends AbstractRequest, V> {
    public String getActionName();

    public HttpMethod getMethod();

    public ServiceClient getClient();

    public ServiceCredentials getCredentials();

    public AsyncResult<V> execute(T reqObject, AsyncCallback<V> asyncHandler) throws ClientException, ServiceException;

    public V execute(T reqObject) throws ClientException, ServiceException;
}
