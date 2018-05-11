package com.edison.testJunit.oth.streamAndFile;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


public class G_Nio_Buffer {
	private static final String FILE="D:\\工作\\其他\\buff.txt";
	private static final String FILE2="D:\\工作\\其他\\传递外部文档4.txt";

	public static void main(String[] args) {
		try{
			Path path=Paths.get(FILE);
			FileChannel channel=FileChannel.open(path,  StandardOpenOption.READ);//计算校验和，只能read
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
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
