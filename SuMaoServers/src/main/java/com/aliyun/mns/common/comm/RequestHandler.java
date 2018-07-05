package com.aliyun.mns.common.comm;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.http.ServiceClient.Request;

/**
 * 对即将发送的请求数据进行预处理
 */
public interface RequestHandler {

    /**
     * 预处理需要发送的请求数据
     */
    public void handle(Request message)
            throws ServiceException, ClientException;
}
