package com.iscas.component.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iscas.component.entity.ReturnMessage;

/**
 * @author adams 通信客户端
 */
public class SocketClientUtil {
	private static Logger log = LoggerFactory.getLogger(SocketClientUtil.class);
	private static String retValue = null;

	/**
	 * @param reqParame
	 *            请求参数
	 * @return ReturnMessage 请求结果
	 */
	public static ReturnMessage sockextClientMethod(String reqParame) {
		log.info("socketClientMethod start .................");
		Socket socket = null;
		DataInputStream input = null;
		DataOutputStream out = null;
		ReturnMessage returnMessage = null;
		Gson gson = new Gson();
		try {
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(ComponentConstant.SERVICE_IP_ADDR, ComponentConstant.SERVICE_PORT);
			socket.setSoTimeout(100000);
			// 读取服务器端数据
			input = new DataInputStream(socket.getInputStream());
			// 向服务器端发送数据
			out = new DataOutputStream(socket.getOutputStream());
			out.writeUTF(reqParame);
			retValue = input.readUTF();
			returnMessage = gson.fromJson(retValue, new TypeToken<ReturnMessage>() {
			}.getType());
			log.info("服务器端返回过来信息是: {} ", retValue);
		} catch (Exception e) {
			log.info("客户端异常:{}", e.getMessage());
			return returnMessage;
		} finally {
			try {
				if (socket != null) {
					socket.close();
					socket = null;
				}	
				if (out != null) {
					out.close();
					out = null;
				}
				if (input != null) {
					input.close();
					input = null;
				}
			} catch (IOException e) {
				socket = null;
				log.info("客户端 finally 异常:{}", e.getMessage());
			}
		}
		return returnMessage;
	}
}
