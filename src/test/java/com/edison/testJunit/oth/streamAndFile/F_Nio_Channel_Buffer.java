package com.edison.testJunit.oth.streamAndFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.zip.CRC32;

/**主要是nio的channel和buffer的初步应用<p>
 * 这里实现了一个计算一个62M的文件rt.jar的校验码的测试类，对比了InputStream和BufferedInputStream以及RandomAccessFile的效率*/
public class F_Nio_Channel_Buffer {
	private static final String FILE="D:\\工作\\其他\\rt.jar";
	private static final String BIGFILE="C:\\Users\\Edison\\git\\mvc\\src\\main\\resources\\xml_parser\\big.xml";//2.1g大文件

	public static void main(String[] args) {
		try{
//			Path path=Paths.get(BIGFILE); //这个在调用channel.map时，因为无法将文件大小一次性传入，只能将文件分段计算
			Path path=Paths.get(FILE);
			/*打开选项APPEND           
			TRUNCATE_EXISTING 存在则清空 
			CREATE_NEW        不存在则创建,存在则失败
			CREATE            不存在则创建
			DELETE_ON_CLOSE   退出时关闭
			SPARSE            稀疏文件-开始时，一个稀疏文件不包含用户数据，也没有分配到用来存储用户数据的磁盘空间。
			当数据被写入稀疏文件时，NTFS逐渐地为其分配磁盘空间，普遍用来磁盘镜像，数据库快照，日志文件，还有其他科学运用上
			SYNC              每次的更新文件内容以及文件信息(最新更新时间)都会被即时的写到底层硬盘中
			DSYNC             同SYNC，不过不包括文件信息*/          
			FileChannel channel=FileChannel.open(path,  StandardOpenOption.READ);//计算校验和，只能read
			
			//获取映射缓冲区buffer,缓冲区有三种模式 只读-读写(所有修改都会在某一时刻写入文件)-PRIVATE-读写(不写入文件)
			MappedByteBuffer mapBuff=channel.map(MapMode.READ_ONLY, 0, channel.size());
			System.out.println("ByteBuffer的默认顺序"+mapBuff.order());
	
			//计算校验值CRC32
			long start=System.currentTimeMillis();
			CRC32 crc32=new CRC32();
			Byte bytes;
			while(mapBuff.hasRemaining()){
				bytes=mapBuff.get(); //getInt/putInt Double等等实现缓冲区基本类型的读写
				crc32.update(bytes);
			}
			long checkSum=crc32.getValue();
			long end=System.currentTimeMillis();
			System.out.println("使用NIO的channel+buffer计算校验值="+checkSum+" 耗时："+(end-start));
			//输出： 使用NIO的channel+buffer计算校验值=477532652 耗时：631
			channel.close();
			
			//计算校验值2
			start=System.currentTimeMillis();
			FileInputStream fin=new FileInputStream(new File(FILE));
			crc32=new CRC32();
			int data;
			/*while((data=fin.read())!=-1){
				crc32.update(data);
			} 太耗时，先注释掉*/
			checkSum=crc32.getValue();
			end=System.currentTimeMillis();
			System.out.println("使用IO的InputStream计算校验值="+checkSum+" 耗时："+(end-start));
			//输出： 使用IO的InputStream计算校验值=477532652 耗时：91915
			fin.close();
			
			//使用BufferedInputStream
			start=System.currentTimeMillis();
			InputStream fin2=new BufferedInputStream(new FileInputStream(new File(FILE)));
			crc32=new CRC32();
			while((data=fin2.read())!=-1){
				crc32.update(data);
			}
			checkSum=crc32.getValue();
			end=System.currentTimeMillis();
			System.out.println("使用IO的BufferedInputStream计算校验值="+checkSum+" 耗时："+(end-start));
			//输出： 
			fin2.close();
			
			//使用RandomAccessFile
			start=System.currentTimeMillis();
			RandomAccessFile fin3=new RandomAccessFile(new File(FILE),"r");
			crc32=new CRC32();
			/*while((data=fin3.read())!=-1){
				crc32.update(data);
			}//太耗时，先注释掉
*/			checkSum=crc32.getValue();
			end=System.currentTimeMillis();
			System.out.println("使用IO的RandomAccessFile计算校验值="+checkSum+" 耗时："+(end-start));
			//输出： 
			fin3.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
