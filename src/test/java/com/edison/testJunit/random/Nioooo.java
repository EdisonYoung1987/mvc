package com.edison.testJunit.random;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class Nioooo {
    private static Charset utf8 = Charset.forName("UTF-8");
    public static void main(String[] args) {
//        ByteBuffer byteBuffer= ByteBuffer.allocate(6);
        try {
          /*  FileChannel fileChannel = FileChannel.open(Paths.get("d:/a.java"), StandardOpenOption.READ);
            while (fileChannel.read(byteBuffer) !=-1){
                byteBuffer.flip();//for read
               *//* 无乱码展示
                CharBuffer charBuffer=utf8.decode(byteBuffer);
                System.out.println(charBuffer.toString());*//*

                //一个byte一个byte的打印  如 0 --48  1--49  a--97
//                for(int i=byteBuffer.position();i<byteBuffer.limit();i++){
                while(byteBuffer.hasRemaining()){
                    System.out.println((char)byteBuffer.get());
                }
                byteBuffer.clear();//for write
            }

            //子缓冲区-视图
            byteBuffer.clear();
            for(int i=0;i<byteBuffer.capacity();i++){
                byteBuffer.put((byte)('a'+i));
            }

            byteBuffer.position(2);
            byteBuffer.limit(4);
            ByteBuffer subByteBuffer=byteBuffer.slice();
            System.out.println(subByteBuffer.position()+" "+subByteBuffer.limit()+" "+subByteBuffer.capacity());

            System.out.println(subByteBuffer.capacity());
            while(subByteBuffer.hasRemaining()){
                System.out.println((char)subByteBuffer.get());//
            }

            //只读
            ByteBuffer readOnly=byteBuffer.asReadOnlyBuffer();
            testWrite(readOnly);

            //直接缓存
            ByteBuffer  directByteBuffer=ByteBuffer.allocateDirect(1024);*/

            /*//映射内存
            RandomAccessFile randomAccessFile=new RandomAccessFile("D:/a.java","rw");
            FileChannel fileChannel1=randomAccessFile.getChannel();
            MappedByteBuffer mappedByteBuffer=fileChannel1.map(FileChannel.MapMode.READ_WRITE,2,7);
//            while(mappedByteBuffer.hasRemaining()){
            for(int i=mappedByteBuffer.position();i<mappedByteBuffer.limit();i++){
                System.out.println(mappedByteBuffer.position()+":"+(char)mappedByteBuffer.get());
            }
            //修改buffer中的内容，文件的内容也会改变
            mappedByteBuffer.clear();
            for(int i=mappedByteBuffer.position();i<mappedByteBuffer.limit();i++){
                mappedByteBuffer.put((byte)('a'+i));
            }
            randomAccessFile.close();*/

            //NIO --SELECTOR
            Selector selector= Selector.open();
            ServerSocketChannel serverSocketChannel=ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",9999));

            while(true){
                System.out.println("下一次轮询");
                int n=selector.select();//blocking util at least 1 connection recevied
                System.out.println(n);
                Set<SelectionKey> keys= selector.selectedKeys();
                Iterator<SelectionKey> it=keys.iterator();

                while(it.hasNext()){
                    System.out.println("遍历所有key");
                    SelectionKey selectionKey=it.next();
                    it.remove();

                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel ssc= (ServerSocketChannel) selectionKey.channel();
                        SocketChannel socketChannel=ssc.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                        System.out.println("有连接接入："+socketChannel.getRemoteAddress());
                    }else if(selectionKey.isReadable()){//读多少算多少
                        System.out.println("readable");
                        SocketChannel sc= (SocketChannel) selectionKey.channel();
                        sc.configureBlocking(false);
                        ByteBuffer byteBuffer= (ByteBuffer) selectionKey.attachment();
                        if(sc.read(byteBuffer)!=-1){ //-1 end-of-stream 讀取到內容則註冊寫事件
                            System.out.println("进行写："+byteBuffer.remaining());
                            byteBuffer.flip();
//                            System.out.println("读:"+utf8.decode(byteBuffer));
                            sc.register(selector,SelectionKey.OP_WRITE,byteBuffer);//每次都要注册
                        }else{//已关闭连接
                            selectionKey.cancel(); //没有这一句的话，可能出现死循环 select()一直返回1
                        }
                    }else if(selectionKey.isWritable()){//一次没写完就多写几次
                        SocketChannel sc= (SocketChannel) selectionKey.channel();
                        sc.configureBlocking(false);
                        ByteBuffer byteBuffer= (ByteBuffer) selectionKey.attachment();
                        sc.write(byteBuffer);

                        if(!byteBuffer.hasRemaining()){//写完了才进行下一次的读取
                            byteBuffer.clear();
                            System.out.println("写完");
                            sc.register(selector,SelectionKey.OP_READ,byteBuffer);//每次都要注册
                        }
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void testWrite(ByteBuffer readOnly){
        readOnly.put(1,(byte)1);
    }
}
