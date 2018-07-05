package com.iscas.component.util;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author adams 加载spring上下文环境
 */
public class SpringUtil {

	private static ClassPathXmlApplicationContext ctx = null;

	/**
	 * 加载Spring配置文件
	 */
	static {
		ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	}

	/**
	 * 返回Spring上下文环境
	 * 
	 * @return ClassPathXmlApplicationContext
	 */
	public static ClassPathXmlApplicationContext getSpringContext() {
		return ctx;
	}
}
