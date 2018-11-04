package com.edison.testJunit.oth.ii_3_Socket;
/**前面每个线程处理一个连接，这种方法并不能满足高性能服务器的要求，采用nio方式<p>
 * 主要用到：SelectableChannel and Selector classes.<br>
 * 构建非阻塞nonblocking I/O: 一般的阻塞socket在accept/read/write时都会阻塞*/

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.net.*;
import java.util.*;
import java.nio.charset.*;


public class C_ServerImpl_NIO{
    public Selector sel = null;
    public ServerSocketChannel server = null;
    public SocketChannel socket = null;
    public int port = 4900;
    String result = null;


    public C_ServerImpl_NIO()
    {
		System.out.println("Inside default ctor");
    }
    
	public C_ServerImpl_NIO(int port)
    {
		System.out.println("Inside the other ctor");
		this.port = port;
    }

    public void initializeOperations() throws IOException,UnknownHostException
    {
		System.out.println("Inside initialization");
		server = ServerSocketChannel.open();//ServerSocketChannel server 获取通道  
		server.configureBlocking(false);    //设置非阻塞  server.accept()就不阻塞了
		InetAddress ia = InetAddress.getLocalHost();
		InetSocketAddress isa = new InetSocketAddress(ia,port);
		System.out.println("监听地址："+isa);
		server.socket().bind(isa);//绑定连接 
    }
    
	public void startServer() throws IOException
    {
        initializeOperations();//打开服务器 启动监听
		sel = Selector.open(); //Selector sel 获取选择器:Selector(选择器)是Java NIO中能够检测一到多个NIO通道，
								//并能够知晓通道是否为诸如读写事件做好准备的组件。

		//某个client成功连接到另一个服务器称为”连接就绪“。一个server socket channel准备号接收新进入的连接称为”接收就绪“。
		//一个有数据可读的通道可以说是”读就绪“。等代写数据的通道可以说是”写就绪“。
		server.register(sel, SelectionKey.OP_ACCEPT );	//将通道注册到选择器上, 并且指定“监听接收事件”  
				//假设selector有三个集合：读集合、写集合、连接集合，每个socket在注册时，被加到对应的集合中，然后select采用
				//轮询的方式查看是否有socket满足注册时指定的读写条件，只要有一个就返回交给应用处理。

		while (sel.select() > 0 )
		{	
	    
			Set<SelectionKey> readyKeys = sel.selectedKeys();
			Iterator<SelectionKey> it = readyKeys.iterator();

			while (it.hasNext()) {
				//7. 获取当前选择器中所有注册的“选择键(已就绪的监听事件)” 
				SelectionKey key = (SelectionKey)it.next();
				it.remove();
                
				//9. 判断具体是什么事件准备就绪  
				if (key.isAcceptable()) {//10. 若“接收就绪”，获取客户端连接  
					System.out.println("Key is Acceptable");
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					socket = (SocketChannel) ssc.accept();
					socket.configureBlocking(false);//11. 切换非阻塞模式  send() recv()读写就不再阻塞
					SelectionKey another = socket.register(sel,SelectionKey.OP_READ|SelectionKey.OP_WRITE);
				}
				if (key.isReadable()) {
					System.out.println("Key is readable");
					String ret = readMessage(key);
					if (ret.length() > 0) {
						writeMessage(socket,ret);
					}
				}
				if (key.isWritable()) {
					System.out.println("THe key is writable");
					String ret = readMessage(key);
					socket = (SocketChannel)key.channel();
					if (result.length() > 0 ) {
						writeMessage(socket,ret);
					}
				}
			}
		}
    }

    public void writeMessage(SocketChannel socket,String ret)
    {
		System.out.println("Inside the loop");

		if (ret.equals("quit") || ret.equals("shutdown")) {
			return;
		}
		File file = new File(ret);
		try
		{
		
			RandomAccessFile rdm = new RandomAccessFile(file,"r");
			FileChannel fc = rdm.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			fc.read(buffer);
			buffer.flip();
    
			Charset set = Charset.forName("us-ascii");
			CharsetDecoder dec = set.newDecoder();
			CharBuffer charBuf = dec.decode(buffer);
			System.out.println(charBuf.toString());
			buffer = ByteBuffer.wrap((charBuf.toString()).getBytes());
			int nBytes = socket.write(buffer);
			System.out.println("nBytes = "+nBytes);
				result = null;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

    }
  
    public String readMessage(SelectionKey key)
    {
		int nBytes = 0;
		socket = (SocketChannel)key.channel();
        ByteBuffer buf = ByteBuffer.allocate(1024);
		try
		{
            nBytes = socket.read(buf);
			buf.flip();
			Charset charset = Charset.forName("us-ascii");
			CharsetDecoder decoder = charset.newDecoder();
			CharBuffer charBuffer = decoder.decode(buf);
			result = charBuffer.toString();
	    
        }
		catch(IOException e)
		{
			e.printStackTrace();
		}
		return result;
    }

    public static void main(String args[])
    {
    	C_ServerImpl_NIO nb = new C_ServerImpl_NIO();
		try
		{
			nb.startServer();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
	}
}