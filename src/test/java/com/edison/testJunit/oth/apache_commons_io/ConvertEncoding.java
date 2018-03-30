package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

/**主要是为了转换以前用gbk写的旧代码/*/
public class ConvertEncoding {
	private final static String PROJECTDIR="C:\\Users\\Administrator\\git\\mvc\\";//项目的根目录
	private final static String OUT_PROJECTDIR="D:\\Users\\Administrator\\git\\mvc\\";//转换后存放路径
	
	private final static String[] excludeDirs={
		    "C:\\Users\\Administrator\\git\\mvc\\src\\test\\java\\com\\edison\\testJunit\\oth\\apache_commons_io",
		    "C:\\Users\\Administrator\\git\\mvc\\target",
		    "C:\\Users\\Administrator\\git\\mvc\\src\\main\\webapp",
		    "C:\\Users\\Administrator\\git\\mvc\\.settings"};//不用转换的路径
	private final static String[] excludeFiles={
		"testspringContext.xml","springContext.xml","logback.xml","amortized.xml","pom.xml"
	}; //这些不用转换的文件都是独一无二的，所以没有按完整路径来
	
	public static void main(String[] args) {
		File inBaseDir=FileUtils.getFile(PROJECTDIR); //待转码文件根路径
		File outBaseDir=FileUtils.getFile(OUT_PROJECTDIR);//输出文件根路径 

		Collection<File> fileCollection=FileUtils.listFiles(inBaseDir, new String[]{"java","xml","properties"}, true); //读取xml和java及properties文件
		outer: for(File inFile:fileCollection){
			String path=inFile.getParent(); //路径
			String fileName=inFile.getName();//文件名
			
			for(String excludeDir:excludeDirs){//排除的目录
				if(path.startsWith(excludeDir)){
					continue outer;
				}
			}
			for(String excludeFile:excludeFiles){//排除的文件
				if(fileName.equals(excludeFile)){
					continue outer;
				}
			}
			System.out.println(path+"\\"+fileName);
			
			//拼接转换后文件的路径
			String outFileName=OUT_PROJECTDIR+path.substring(PROJECTDIR.length())+"\\"+fileName;
			System.out.println(outFileName);
			File outFile=FileUtils.getFile(outFileName);
			
			try{
				String strData=FileUtils.readFileToString(inFile, "GBK");
				FileUtils.write(outFile, strData, "UTF-8");
			}catch(Exception e){
				e.printStackTrace();
				continue;
			}
		}
	}

}
