package com.edison.testJunit.oth.netty.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerHandler implements Runnable {
	private static final Logger logger = LoggerFactory.getLogger( ServerHandler.class );
	private Socket socket;
	
	public ServerHandler(Socket socket){
		this.socket=socket;
	}
	public void run() {
		BufferedReader in=null;
		PrintWriter out=null;
		
		try {
			in=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out=new PrintWriter(socket.getOutputStream(),true);
			String expression;
			String result;
			while(true){
				if((expression=in.readLine())==null)
					break;
				logger.info("expression={}",expression);
				result=Caculate.cal(expression);
				logger.info("result={}",result);
				out.println(result);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getLocalizedMessage());
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
