package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;  
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;  
import org.apache.commons.io.IOCase;  
import org.apache.commons.io.filefilter.AndFileFilter;  
import org.apache.commons.io.filefilter.NameFileFilter;  
import org.apache.commons.io.filefilter.NotFileFilter;  
import org.apache.commons.io.filefilter.OrFileFilter;  
import org.apache.commons.io.filefilter.PrefixFileFilter;  
import org.apache.commons.io.filefilter.SuffixFileFilter;  
import org.apache.commons.io.filefilter.WildcardFileFilter;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;


/**
 * 过滤器<br>
 * 1. 实际上只是帮用户实现了FileFilter<br>
 * 2. 最后还是要调用File.listxxx(),而且listFiles还有歧义<p>
 * 可以看清晰看到，使用过滤器，可以分别在指定的目录下，寻找符合条件的文件，<br>
 * 比如以什么开头的文件名，支持通配符，甚至支持多个过滤器进行或的操作*/
public final class FiltersExample { //可以用来查找文件 
      
    private static final String PARENT_DIR = "D:\\Youxun";  
    private static void listFilesRecursively(File dir,List<File> files){//递归查看目录的子孙目录及文件
    	if(!dir.isDirectory()){//非目录
    		files.add(dir);
    		return ;
    	}
    	files.add(dir); //将其本身加入到files，哪怕是空目录
    	
    	for(File file:dir.listFiles()){
    		if(file.isDirectory()){
    			listFilesRecursively(file, files);
    		}else{
    			files.add(file);
    		}
    	}
    }
    public static void runExample() {  
        Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));

        logger.info("File Filter example...");  
        
        //递归查看所有文件
        File dir = FileUtils.getFile(PARENT_DIR);  
        List<File> files=new ArrayList<File>();
        listFilesRecursively(dir, files);
        for(File file:files){
        	System.out.println(file.getAbsolutePath());
        }
        
         //都不能递归查看子孙目录，没多大意思哦 
        // NameFileFilter  
        // Right now, in the parent directory we have 3 files:  
        //      directory example  
        //      file exampleEntry.txt  
        //      file exampleTxt.txt  
          
        // Get all the files in the specified directory  
        // that are named "example".  
        String[] acceptedNames = {"example", "exampleTxt.txt","aaexamplef.txt"};  
        for (String file: dir.list(new NameFileFilter(acceptedNames, IOCase.INSENSITIVE))) {  
            logger.info("File found, named: " + file);  
        }  
          
          
        //WildcardFileFilter  //通配符
        // We can use wildcards in order to get less specific results  
        //      ? used for 1 missing char  
        //      * used for multiple missing chars  
        for (String file: dir.list(new WildcardFileFilter("*ample*"))) {  
            logger.info("Wildcard file found, named: " + file);  
        }  
          
          
        // PrefixFileFilter   前缀 startsWith
        // We can also use the equivalent of  startsWith 
        // for filtering files.  
        for (String file: dir.list(new PrefixFileFilter("example"))) {  
            logger.info("Prefix file found, named: " + file);  
        }  
          
          
        // SuffixFileFilter  后缀 endsWith
        // We can also use the equivalent of endsWith  
        // for filtering files.  
        for (String file: dir.list(new SuffixFileFilter(".txt"))) {  
            logger.info("Suffix file found, named: " + file);  
        }  
          
          
        // OrFileFilter  相当于一个grep xxx|aaa  
        // We can use some filters of filters.  
        // in this case, we use a filter to apply a logical   
        // or between our filters.  
        for (String file: dir.list(new OrFileFilter(  
                new WildcardFileFilter("*ample*"), new SuffixFileFilter(".txt")))) {  
            logger.info("Or file found, named: " + file);  
        }  
          
        // And this can become very detailed.  相当于一个组合查找的东西 find |grep  |grep
        // Eg, get all the files that have "ample" in their name  
        // but they are not text files (so they have no ".txt" extension.  
        for (String file: dir.list(new AndFileFilter( // we will match 2 filters...  
                new WildcardFileFilter("*ample*"), // ...the 1st is a wildcard...  
                new NotFileFilter(new SuffixFileFilter(".txt"))))) { // ...and the 2nd is NOT .txt.  
            logger.info("And/Not file found, named: " + file);  
        }  
    }  
}  