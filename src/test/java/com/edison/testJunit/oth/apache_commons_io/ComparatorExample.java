package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;  
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;  
  



import org.apache.commons.io.FileUtils;  
import org.apache.commons.io.IOCase;  
import org.apache.commons.io.comparator.LastModifiedFileComparator;  
import org.apache.commons.io.comparator.NameFileComparator;  
import org.apache.commons.io.comparator.SizeFileComparator;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

/**
 * 文件比较器.<br>
 * NameFileComparator: 文件名的比较器，可以进行文件名称排序；<br> 
SizeFileComparator: 按照文件大小比较 <br>
LastModifiedFileComparator: 根据最新修改日期比较 */
public final class ComparatorExample {  
      
    private static final String PARENT_DIR = "D:\\Youxun\\Install";  
    private static final String FILE_1 =  "D:\\Youxun\\Install\\example";  
    private static final String FILE_2 =   "D:\\Youxun\\Install\\exampleTxt.txt";  
      
    public static void runExample() {  
        Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));

        logger.info("Comparator example...");  
          
        //NameFileComparator  
          
        // Let's get a directory as a File object  
        // and sort all its files.  
        File parentDir = FileUtils.getFile(PARENT_DIR);  
        NameFileComparator comparator = new NameFileComparator(IOCase.SENSITIVE);  
        File[] sortedFiles = comparator.sort(parentDir.listFiles());  
          
        logger.info("Sorted by name files in parent directory: ");  
        for (File file: sortedFiles) {  
            System.out.println("\t"+ file.getAbsolutePath());  
        }  
          
          
        // SizeFileComparator  
          
        // We can compare files based on their size.  
        // The boolean in the constructor is about the directories.  
        //      true: directory's contents count to the size.  
        //      false: directory is considered zero size.  
        SizeFileComparator sizeComparator = new SizeFileComparator(true);  
        File[] sizeFiles = sizeComparator.sort(parentDir.listFiles());  
          
        logger.info("Sorted by size files in parent directory: ");  
        for (File file: sizeFiles) {  
            System.out.println("\t"+ file.getName() + " with size (kb): " + file.length());  
        }  
          
          
        // LastModifiedFileComparator  
          
        // We can use this class to find which file was more recently modified.  
        LastModifiedFileComparator lastModified = new LastModifiedFileComparator();  
        File[] lastModifiedFiles = lastModified.sort(parentDir.listFiles());  
          
        logger.info("Sorted by last modified files in parent directory: ");  
        for (File file: lastModifiedFiles) {  
            Date modified = new Date(file.lastModified());  
            logger.info("\t"+ file.getName() + " last modified on: " + modified);  
        }  
          
        //TODO 这一段需要重新整理
        // Or, we can also compare 2 specific files and find which one was last modified.  
        //      returns > 0 if the first file was last modified.  
        //      returns  0)  
        /*    logger.info("File " + file1.getName() + " was modified last because...");  
        else  
            logger.info("File " + file2.getName() + "was modified last because...");  */
       File file1=new File(FILE_1);
       File file2=new File(FILE_2);
       DateFormat dFormat=new SimpleDateFormat();
      /* try{
	       logger.info("\t"+ file1.getName() + " last modified on: " +   dFormat.parse(""+file1.lastModified()));  
	       logger.info("\t"+ file2.getName() + " last modified on: " +  dFormat.parse(""+file2.lastModified()));  
       }catch(Exception e){
    	   e.printStackTrace();
       }*/
    }  
}  
