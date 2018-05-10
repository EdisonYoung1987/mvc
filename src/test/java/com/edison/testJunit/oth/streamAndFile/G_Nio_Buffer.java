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
	private static final String FILE="D:\\工作\\其他\\传递外部文档.txt";
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
			ByteBuffer bf=ByteBuffer.allocate(200);
			int j=0;
			while(channel.read(bf)!=-1){
				bf.flip(); //要读的时候需要切换到读模式，且将指针指向0,否则下一步write什么都写不了(实际上随机写了一段进去。。)
				channel2.write(bf);
				bf.clear();//读完了要进行clear，以便下一次写，否则read(bf)不会改变buf的内容，channel一个byte都没读，死循环
			}
			channel.close();
			channel2.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
