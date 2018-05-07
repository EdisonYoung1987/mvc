package com.edison.testJunit.oth.streamAndFile;

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

	public static void main(String[] args) {
		System.out.println(System.getProperty("user.dir"));
	}

}
