package com.aliyun.mns.common.comm;

import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.ServiceException;
import com.aliyun.mns.common.http.ResponseMessage;

/**
 * 对返回结果进行处理。
 */
public interface ResponseHandler {

    /**
     * 处理返回的结果
     */
    public void handle(ResponseMessage responseData)
            throws ServiceException, ClientException;
}
