package com.edison.testJunit.oth.ii_3_Socket;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

/**3.1 连接到服务器*/
public class A_ConnectToServer {

	public static void main(String[] args) {
		try {//下面这些通过主机名称获取连接的方式，实际上都要去dns查询实际ip
//			Socket socket=new Socket("www.baidu.com", 80);//这个就需要发送GET等http信息
			Socket socket=new Socket("time-A.timefreq.bldrdoc.gov", 13); //这是个时间服务器，只需要访问该端口，就会返回时间串
			socket.setSoTimeout(3000);//设置后面读阻塞等待超时时间,如连接baidu，在readLine时，等待3秒就超时返回异常
		
			/*Socket socket=new Socket(); 这种也可以
			socket.connect(new InetSocketAddress("www.baidu.com", 80), 3000);*/
			
			/***/
			
			System.out.println(socket.getInetAddress().getHostName()+"-"+socket.getInetAddress().getHostAddress()); //获取服务端地址
			System.out.println(socket.getInetAddress().getLocalHost());//获取主机地址
			System.out.println(socket.getLocalAddress()+" "+socket.getLocalPort());//获取连接的本地ip和端口

			InputStream in=socket.getInputStream();
			BufferedReader br=new BufferedReader(new InputStreamReader(in));
			String buff;
			while((buff=br.readLine())!=null){
				System.out.println(buff);
			}
			socket.close();
			in.close();
			br.close();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//InetAddress 因特网地址 不含端口  InetSocketAddress 含端口
		try{
			InetAddress inetAddress=InetAddress.getByName("www.baidu.com");//这个不是通过new创建的 另外只是返回随机的一个ip
			InetSocketAddress inetSocketAddress=new InetSocketAddress("www.baidu.com", 80);
			InetSocketAddress inetSocketAddress2=new InetSocketAddress(inetAddress, 80);
			Socket socket3=new Socket(inetAddress,80);
			socket3.close();
			byte[] bts=inetAddress.getAddress();//字节数组，共4个字节
			for(byte bt:bts){
				System.out.print(Byte.toUnsignedInt(bt)+"."); //将byte转为无符号的int，否则打印出是负数
			}
			System.out.println();
			
			//针对实现负载均衡的服务器一个主机名对应多个ip
			InetAddress[] inetAddresses=InetAddress.getAllByName("www.google.cn");
			for(InetAddress addr:inetAddresses){
				bts=addr.getAddress();//字节数组，共4个字节
				for(byte bt:bts){
					System.out.print(Byte.toUnsignedInt(bt)+"."); //将byte转为无符号的int，否则打印出是负数
				}
				System.out.println();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
