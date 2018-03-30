package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;  
import java.io.IOException;  
import java.util.Collection;

import org.apache.commons.io.FileSystemUtils;  //已经被弃用了，使用java.nio.file.FileStore
import org.apache.commons.io.FileUtils;  
import org.apache.commons.io.FilenameUtils;  
import org.apache.commons.io.LineIterator;  
import org.apache.commons.io.IOCase;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

public final class UtilityExample {  
      
    // We are using the file exampleTxt.txt in the folder ExampleFolder,  
    // and we need to provide the full path to the Utility classes.  
    private static final String EXAMPLE_TXT_PATH =  
            "C:\\Users\\Edison\\git\\mvc\\src\\main\\resources\\apache_commons_io\\exampleTxt.txt";  
      
    private static final String PARENT_DIR =  
            "C:\\Users\\Lilykos\\workspace\\ApacheCommonsExample";  
  
    public static void runExample() throws IOException {  
	    Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));
	    logger.info("Utility Classes example..."); 
	    
	    //下面是获取项目路径和资源路径的三个方法
	    logger.info(System.getProperty("user.dir"));//这是获取java本地项目的绝对路径 C:\Users\Edison\git\mvc
	    
        // 下面返回值/C:/Users/Edison/git/mvc/target/test-classes/ 这个实际上返回的是测试类最开始寻找的资源文件路径
        //即test/resources对应的到处路径target/test-classes下面去找
        logger.info(Thread.currentThread().getContextClassLoader().getResource("").getPath());
        
        // 返回值 /C:/Users/Edison/git/mvc/target/classes/apache_commons_io/exampleTxt.txt 测试类在上面那个路径如果找不到
        // 就会在main/resources对应的导出路径target/classes下面去找
        logger.info(Thread.currentThread().getContextClassLoader().getResource("apache_commons_io/exampleTxt.txt").getPath());

        // FilenameUtils 顾名思义，应该是与文件名称相关的工具包 
        //C:\\Users\\Edison\\git\\mvc\\src\\main\\resources\\apache_commons_io\\exampleTxt.txt
        logger.info("Full path of exampleTxt: " +  FilenameUtils.getFullPath(EXAMPLE_TXT_PATH)); //文件前面的路径，含/或者\,还有个方法不含
        logger.info("Full name of exampleTxt: " +  FilenameUtils.getName(EXAMPLE_TXT_PATH)); //返回文件名或者""或者目录
        logger.info("Extension of exampleTxt: " +   FilenameUtils.getExtension(EXAMPLE_TXT_PATH)); //后缀或"" 
        logger.info("Base name of exampleTxt: " +  FilenameUtils.getBaseName(EXAMPLE_TXT_PATH));  //无后缀文件或目录
          
          
        // FileUtils 打开\关闭\读写文件util  还有对目录的操作
        //1.0 目录操作
        File dir=FileUtils.getFile(System.getProperty("user.dir"));//C:\Users\Edison\git\mvc
        Collection<File> fileCollection=FileUtils.listFiles(dir, new String[]{"txt","xml"}, true); //递归查找目录下以txt或xml结尾的文件
        logger.info("该目录{}下有以下文件",dir.getPath());
        for(File file:fileCollection){//列出所有文件
        	if(file.getAbsolutePath().startsWith(""+dir.getPath()+"\\src")){
        		System.out.println(file.getAbsolutePath());//这是包含路径名+文件名的
        	}
        }
        
        //2.0 文件操作
        File exampleFile = FileUtils.getFile(EXAMPLE_TXT_PATH); //java io的new File(xxx)是不检查文件是否存在的
        if(!exampleFile.exists()){//先查看example文件是否存在
        	logger.error("{}不存在",EXAMPLE_TXT_PATH);
        	return;
        }
          
        //目录是否包含某文件  
        File parent = FileUtils.getFile(PARENT_DIR);  
        logger.info("Parent directory contains exampleTxt file: " +FileUtils.directoryContains(parent, exampleFile));  
          
        //读取文件内容 
        LineIterator iter = FileUtils.lineIterator(exampleFile);  //FileUtils提供的行浏览器，同时打开文件
        logger.info("Contents of exampleTxt...");  
        while (iter.hasNext()) {  
            System.out.println("\t" + iter.next());  
        }  
        iter.close();  //需要关闭文件
        
        //转码读写文件
        File infile=FileUtils.getFile("C:\\Users\\Edison\\git\\mvc\\src\\main\\java\\com\\edison\\controller\\HelloSpringmvcController.java");
        File outfile=FileUtils.getFile("C:\\Users\\Edison\\git\\mvc\\src\\main\\java\\com\\edison\\controller\\HelloSpringmvcController2.java");

        String  dataStr=FileUtils.readFileToString(infile, "GBK");//GBK转UTF8 成功 这里有个问题，如果infile本来就是utf8，读出来是乱码
        FileUtils.writeStringToFile(outfile, dataStr, "UTF-8");
        
        //IOCASE -主要针对String大小写的吧
        String str1 = "This is a new String.";  
        String str2 = "This is another new String, yes!";  
          
        logger.info("Ends with string (case sensitive): " +  
                IOCase.SENSITIVE.checkEndsWith(str1, "string."));  //false-大小写敏感 IOCASE是个枚举
        logger.info("Ends with string (case insensitive): " +  
                IOCase.INSENSITIVE.checkEndsWith(str1, "string."));  //true-大小写不敏感
        logger.info("String equality: " +   IOCase.SENSITIVE.checkEquals(str1, str2)); //false 
          
    }  
}  