package com.edison.testJunit.oth.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client {
	private static final Logger logger = LoggerFactory.getLogger( Client.class );

	private static final int DEFAULT_SERVER_PORT=7777; //服务端默认端口号
	private static final String DEFAULT_SERVER_IP="127.0.0.1"; //默认端口号
	
	public static void send(String  expression){
		send(DEFAULT_SERVER_PORT,expression);
	}

	private static void send(int port, String expression) {
		logger.info("客户端:expr={}",expression);
		Socket socket=null;
		BufferedReader in=null;
		PrintWriter out=null;
		try{
			socket=new Socket(DEFAULT_SERVER_IP, port);
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(),true);
			//其中的write()方法，本身不会写入换行符，如果用write()写入了信息，在另一端如果用readLine()方法。
			//由于读不到换行符，意味中读不到结束标记，然后由于IO流是阻塞式的，所以程序就是一直卡在那里不动了。
			//原因即为缺少回车标识。如果在写入的时候加上“\r\n”,就可以解决这个问题了。而println()就自动加上了换行符了
			out.println(expression);
			logger.info("服务端返回结果为：{}",in.readLine());
			String result;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				in=null;
			}
			if(out!=null){
				out.close();
				out=null;
			}
			if(socket!=null){
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				socket=null;
			}
		}
	}

}
