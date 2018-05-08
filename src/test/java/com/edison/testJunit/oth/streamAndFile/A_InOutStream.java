package com.edison.testJunit.oth.streamAndFile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 字节读写流<p>
 * read()/write()会阻塞线程直到资源可读/已写完<br>
 * in.available()--返回输入流当前可读字节数量，是非阻塞的，立即返回结果，如果大于0，则可用read()读取数据<br>
 * flush/close()释放系统资源，同时将输出缓冲区的内容输送出去，如果既不flush()，也不close()，则输出缓冲区的东西就一直不会写到目标中<br>
 * long skip(long n) --跳过n个字节，如果碰到文件末尾，返回值可能小于n<br>
 * boolean markSupport()/mark(int limit)/reset():是否支持打标记/打标机/回到最近的标记
 * */
public class A_InOutStream {
	private static final String INFILE="D:\\工作\\其他\\传递外部文档.txt";
	private static final String OUTFILE="D:\\工作\\其他\\传递外部文档2.txt";
	
	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));//工作路径 C:\Users\Edison\git\mvc
		
		//组合文件处理器
		try{
			//纯字节读写
//			FileInputStream fi=new FileInputStream(new File("D:\\工作\\其他\\传递外部文档.txt")); //方法1
			FileInputStream fin=new FileInputStream(INFILE);           //方法2
			FileOutputStream fout=new FileOutputStream(OUTFILE);
			Byte bt;
			while( (bt=(byte)fin.read()) !=-1){//字节读写-实际上也可以把bt定义成int去接
				System.out.print((char)(int)bt);//中文会产生乱码，因为一个中文有多个字节组成
				fout.write(bt);
			}
			fin.close();
			fout.close();
			System.out.println();
			System.out.println("------------------华丽的分隔符-------------------------");
			
			//支持字节读写，也可以包装成类型读写
			DataInputStream din=new DataInputStream(new FileInputStream(INFILE)); //包装InputStream
			DataOutputStream dout=new DataOutputStream(new FileOutputStream(OUTFILE)); 
			char ch; //实际上DataInputStream除了支持8种基本类型外还支持readLine()读取String
			for(;;){
				try{
//					din.readLine() ;//已被BufferedReader.readLine()替代
					ch=din.readChar();//实际上读取两个byte组成一个CHAR返回
					System.out.print(ch);//所以全是乱码
					dout.writeChar(ch); //DataOutput在写的时候是定长写，如wirteChar就是写两个字节，writeInt就是写4个字节
				}catch(EOFException e){//readChar在读到末尾时抛该异常
					break;
				}
			}
			din.close();
			dout.close();
			System.out.println();
			System.out.println("------------------华丽的分隔符-------------------------");

			
			//给输入输出流增加缓存buffer,指定缓存大小为1024字节，这样每次调用read时，实际上是多次调用系统的读取方
			//法native int read0()，直到buffer满获取读完，读写方法倒是没变
			BufferedInputStream buffin=new BufferedInputStream(new FileInputStream(INFILE),1024);
//			BufferedOutputStream buffout=new BufferedOutputStream(new FileOutputStream(OUTFILE),1024);
			DataInputStream din2=new DataInputStream(buffin); //包装BufferedInputStream
//			DataOutputStream dout2=new DataOutputStream(buffout);
			
			//这里的读写和上面没有包装用户缓存一样，就不再写了 不过OUTFILE就是空的了
			
			din2.close();
//			dout2.close();
			buffin.close();
//			buffout.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
