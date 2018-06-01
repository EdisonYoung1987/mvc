package com.edison.testJunit.oth.ii_3_Socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


/**实现一个服务器*/
public class B_ServerImpl {

	public static void main(String[] args) {
		ServerSocket sSocket=null;
		try {
			sSocket=new ServerSocket(8189, 10, InetAddress.getByName("172.20.10.2"));
			
			//多线程获取连接并进行处理
			while(true){
				Socket socket=sSocket.accept();//获取客户端连接,此时阻塞等待
				MyService myService=new MyService(socket);
				Thread thread=new Thread(myService);
				thread.start();
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(sSocket!=null){
				try{
					sSocket.close();
				}catch(Exception e){
				}
			}
		}
	}

}

/**多线程实现对服务端收到的连接的处理*/
class MyService implements Runnable{
	private Socket socket;
	
	public MyService(Socket socket){
		this.socket=socket;
	}
	
	public void run() {
		try{
//			socket.setSoTimeout(); //设置整个会话的超时时长
			
			BufferedWriter bw=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write("HELLO: "+socket.getInetAddress() + "\n This is "+Thread.currentThread().getId()+"\n"); 
			bw.flush();//必须要有个操作，不然一直到连接关闭或者写buffer满了才会写到socket
			
			BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String recv=br.readLine();
			while(!"bye".equals((recv=br.readLine()))){
				bw.write("recv:"+recv.trim()+"\n");
				bw.flush();
			}
			bw.write("Bye!!");
			
			bw.close();
			br.close();
			socket.close();		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
