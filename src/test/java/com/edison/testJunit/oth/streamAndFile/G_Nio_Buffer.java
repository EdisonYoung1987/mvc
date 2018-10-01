package com.edison.testJunit.oth.streamAndFile;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32;


public class G_Nio_Buffer {
	private static final String FILE="D:\\工作\\其他\\buff.txt";
	private static final String FILE3="D:\\工作\\其他\\rt.jar";

	private static final String FILE2="D:\\工作\\其他\\传递外部文档4.txt";
	private static final String BIGFILE="C:\\Users\\Edison\\git\\mvc\\src\\main\\resources\\xml_parser\\big.xml";//2.1g大文件


	public static void main(String[] args) {
		try{
			Path path=Paths.get(FILE);
			FileChannel channel=FileChannel.open(path,  StandardOpenOption.READ);
			Path path2=Paths.get(FILE2);
			FileChannel channel2=FileChannel.open(path2,  StandardOpenOption.CREATE,
														  StandardOpenOption.TRUNCATE_EXISTING,
					                                      StandardOpenOption.WRITE);//计算校验和，只能read
			//获取映射缓冲区buffer,缓冲区有三种模式 只读-读写(所有修改都会在某一时刻写入文件)-PRIVATE-读写(不写入文件)
			ByteBuffer bf=ByteBuffer.allocate(4);
			
			System.out.println("分配空间之后read()之前，buff的指针位置="+bf.position()+" 界限 "+bf.limit()+"\r\n");
			while(channel.read(bf)!=-1){
				System.out.println("read()之后，buff[4]的指针位置="+bf.position()+" 界限 "+bf.limit());
				//flip是翻转读写模式，且将指针指向0,否则下一步write什么都写不了
				bf.flip(); //如果最后一次读取内容长度len小于容量capacity,写入的内容是buf[len]->buf[capacity]的内容
				System.out.println("read()之后执行flip()，buff[4]的指针位置="+bf.position()+" 界限 "+bf.limit());
				channel2.write(bf);
				System.out.println("write()写入channel之后，buff[4]的指针位置="+bf.position()+" 界限 "+bf.limit());
				bf.clear();//读完了要重置指针，以便下一次写，否则read(bf)不会改变buf的内容，channel一个byte都没读，死循环
				System.out.println("write()写入channel后执行clear(),buff[4]的指针位置="+bf.position()+" 界限 "+bf.limit());
				System.out.println();
			}
			channel.close();
			channel2.close();
			
			//使用ByteBuffer计算校验和
			path=Paths.get( BIGFILE);//耗时：6390 毫秒 cpu无压力
 			long start=System.currentTimeMillis();
			FileChannel fc=FileChannel.open(path, StandardOpenOption.READ);
			ByteBuffer buffer=ByteBuffer.allocate(1024);
			CRC32 crc32=new CRC32();
			while(fc.read(buffer)!=-1){
				buffer.flip(); 
				crc32.update(buffer);
				buffer.clear();	
			}
			System.out.println("最终校验值="+crc32.getValue()+" 耗时："+(System.currentTimeMillis()-start)+" 毫秒");
			
			Thread.sleep(5000);
			
			crc32=new CRC32();//耗时9712毫秒，cpu满，如果调小size，则耗时大大增加
			start=System.currentTimeMillis();
			long position=0,size=Integer.MAX_VALUE;
			while(position<fc.size()){
				MappedByteBuffer mapBuff;
				System.out.println(position);
				if(position+size<=fc.size()){
					mapBuff=fc.map(MapMode.READ_ONLY, position, size);
				}else{
					mapBuff=fc.map(MapMode.READ_ONLY, position, fc.size()-position);
				}
				position+=size;
				while(mapBuff.hasRemaining()){
					crc32.update(mapBuff.get());
				}
				mapBuff.clear();
			}
			System.out.println("最终校验值="+crc32.getValue()+" 耗时："+(System.currentTimeMillis()-start)+" 毫秒");

		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
