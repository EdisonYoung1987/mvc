package com.edison.testJunit.oth.apache_commons_io;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.File;  
import java.io.IOException;  
  

import org.apache.commons.io.FileUtils;  
import org.apache.commons.io.input.TeeInputStream;  
import org.apache.commons.io.input.XmlStreamReader;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
  
/**在 common io的org.apache.commons.io.input 包中，有各种对InputStream的实现类： 
我们看下其中的TeeInputStream, ，它接受InputStream和Outputstream参数*/  
public final class InputExample {  
    private static Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));
   
    private static final String XML_PATH =  
            "C:\\Users\\Administrator\\git\\mvc\\src\\main\\resources\\logback.xml";  
      
    private static final String INPUT = "This should go to the output.";  
  
    public static void runExample() {  
        logger.info("Input example...");  
        XmlStreamReader xmlReader = null;  
        TeeInputStream tee = null;  
          
        try {  
              
            // XmlStreamReader  
              
            // We can read an xml file and get its encoding.  
            File xml = FileUtils.getFile(XML_PATH);  
              
            xmlReader = new XmlStreamReader(xml);  //感觉意义不大，还是专门用其他xml解析工具库
            logger.info("XML encoding: " + xmlReader.getEncoding());  
  
              
            // TeeInputStream  
            // This very useful class copies an input stream to an output stream  
            // and closes both using only one close() method (by defining the 3rd  
            // constructor parameter as true).  
            ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));  
            ByteArrayOutputStream out = new ByteArrayOutputStream();  
              
            tee = new TeeInputStream(in, out, true);  
            tee.read(new byte[INPUT.length()]);  
  
            logger.info("Output stream: " + out.toString());           
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try { xmlReader.close(); }  
            catch (IOException e) { e.printStackTrace(); }  
              
            try { tee.close(); }  
            catch (IOException e) { e.printStackTrace(); }  
        }  
    }  
}  