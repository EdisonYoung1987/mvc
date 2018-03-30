package com.edison.testJunit.oth.apache_commons_io;

import java.io.ByteArrayInputStream;  
import java.io.ByteArrayOutputStream;  
import java.io.IOException;  
  

import org.apache.commons.io.input.TeeInputStream;  
import org.apache.commons.io.output.TeeOutputStream;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
  
public final class OutputExample {  
    private static Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));

    private static final String INPUT = "This should go to the output.";  
  
    public static void runExample() {  
        logger.info("Output example...");  
        TeeInputStream teeIn = null;  
        TeeOutputStream teeOut = null;  
          
        try {  
              
            // TeeOutputStream  
              
            ByteArrayInputStream in = new ByteArrayInputStream(INPUT.getBytes("US-ASCII"));  
            ByteArrayOutputStream out1 = new ByteArrayOutputStream();  
            ByteArrayOutputStream out2 = new ByteArrayOutputStream();  
              
            teeOut = new TeeOutputStream(out1, out2);  
            teeIn = new TeeInputStream(in, teeOut, true);  
            teeIn.read(new byte[INPUT.length()]);  
  
            logger.info("Output stream 1: " + out1.toString());  
            logger.info("Output stream 2: " + out2.toString());  
              
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // No need to close teeOut. When teeIn closes, it will also close its  
            // Output stream (which is teeOut), which will in turn close the 2  
            // branches (out1, out2).  
            try { teeIn.close(); }  
            catch (IOException e) { e.printStackTrace(); }  
        }  
    }  
}  
