package com.edison.testJunit.oth.netty.bio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edison.testJunit.oth.zookeeper.base.AllZooKeeperWatcher;

public class Server {
	private static final Logger logger = LoggerFactory.getLogger( Server.class );

	private static final int DEFAULT_PORT=7777; //默认端口号
	private static ServerSocket serverSocket; //单利serversocket
	
	public static void start(){
		start(DEFAULT_PORT);
	}
	public static void start(int port){
		if(serverSocket!=null){
			return;
		}
		
		try {
			serverSocket=new ServerSocket(port);
			logger.info("已监听端口：{}",port);
			
			while (true) {
				Socket socket=serverSocket.accept(); //接收连接
				new Thread(new ServerHandler(socket)).start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(serverSocket!=null){
				try {
					serverSocket.close();
					serverSocket=null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
