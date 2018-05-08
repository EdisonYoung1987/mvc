package com.edison.testJunit.oth.streamAndFile;

import java.io.RandomAccessFile;

/**
 * 随机读取文件类<p>
 * */
public class C_RandomAccessFile {
	private static final String FILE="D:\\工作\\其他\\rand.txt";
	private static final String SPE=System.getProperty("line.separator"); //结尾符 win \r\n linux \n  
	private static final String CHINESECONT="用户：张三"+SPE; //一行中文用来测试乱码处理
	
	public static void main(String[] args) {
		try{
			//1.0 写一个文件
			/*File file=new File(FILE);
			if(file.exists()){//要删除文件，否则多次写入，如果每次写入内容不同，可能会有上次写入的参与内容
				file.delete();
			}*/
			RandomAccessFile randfile=new RandomAccessFile(FILE, "rw");//"r", "rw", "rws", "rwd"
			
			randfile.setLength(0);//这样就能达到清除文件内容的效果
			
			randfile.writeBytes(CHINESECONT); //加上换行符 这种方式读出来是乱码
			System.out.println(randfile.getFilePointer());//打印当前指针位置7
			randfile.writeInt(28); //年龄 
			System.out.println(randfile.getFilePointer());//打印当前指针位置7+4
			randfile.writeDouble(15000.29); //工资
			System.out.println(randfile.getFilePointer());//打印当前指针位置7+4+8
			randfile.writeChar('M'); //性别
			System.out.println(randfile.getFilePointer());//打印当前指针位置7+4+8+2
			randfile.writeBoolean(true); //已满1年
			System.out.println(randfile.getFilePointer());//打印当前指针位置 7+4+8+2+1
			
			//2.0 读取文件内容
			randfile.seek(0);//跳到文件开头
			System.out.println("本次读出来的内容："+randfile.readLine()); //如果这一行有中文，打印出来是乱码

			randfile.seek(randfile.length());//跳到末尾准备重写该中文内容
			randfile.write((CHINESECONT).getBytes()); //换种方式写中文
			System.out.println(randfile.getFilePointer());//打印当前指针位置 7+4+8+2+1+17
			
			randfile.seek(22);//重读该中文内容
			byte[] bytes=new byte[(CHINESECONT).getBytes().length]; //这里如果定义数组过长，下一步打印就会有很多空白
			randfile.read(bytes, 0, (CHINESECONT).getBytes().length);//从文件中读取多长的数据
			System.out.println("["+new String(bytes)+"]"); //这次正常了

			//开始随机读取刚刚写的内容
			randfile.seek(7);//跳过第一次的中文
			System.out.println(randfile.readInt());
			System.out.println(randfile.readDouble());
			System.out.println(randfile.readChar());
			System.out.println(randfile.readBoolean());

			randfile.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
