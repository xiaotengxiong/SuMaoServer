package com.iscas.component.entity;

import java.io.Serializable;

import org.springframework.stereotype.Repository;

/**
 * @author adams 返回信息封装
 */
@Repository("returnMessage")
public class ReturnMessage implements Serializable {

	/**
	 * 返回码
	 */
	private int code;

	/**
	 * 返回描述
	 */
	private String message;

	/**
	 * 返回数据值
	 */
	private Object returnMsg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(Object returnMsg) {
		this.returnMsg = returnMsg;
	}
}
