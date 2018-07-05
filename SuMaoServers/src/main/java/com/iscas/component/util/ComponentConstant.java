package com.iscas.component.util;

/**
 * @author adams 常用字符串，常用静态变量类
 */
public class ComponentConstant {
	/**
	 * 返回给客户端的成功信息
	 */
	public static final String RETURN_SUCCESS_MSG = "成功";
	public static final int PAGE_SIZE = 20;
	/**
	 * 返回给客户端的失败信息
	 */
	public static final String RETURN_FAIL_MSG = "失败";

	/**
	 * 项目监听的端口号
	 */
	public static final int LISTENER_PORT = 65535;

	/**
	 * 返回成功编码
	 */
	public static final int RETURN_SUCCESS_CODE = 200;
	/**
	 * 返回请求类为空
	 */
	public static final int RETURN_FAIL_CLASS_CODE = 201;
	/**
	 * 返回请求方法为空
	 */
	public static final int RETURN_FAIL_METHOD_CODE = 203;
	/**
	 * 找不到对应的接口
	 */
	public static final int RETURN_FAIL_CODE = 404;

	/**
	 * 请求接口失败
	 */
	public static final int RETURN_INTFACE_FAIL_CODE = 500;

	/**
	 * 服务端处理异常
	 */
	public static final int EXCEPTION_FAIL_CODE = 501;

	/**
	 * 接口类路径（所有接口必须全部写在这个包下面）
	 */
	public static final String INTFACE_PATH = "com.iscas.component.intfaces.";

	/**
	 * 服务端处理异常
	 */
	public static final String SERVICE_IP_ADDR = "127.0.0.1";

	/**
	 * 远程接口服务的监听端口号
	 */
	public static final int SERVICE_PORT = 65535;
}
