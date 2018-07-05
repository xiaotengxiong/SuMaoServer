/**
 * Copyright (C) Alibaba Cloud Computing
 * All rights reserved.
 *
 * 版权所有 （C）阿里云计算有限公司
 */

package com.aliyun.mns.common.http;

import com.aliyun.mns.common.ClientErrorCode;
import com.aliyun.mns.common.ClientException;
import com.aliyun.mns.common.comm.ExecutionContext;
import com.aliyun.mns.common.comm.RetryStrategy;
import com.aliyun.mns.common.http.HttpFactory.IdleConnectionMonitor;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.nio.client.HttpAsyncClient;

import java.io.IOException;
import java.util.concurrent.Future;

/**
 * The default implementation of <code>ServiceClient</code>.
 */
public class DefaultServiceClient extends ServiceClient {

    boolean clientIsOpen;
    private HttpAsyncClient httpClient;
    private PoolingNHttpClientConnectionManager connManager;

    public DefaultServiceClient(ClientConfiguration config) {
        super(config);
        clientIsOpen = false;
        connManager = HttpFactory.createConnectionManager(config);
        httpClient = HttpFactory.createHttpAsyncClient(connManager, config);
        this.open();
    }

    @Override
    public <T> Future<HttpResponse> sendRequestCore(
            ServiceClient.Request request, ExecutionContext context,
            HttpCallback<T> callback) throws IOException {
        assert request != null && context != null;

        HttpRequestBase httpRequest = HttpFactory.createHttpRequest(
                request, context);

        // Execute request, make the exception to the standard WebException
        return httpClient.execute(httpRequest, callback);
    }

    @Override
    public void open() {
        if (this.httpClient != null
                && this.httpClient instanceof CloseableHttpAsyncClient
                && !clientIsOpen) {
            // start a thread to clean idle and expired connection
            IdleConnectionMonitor.getInstance().addConnMgr(connManager);
            ((CloseableHttpAsyncClient) httpClient).start();
            clientIsOpen = true;
        }

    }

    @Override
    public boolean isOpen() {
        return clientIsOpen;
    }

    /**
     * @deprecated use open instead
     */
    public void start() {
        this.open();
    }

    @Override
    public void close() {
        if (this.httpClient != null
                && this.httpClient instanceof CloseableHttpAsyncClient) {
            try {
                ((CloseableHttpAsyncClient) httpClient).close();
                HttpFactory.IdleConnectionMonitor.getInstance().removeConnMgr(connManager);
                clientIsOpen = false;
            } catch (IOException e) { // quietly
            }
        }
    }

    protected RetryStrategy getDefaultRetryStrategy() {
        return new DefaultRetryStrategy();
    }

    private static class DefaultRetryStrategy extends RetryStrategy {

        @Override
        public boolean shouldRetry(Exception ex, RequestMessage request,
                                   ResponseMessage response, int retries) {
            if (ex instanceof ClientException) {
                String errorCode = ((ClientException) ex).getErrorCode();
                if (errorCode.equals(ClientErrorCode.CONNECTION_TIMEOUT)
                        || errorCode.equals(ClientErrorCode.SOCKET_TIMEOUT)) {
                    return true;
                }
            }

            if (response != null) {
                int statusCode = response.getStatusCode();
                if (statusCode == HttpStatus.SC_INTERNAL_SERVER_ERROR
                        || statusCode == HttpStatus.SC_SERVICE_UNAVAILABLE) {
                    return true;
                }
            }

            return false;
        }
    }
}
