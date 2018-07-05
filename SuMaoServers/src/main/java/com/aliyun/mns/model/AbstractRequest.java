package com.aliyun.mns.model;


public class AbstractRequest {
    protected String requestPath;
    //protected ServiceCredentials credentials;
    protected String host;

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }


//	public void setCredentials(ServiceCredentials credentials){
//		this.credentials = credentials;
//	}
//	
//	public ServiceCredentials getCredentials() {
//		return credentials;
//	}
}
