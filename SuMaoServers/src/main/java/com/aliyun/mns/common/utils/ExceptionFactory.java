package com.aliyun.mns.common.utils;

import com.aliyun.mns.common.ClientErrorCode;
import com.aliyun.mns.common.ClientException;
import org.apache.http.conn.ConnectTimeoutException;

import java.io.IOException;
import java.net.SocketTimeoutException;

public class ExceptionFactory {
    public static ClientException createNetworkException(IOException ex) {
        String errorCode = ClientErrorCode.UNKNOWN;

        if (ex instanceof SocketTimeoutException) {
            errorCode = ClientErrorCode.SOCKET_TIMEOUT;
        } else if (ex instanceof ConnectTimeoutException) {
            errorCode = ClientErrorCode.CONNECTION_TIMEOUT;
        }

        return new ClientException(errorCode, ex.getMessage(), ex);
    }
}
