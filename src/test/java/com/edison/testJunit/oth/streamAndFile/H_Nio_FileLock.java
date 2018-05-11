package com.edison.testJunit.oth.streamAndFile;

import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**给文件分段加锁*/
public class H_Nio_FileLock {
	private static final String FILE="D:\\工作\\其他\\buff.txt";
	public static void main(String[] args) {
		try{
			Path path=Paths.get(FILE);
			FileChannel filechannel=FileChannel.open(path,  StandardOpenOption.WRITE,StandardOpenOption.READ);
			FileLock filelock=filechannel.lock(0, 4, false);
			ss
			filechannel.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
