package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;  
import java.io.IOException;  
  




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
            "C:\\Users\\Lilykos\\workspace\\ApacheCommonsExample\\ExampleFolder\\exampleTxt.txt";  
      
    private static final String PARENT_DIR =  
            "C:\\Users\\Lilykos\\workspace\\ApacheCommonsExample";  
  
    public static void runExample() throws IOException {  
        System.out.println("Utility Classes example...");  
        Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));
        System.out.println("MDC.get(\"LOGGER\")="+MDC.get("LOGGER"));
        logger.info("Utility Classes example..."); 
          
        // FilenameUtils  
          
        System.out.println("Full path of exampleTxt: " +  
                FilenameUtils.getFullPath(EXAMPLE_TXT_PATH));  
          
        System.out.println("Full name of exampleTxt: " +  
                FilenameUtils.getName(EXAMPLE_TXT_PATH));  
          
        System.out.println("Extension of exampleTxt: " +  
                FilenameUtils.getExtension(EXAMPLE_TXT_PATH));  
          
        System.out.println("Base name of exampleTxt: " +  
                FilenameUtils.getBaseName(EXAMPLE_TXT_PATH));  
          
          
        // FileUtils  
          
        // We can create a new File object using FileUtils.getFile(String)  
        // and then use this object to get information from the file.  
        File exampleFile = FileUtils.getFile(EXAMPLE_TXT_PATH);  
        LineIterator iter = FileUtils.lineIterator(exampleFile);  
          
        System.out.println("Contents of exampleTxt...");  
        while (iter.hasNext()) {  
            System.out.println("\t" + iter.next());  
        }  
        iter.close();  
          
        // We can check if a file exists somewhere inside a certain directory.  
        File parent = FileUtils.getFile(PARENT_DIR);  
        System.out.println("Parent directory contains exampleTxt file: " +  
                FileUtils.directoryContains(parent, exampleFile));  
          
          
        // IOCase  
          
        String str1 = "This is a new String.";  
        String str2 = "This is another new String, yes!";  
          
        System.out.println("Ends with string (case sensitive): " +  
                IOCase.SENSITIVE.checkEndsWith(str1, "string."));  
        System.out.println("Ends with string (case insensitive): " +  
                IOCase.INSENSITIVE.checkEndsWith(str1, "string."));  
          
        System.out.println("String equality: " +  
                IOCase.SENSITIVE.checkEquals(str1, str2));  
          
          
        // FileSystemUtils  //已经被弃用了，使用java.nio.file.FileStore
        /*System.out.println("Free disk space (in KB): " + FileSystemUtils.freeSpaceKb("C:"));  
        System.out.println("Free disk space (in MB): " + FileSystemUtils.freeSpaceKb("C:") / 1024); */ 
    }  
}  