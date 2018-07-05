package com.iscas.component.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.iscas.component.core.intfs.ComponentIntface;
import com.iscas.component.entity.RequestEntity;
import com.iscas.component.entity.ReturnMessage;

/**
 * @author adams socket接口类入口核心类
 */
@Repository("socketServerUtil")
public class SocketServerUtil {
	private static Logger log = LoggerFactory.getLogger(SocketServerUtil.class);
	private ClassPathXmlApplicationContext ctx = null;
	private Gson gson = null;

	/**
	 * 初始化方法入口
	 */
	public void init(ClassPathXmlApplicationContext ctx) {
		log.info("SocketServerUtil init start................\n");
		try {
			this.ctx = ctx;
			this.gson = new Gson();
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(ComponentConstant.LISTENER_PORT);
			// 一直获取数据
			while (true) {
				// 一旦有堵塞, 则表示服务器与客户端获得了连接
				Socket client = serverSocket.accept();
				log.info("请求客户端的IP地址为：{},客户端通信端口号为：{}\n", client.getInetAddress(), client.getPort());
				// 处理当前连接
				threadHandle(client);
			}
		} catch (Exception e) {
			log.info("SocketServerUtil init Exception................\n");
		}
	}

	/**
	 * @param client
	 *            线程池处理连接请求并把处理结果返回给客户端
	 */
	public void threadHandle(final Socket client) {
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		cachedThreadPool.execute(new Runnable() {
			Socket socket = client;
			DataInputStream input = null;
			DataOutputStream out = null;

			public void run() {
				try {
					// 读取客户端数据
					input = new DataInputStream(socket.getInputStream());
					String clientInputString = input.readUTF();// 这里要注意和客户端输出流的写方法对应,否则会抛
					// 处理客户端数据
					log.info("客户端请求过来的内容是：{}\n", clientInputString);
					// 获取请求数据
					String returnMessage = getReturnData(clientInputString);
					// 向客户端回复信息
					out = new DataOutputStream(socket.getOutputStream());
					log.info("发送给客户端的信息为：{}\n", returnMessage);
					out.writeUTF(returnMessage);
				} catch (Exception e) {
					log.info("服务器 run 异常：{}", e.getMessage());
				} finally {
					try {
						if (socket != null) {
							socket.close();
							socket = null;
						}
						if (input != null) {
							input.close();
							input = null;
						}
						if (out != null) {
							out.close();
							out = null;
						}
					} catch (Exception e) {
						socket = null;
						out = null;
						input = null;
						log.info("服务端 finally 异常：{}\n", e.getMessage());
					}
				}

			}
		});
	}

	/**
	 * 获取返回的数据
	 * 
	 * @return
	 */
	private String getReturnData(String clientInputString) {
		// 获取returnMessage
		ReturnMessage returnMessage = (ReturnMessage) ctx.getBean("returnMessage");
		try {
			RequestEntity requestEntity = gson.fromJson(clientInputString, RequestEntity.class);
			// 类名大小写处理
			char firstChar = requestEntity.getBusiclass().charAt(0);
			String className = null;
			if (Character.isUpperCase(firstChar)) {
				firstChar = Character.toLowerCase(firstChar);
				String str = String.valueOf(firstChar);
				className = str + requestEntity.getBusiclass().subSequence(1, requestEntity.getBusiclass().length());
			}
			// 获取需要调的接口对象
			ComponentIntface componentIntface = (ComponentIntface) ctx.getBean(className);
			if (null == componentIntface || null == requestEntity || null == requestEntity.getBusiclass()
					|| "".equals(requestEntity.getBusiclass())) {
				returnMessage.setMessage(ComponentConstant.RETURN_FAIL_MSG);
				returnMessage.setCode(ComponentConstant.RETURN_FAIL_CLASS_CODE);
			} else {
				if (null == requestEntity.getBusiMethod() || "".equals(requestEntity.getBusiMethod())) {
					returnMessage.setMessage(ComponentConstant.RETURN_FAIL_MSG);
					returnMessage.setCode(ComponentConstant.RETURN_FAIL_METHOD_CODE);
				} else {
					// 反射获取方法并调用返回结果
					Class<?> clazz = Class.forName(ComponentConstant.INTFACE_PATH + requestEntity.getBusiclass());
					Method[] methods = clazz.getMethods();
					for (Method method : methods) {
						if (method.getName().equals(requestEntity.getBusiMethod())) {
							// 启动客服端传来的方法，并返回值
							Object result = method.invoke(componentIntface,
									new Object[] { requestEntity.getRequestParam() });
							returnMessage.setCode(ComponentConstant.RETURN_SUCCESS_CODE);
							returnMessage.setMessage(ComponentConstant.RETURN_SUCCESS_MSG);
							returnMessage.setReturnMsg(result);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			returnMessage.setMessage(ComponentConstant.RETURN_FAIL_MSG);
			returnMessage.setCode(ComponentConstant.RETURN_FAIL_CLASS_CODE);
		}
		return gson.toJson(returnMessage);
	}
}
