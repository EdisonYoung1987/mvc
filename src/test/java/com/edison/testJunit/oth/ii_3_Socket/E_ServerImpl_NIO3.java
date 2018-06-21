package com.edison.testJunit.oth.ii_3_Socket;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * 仿照前面的NIO2的多端口监听，不过增加了对收到内容的简单计算，如[24  35 2] 返回 24+35+2=61*/
public class E_ServerImpl_NIO3 {
	private int[] ports;
	private Map<String,StringBuilder> map=new HashMap<String,StringBuilder>(16); //用客户端地址端口作为key，传送的数值表达式作为value
	
	public E_ServerImpl_NIO3(String[] ports){//4906 4907 4908 4909
		this.ports=new int[ports.length];
		for(int i=0;i<ports.length;i++){
			this.ports[i]=Integer.parseInt(ports[i]);
		}
	}
	
	public void go() throws Exception{
		//1 首先获取一个Selector
		Selector selector=Selector.open();
				
		//2 然后获取每个端口获取一个channel:ServerSocketChannel 
		for(int port:ports){
			ServerSocketChannel sschannel=ServerSocketChannel.open();//每次都不一样
			System.out.println("sschannel="+sschannel.hashCode());
			sschannel.configureBlocking(false);//设置为非阻塞
			
			//绑定端口
			ServerSocket sSocket=sschannel.socket();
			sSocket.bind(new InetSocketAddress(InetAddress.getByName("172.20.10.2"), port)); //两者效果一样。。
//			sschannel.bind(new InetSocketAddress(InetAddress.getByName("172.20.10.2"), port));
			
			//3 再将ServerSocketChannel注册到selector上面
			SelectionKey skey= sschannel.register(selector, SelectionKey.OP_ACCEPT);//肯定只对accept感兴趣
			////返回对象包含selector,channel,感兴趣集合以及ready集合
			System.out.println("刚注册，数量="+selector.keys().size()); //keys()方法返回所有注册的channel
		}	
		
		//4 开始监听
		while(true){
			selector.select(); //这个时候端口才会监听起来,该方法阻塞到至少有一个通道在你注册的事件上就绪了。
			
			//5 获取就绪的key
			Set<SelectionKey> set=selector.selectedKeys(); //前面注册时就相当于往selector里面增加一个selectionKey
//			for(SelectionKey selectionKey:set){//这种forreach方法如果遇到remove会抛异常ConcurrentModificationException
			Iterator<SelectionKey> it=set.iterator();
			while(it.hasNext()){
				SelectionKey selectionKey=it.next();
				
				//根据key不同的就绪状态做不同处理
				if(selectionKey.isAcceptable()){//服务端是accept，客户端是connect
					System.out.print(((ServerSocketChannel)selectionKey.channel()).getLocalAddress()+" ACCEPT");
					ServerSocketChannel ssChannel=(ServerSocketChannel)selectionKey.channel();//获取其channel，并转换为ServerSocketChannel
					SocketChannel sChannel= ssChannel.accept(); //获取socketChannel
					System.out.println("  FROM "+sChannel.getRemoteAddress());
					sChannel.configureBlocking(false);//这个也要设置非阻塞
					sChannel.register(selector, SelectionKey.OP_READ|SelectionKey.OP_WRITE);//再次注册
					System.out.println("刚注册，数量="+selector.keys().size());

					it.remove();//如果我们没有删除处理过的键，那么它仍然会在主集合中以一个激活的键出现，这会导致我们尝试再次处理它。 
				}else if(selectionKey.isReadable()){
					System.out.println(((SocketChannel)selectionKey.channel()).getRemoteAddress()+"is ready for read");
					SocketChannel sChannel=(SocketChannel)selectionKey.channel();
					String key=sChannel.getRemoteAddress().toString();
					StringBuilder sb;
					if(!map.containsKey(key)){//第一次接入
						sb=new StringBuilder();
						map.put(key, sb);
					}else{
						sb=map.get(key);
					}
					ByteBuffer bflen=ByteBuffer.allocate(4啊); //获取4位长度
					ByteBuffer bf=ByteBuffer.allocate(1024); //获取内容
					int totle=0;
					while(true){
						int len=sChannel.read(bf);
						totle+=len;
						if(len<=0){
							System.out.println("len=0,break");
							break;
						}
						/*bf.flip();
						int remain=bf.remaining();
						byte[] bytes=new byte[remain];
						bf.get(bytes, 0, remain);
						String content=new String(bytes);
						entity.addCalString(content);
						System.out.println("sss:"+entity.getCalString().toString());
						if(entity.getCalString().toString().length()>5){
							System.out.println("mmm:"+entity.getCalString().toString());

							ByteBuffer bfw=ByteBuffer.allocate(10);
							bfw.put(("    "+entity.getCalString().toString()).getBytes());
							sChannel.write(bfw);
							entity.clear();
						}
						bf.clear();*/
						bf.flip();  
						  
                        sChannel.write(bf);  
                        bf.clear();
					}
					System.out.println("本次读取"+totle+"byte");
					it.remove();
				}
			}
		}
		
		//然后将ServerSocketChannel注册到selector上面
//		sschannel.register(selector,SelectionKey.OP_ACCEPT);//
		
	}
	
	public static void main(String[] args) {
		if(args.length==0){
			System.err.println("usage：xxx  端口1  端口2 端口3 ...");
			return;
		}
		
		E_ServerImpl_NIO3 e_ServerImpl_NIO3=new E_ServerImpl_NIO3(args);
		try{
			e_ServerImpl_NIO3.go();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
