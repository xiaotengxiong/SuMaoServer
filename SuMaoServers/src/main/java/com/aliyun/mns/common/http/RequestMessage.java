/**
 * filename: OpenTableServiceClient.java
 *
 * Copyright (C) Alibaba Cloud Computing, 2012
 * All rights reserved.
 *
 * 版权所有 （C）阿里巴巴云计算，2012
 */

package com.aliyun.mns.common.http;

import com.aliyun.mns.common.HttpMethod;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import static com.aliyun.mns.common.utils.CodingUtils.assertParameterNotNull;
import static com.aliyun.mns.common.utils.CodingUtils.assertStringNotNullOrEmpty;


/**
 * 表示发送请求的信息。
 */
public class RequestMessage extends HttpMesssage {
    private HttpMethod method = HttpMethod.GET; // HTTP Method. default GET.
    private URI endpoint;
    private String resourcePath;
    private Map<String, String> parameters = new HashMap<String, String>();

    /**
     * 构造函数。
     */
    public RequestMessage() {
    }

    /**
     * 获取HTTP的请求方法。
     *
     * @return HTTP的请求方法。
     */
    public HttpMethod getMethod() {
        return method;
    }

    /**
     * 设置HTTP的请求方法。
     *
     * @param method HTTP的请求方法。
     */
    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    /**
     * @return the endpoint
     */
    public URI getEndpoint() {
        return endpoint;
    }

    /**
     * @param endpoint the endpoint to set
     */
    public void setEndpoint(URI endpoint) {
        this.endpoint = endpoint;
    }

    /**
     * @return the resourcePath
     */
    public String getResourcePath() {
        return resourcePath;
    }

    /**
     * @param resourcePath the resourcePath to set
     */
    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    /**
     * @return the parameters
     */
    public Map<String, String> getParameters() {
        return parameters;
    }

    /**
     * @param parameters the parameters to set
     */
    public void setParameters(Map<String, String> parameters) {
        assertParameterNotNull(parameters, "parameters");

        this.parameters = parameters;
    }

    public void addParameter(String key, String value) {
        assertStringNotNullOrEmpty(key, "key");

        this.parameters.put(key, value);
    }

    public void removeParameter(String key) {
        assertStringNotNullOrEmpty(key, "key");

        this.parameters.remove(key);
    }

    /**
     * Whether or not the request can be repeatedly sent.
     *
     * @return
     */
    public boolean isRepeatable() {
        return this.getContent() == null || this.getContent().markSupported();
    }
}