package com.edison.testJunit.oth.streamAndFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * 文本读写<p>
 * 主要针对二进制和文本进行读写，而文本的存储涉及到字符集的选择<br>
 * */
public class B_ReaderAndWriter {
	private static final String INFILE="D:\\工作\\其他\\传递外部文档.txt";
	private static final String OUTFILE="D:\\工作\\其他\\传递外部文档2.txt";
	private static final String SEP=System.getProperty("line.separator");

	public static void main(String[] args) {
		//InputStreamReader（字节流通向字符流的桥梁）
		//BufferedReader(包装InputStreamReader)
		
		//OutputStreamWriter（字节流通向字符流的桥梁）
		//BufferedWriter
		//FileWriter（继承于OutputStreamWriter）
		//PrintWriter
		try{
			InputStreamReader inR=new InputStreamReader(new FileInputStream(INFILE), "UTF-8");
			OutputStreamWriter outW=new OutputStreamWriter(new FileOutputStream(OUTFILE), "UTF-8");
			int ch;
			while((ch=inR.read())!=-1){ //这里是读取一个字符，根据字符集编码对应多个字节
				System.out.print((char)ch);
				outW.write(ch);
			}
			
			inR.close();
			outW.close();
			
			System.out.println();
			System.out.println("-----------------分割线------------------------------");
			
			//有readline方法，较方便
			BufferedReader buffR=new BufferedReader(new InputStreamReader(new FileInputStream(INFILE), "UTF-8"));
			BufferedWriter buffW=new BufferedWriter(new OutputStreamWriter(new FileOutputStream(OUTFILE), "UTF-8"));
			String line;
			while((line=buffR.readLine())!=null){
				System.out.println(line);
				buffW.write(line);
			}
			buffR.close();
			buffW.close();
			
			System.out.println();
			System.out.println("-----------------分割线------------------------------");
			//PrintWriter定义更简洁
			PrintWriter printW=new PrintWriter("D:\\工作\\其他\\传递外部文档3.txt", "UTF-8");
			printW.write("张三");
			printW.println();//换行1 line.separator
			printW.write("------------"+SEP); //换行2
			printW.write("TEL: 15123926944");
			printW.close();
			/*输出结果：
			 * 张三
			   ------------
			   TEL: 15123926944*/
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
