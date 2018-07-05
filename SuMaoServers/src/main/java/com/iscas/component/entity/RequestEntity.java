package com.iscas.component.entity;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

/**
 * @author adams 请求数据封装
 */
@Repository("requestEntity")
public class RequestEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 请求接口名(类名)
	 */
	private String Busiclass;

	/**
	 * 请求方法
	 */
	private String BusiMethod;

	/**
	 * 请求参数对象
	 */
	private Object requestParam;

	public String getBusiclass() {
		return Busiclass;
	}

	public void setBusiclass(String busiclass) {
		Busiclass = busiclass;
	}

	public String getBusiMethod() {
		return BusiMethod;
	}

	public void setBusiMethod(String busiMethod) {
		BusiMethod = busiMethod;
	}

	public Object getRequestParam() {
		return requestParam;
	}

	public void setRequestParam(Object requestParam) {
		this.requestParam = requestParam;
	}
}
