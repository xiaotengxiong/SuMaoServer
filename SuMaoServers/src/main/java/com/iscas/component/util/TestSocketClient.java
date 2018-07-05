package com.iscas.component.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iscas.component.entity.RequestEntity;
import com.iscas.component.entity.ReturnMessage;
import com.iscas.component.services.test.bean.Test;

public class TestSocketClient {
	// public static final String IP_ADDR = "123.57.143.35"; // 服务器地址
	public static final String IP_ADDR = "127.0.0.1"; // 服务器地址
	public static final int PORT = 65535; // 服务器端口号

	public static void main(String[] args) {
		System.out.println("客户端启动...");
		Socket socket = null;
		DataInputStream input = null;
		DataOutputStream out = null;
		// while (true) {
		try {
			Gson gson = new Gson();
			RequestEntity requestEntity = new RequestEntity();
			requestEntity.setBusiclass("TestIntface");
			requestEntity.setBusiMethod("selectDateAsList");
			String param = gson.toJson(requestEntity);
			// 创建一个流套接字并将其连接到指定主机上的指定端口号
			socket = new Socket(IP_ADDR, PORT);
			socket.setSoTimeout(100000);
			// 读取服务器端数据
			input = new DataInputStream(socket.getInputStream());
			// 向服务器端发送数据
			out = new DataOutputStream(socket.getOutputStream());
			// System.out.print("请输入: \t");
			// InputStreamReader inputStreamReader = new
			// InputStreamReader(System.in);
			// String str = new
			// BufferedReader(inputStreamReader).readLine();
			out.writeUTF(param);
			String ret = input.readUTF();
			// Gson解析字符串
			ReturnMessage ps = gson.fromJson(ret, new TypeToken<ReturnMessage>() {
			}.getType());
			List<Test> list = (List<Test>) ps.getReturnMsg();
			System.out.println("服务器端返回过来的是: " + ret);
			// 如接收到 "OK" 则断开连接
		} catch (Exception e) {
			System.out.println("客户端异常:" + e.getMessage());
		} finally {
			if (socket != null) {
				try {
					out.close();
					input.close();
					socket.close();
				} catch (IOException e) {
					socket = null;
					System.out.println("客户端 finally 异常:" + e.getMessage());
				}
			}
		}
		// }
	}
}
