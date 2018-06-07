package com.edison.testJunit.oth.ii_3_Socket;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
/**
 * 一个多端口监听的收到即返回的server*/
public class D_ServerImpl_NIO2 { // MultiPortEcho
    private int ports[];  
    private ByteBuffer echoBuffer = ByteBuffer.allocate(1024);  
  
    public D_ServerImpl_NIO2(int ports[]) throws IOException {  
        this.ports = ports;  
  
        go();  
    }  
  
    private void go() throws IOException {  
        // 创建一个选择器，需要探知的socketchannel都注册给它  
        Selector selector = Selector.open();  
  
        // Open a listener on each port, and register each one  
        // with the selector  
        ////建立Channel 并绑定到9000端口  
        for (int i = 0; i < ports.length; ++i) {  
            ServerSocketChannel ssc = ServerSocketChannel.open();  
            //使设定non-blocking的方式。  
            ssc.configureBlocking(false);  
            ServerSocket ss = ssc.socket();  
            //InetSocketAddress address = new InetSocketAddress(ports[i]);  
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost(),9000);  
            ss.bind(address);  
  
            //向Selector注册Channel及我们有兴趣的事件  
            SelectionKey key = ssc.register(selector, SelectionKey.OP_ACCEPT);  
  
            System.out.println("Going to listen on " + ports[i]);  
        }  
  
        while (true) {//不断的轮询  
            //Selector通过select方法通知我们我们感兴趣的事件发生了  
            int num = selector.select();  
  
            Set selectedKeys = selector.selectedKeys();  
            Iterator it = selectedKeys.iterator();  
  
            while (it.hasNext()) {//如果有我们注册的事情发生了，它的传回值就会大于0  
                SelectionKey key = (SelectionKey) it.next();  
                //对 SelectionKey 调用 readyOps() 方法，并检查发生了什么类型的事件  
                if ((key.readyOps() & SelectionKey.OP_ACCEPT) == SelectionKey.OP_ACCEPT) {  
                    // Accept the new connection  
                    //我们从这些key中的channel()方法中取得我们刚刚注册的channel  
                    ServerSocketChannel ssc = (ServerSocketChannel) key.channel();  
                    SocketChannel sc = ssc.accept();  
                      
  
                    // Add the new connection to the selector  
                    //将获得新连接的 SocketChannel 配置为非阻塞的。而且由于接受这个连接的目的是为了读取来自套接字的数据，所以我们还必须将 SocketChannel 注册到 Selector  
                    sc.configureBlocking(false);  
                    SelectionKey newKey = sc.register(selector,SelectionKey.OP_READ);//注意我们使用 register() 的 OP_READ 参数，将 SocketChannel 注册用于 读取 而不是 接受 新连接  
                    //在处理 SelectionKey 之后，我们几乎可以返回主循环了。但是我们必须首先将处理过的 SelectionKey 从选定的键集合中删除。  
                    //如果我们没有删除处理过的键，那么它仍然会在主集合中以一个激活的键出现，这会导致我们尝试再次处理它。  
                    //我们调用迭代器的 remove() 方法来删除处理过的 SelectionKey  
                    it.remove();  
  
                    System.out.println("Got connection from " + sc);  
                } else if ((key.readyOps() & SelectionKey.OP_READ) == SelectionKey.OP_READ) {  
                    // Read the data  
                    SocketChannel sc = (SocketChannel) key.channel();  
  
                    // Echo data  
                    int bytesEchoed = 0;  
                    while (true) {  
                        echoBuffer.clear();  
  
                        int r = sc.read(echoBuffer);  
  
                        if (r <= 0) {  
                            break;  
                        }  
  
                        echoBuffer.flip();  
  
                        sc.write(echoBuffer);  
                        bytesEchoed += r;  
                    }  
  
                    System.out.println("Echoed " + bytesEchoed + " from " + sc);  
  
                    it.remove();  
                }  
  
            }  
  
            // System.out.println( "going to clear" );  
            // selectedKeys.clear();  
            // System.out.println( "cleared" );  
        }  
    }  
  
    static public void main(String args[]) throws Exception {  
        if (args.length <= 0) {  
            System.err  
                    .println("Usage: java MultiPortEcho port [port port ...]");  
            System.exit(1);  
        }  
  
        int ports[] = new int[args.length];  
  
        for (int i = 0; i < args.length; ++i) {  
            ports[i] = Integer.parseInt(args[i]);  
        }  
  
        new D_ServerImpl_NIO2(ports);  
    }  
}  