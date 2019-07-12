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
            serverSocketChannel.bind(new InetSocketAddress("127.0.0.1",8888));

            while(true){
                selector.select();//blocking util at least 1 connection recevied
                Set<SelectionKey> keys= selector.selectedKeys();
                Iterator<SelectionKey> it=keys.iterator();

                while(it.hasNext()){
                    SelectionKey selectionKey=it.next();
                    it.remove();

                    if(selectionKey.isAcceptable()){
                        ServerSocketChannel ssc= (ServerSocketChannel) selectionKey.channel();
                        ssc.configureBlocking(false);
                        ssc.register(selector,SelectionKey.OP_READ,ByteBuffer.allocate(1024));
                    }else if(selectionKey.isReadable()){ss
                        SocketChannel sc= (SocketChannel) selectionKey.channel();
                        sc.configureBlocking(false);
                        String addr=sc.getRemoteAddress().toString();
                        ByteBuffer byteBuffer= (ByteBuffer) selectionKey.attachment();
                        if(sc.read(byteBuffer)>0){ //-1 end-of-stream 讀取到內容則註冊寫事件
                            sc.register(selector,SelectionKey.OP_WRITE);
                        }
                    }else if(selectionKey.isWritable()){
                        SocketChannel sc= (SocketChannel) selectionKey.channel();
                        sc.configureBlocking(false);
                        ByteBuffer byteBuffer= (ByteBuffer) selectionKey.attachment();
                        byteBuffer.flip();
                        sc.write(byteBuffer);

                        if(!byteBuffer.hasRemaining()){//已經寫完
                            sc.register(selector,SelectionKey.OP_READ);
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
