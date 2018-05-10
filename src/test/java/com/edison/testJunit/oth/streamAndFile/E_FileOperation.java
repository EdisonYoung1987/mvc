package com.edison.testJunit.oth.streamAndFile;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**文件操作*/
public class E_FileOperation {
	private static final String FILE="D:\\工作\\其他\\fileOperation.txt";
	private static final String DIR="D:\\工作\\其他";
	
	public static void main(String[] args) {
		try{
			//java.io.File
			File file=new File(FILE);
			if(!file.exists()){
				file.createNewFile();
			}
			System.out.println("absolutePath="+file.getAbsolutePath());
			System.out.println("CanonicalPath="+file.getCanonicalPath());
			System.out.println("parent="+file.getParent());
			System.out.println("path="+file.getPath());
			System.out.println("name="+file.getName());
			file.delete();
			
			File dir=new File(DIR);
			System.out.println("整个磁盘空间大小   可用大小    未分配空间大小  ");
			System.out.println(dir.getTotalSpace()+" "+dir.getUsableSpace()+" "+dir.getFreeSpace());
			File[] files=dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {//文件所属路径 文件名称
					if(name.endsWith(".bat"))
						return false;
					else
						return true;
				}
			});
			for(File filetmp:files)
				System.out.println(filetmp.getAbsolutePath());
			
			//java.nio.file.Path Paths Files
//			Path path=Paths.get(FILE); 
			Path path=Paths.get("C:","工作","其他","\\");
			System.out.println(path.getParent()+"\\-"+path.getFileName());//getFileName返回最后一个组件(子目录或者文件)
			System.out.println(path.resolve("fileOperation.txt")); //组装两个路径，如果传入的参数是相对路径，则直接拼接
			System.out.println(path.resolve(DIR));                 //否则返回传入的参数
			System.out.println(path.resolveSibling("fileOperation.txt"));//组装当前路径的父路径+传入参数路径，如果传入参数为
			                                                          //绝对路径，则直接返回传入参数。
			System.out.println("相对路径="+path.relativize(Paths.get("C:\\")));//返回当前path相对于入参path的相对路径
		
			////java.nio.file.Files 可以实现简单读写
			Path path2=Paths.get(FILE);
			Files.write(path2, "这是一个文件第一行\r\n".getBytes("UTF-8"),
					                 StandardOpenOption.CREATE,
					                 StandardOpenOption.TRUNCATE_EXISTING,
					                 StandardOpenOption.WRITE);
			Files.write(path2, "第二行".getBytes("UTF-8"),StandardOpenOption.APPEND );
			List<String> lines=Files.readAllLines(path2, StandardCharsets.UTF_8);
			for(String line:lines)
				System.out.println(line);
//			Files还有增删copy查find等方法
//			Files还有获取owner，等属性的方法
			//Files的迭代 ，在文件数量特别大的时候，效率比Files.list()高得多
			Path pathtmp=Paths.get(DIR);
			DirectoryStream<Path> entries=Files.newDirectoryStream(pathtmp,"*.bat");
			for(Path pathss:entries){
				System.out.print(pathss.getFileName()+"  ");
			}
			System.out.println();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
