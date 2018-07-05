package com.aliyun.mns.common.comm;

import com.aliyun.mns.common.auth.RequestSigner;
import com.aliyun.mns.common.utils.ServiceConstants;

import java.util.LinkedList;
import java.util.List;


/**
 *
 */

/**
 * The context information.
 *
 * @author xiaoming.yin
 */
public class ExecutionContext {
    private String charset = ServiceConstants.DEFAULT_ENCODING;
    private RequestSigner signer;
    // The request handlers that handle request content in as a pipeline
    private List<RequestHandler> requestHandlers = new LinkedList<RequestHandler>();
    // The response handlers that handle response message in as a pipeline.
    private List<ResponseHandler> responseHandlers = new LinkedList<ResponseHandler>();
    private RetryStrategy retryStrategy;

    /**
     * Constructor.
     */
    public ExecutionContext() {
    }

    public RetryStrategy getRetryStrategy() {
        return retryStrategy;
    }

    public void setRetryStrategy(RetryStrategy retryStrategy) {
        this.retryStrategy = retryStrategy;
    }

    /**
     * Returns the default encoding (charset). Default: "UTF-8"
     *
     * @return
     */
    public String getCharset() {
        return charset;
    }

    /**
     * Sets the default encoding (charset). Default: "UTF-8"
     *
     * @param defaultEncoding
     */
    public void setCharset(String defaultEncoding) {
        this.charset = defaultEncoding;
    }

    /**
     * @return the signer
     */
    public RequestSigner getSigner() {
        return signer;
    }

    /**
     * @param signer the signer to set
     */
    public void setSigner(RequestSigner signer) {
        this.signer = signer;
    }

    /**
     * @return the responseHandlers
     */
    public List<ResponseHandler> getResponseHandlers() {
        return responseHandlers;
    }

    public void addResponseHandler(ResponseHandler handler) {
        responseHandlers.add(handler);
    }

    public void insertResponseHandler(int position, ResponseHandler handler) {
        responseHandlers.add(position, handler);
    }

    public void removeResponseHandler(ResponseHandler handler) {
        responseHandlers.remove(handler);
    }

    /**
     * @return the requestHandlers
     */
    public List<RequestHandler> getResquestHandlers() {
        return requestHandlers;
    }

    public void addRequestHandler(RequestHandler handler) {
        requestHandlers.add(handler);
    }

    public void insertRequestHandler(int position, RequestHandler handler) {
        requestHandlers.add(position, handler);
    }

    public void removeRequestHandler(RequestHandler handler) {
        requestHandlers.remove(handler);
    }
}
