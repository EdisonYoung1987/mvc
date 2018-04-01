package com.edison.testJunit.oth.apache_commons_io;

import java.io.File;  
import java.io.IOException;  
  

import org.apache.commons.io.FileDeleteStrategy;  
import org.apache.commons.io.FileUtils;  
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;  
import org.apache.commons.io.monitor.FileAlterationMonitor;  
import org.apache.commons.io.monitor.FileAlterationObserver;  
import org.apache.commons.io.monitor.FileEntry;  
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
  
/**这个org.apache.commons.io.monitor 包中的工具类可以监视文件或者目录的变化，获得指定文件或者目录的相关信息<br>
 * 上面的特性的确很赞！分析下，这个工具类包下的工具类，可以允许我们创建跟踪文件或目录变化的监听句柄，当文件目录等发生任何变化，都可以用“观察者”的身份进行观察， 
其步骤如下： <br>
   1） 创建要监听的文件对象  <br>
   2） 创建FileAlterationObserver 监听对象 <br>
   在上面的例子中， File parentDir = FileUtils.getFile(PARENT_DIR);  <br>
  FileAlterationObserver observer = new FileAlterationObserver(parentDir);  <br>
   创建的是监视parentDir目录的变化，  <br>
   3） 为观察器创建FileAlterationListenerAdaptor的内部匿名类，增加对文件及目录的增加删除的监听  <br>
   4） 创建FileAlterationMonitor监听类，每隔500ms监听目录下的变化，其中开启监视是用monitor的start方法即可。  <br>
 * */  
public final class FileMonitorExample {  
      
    private static final String EXAMPLE_PATH =  "D:\\Youxun\\Install\\exampleFileEntry.txt";  
    private static final String MONITOR_DIR =  "D:\\Youxun\\Install";   //监视的目录
    private static final String NEW_DIR =  "D:\\Youxun\\Install\\newDir";  
    private static final String NEW_DIR_FILE =  "D:\\Youxun\\Install\\newDir\\newFile.txt";  
    private static final String NEW_FILE =  "D:\\Youxun\\Install\\newFile.txt";  

    private static Logger logger=LoggerFactory.getLogger(MDC.get("LOGGER"));
    public static void runExample() {  
        logger.info("File Monitor example...");  
          
        // FileEntry  
        FileEntry entry = new FileEntry(FileUtils.getFile(EXAMPLE_PATH));  
        logger.info("File monitored: " + entry.getFile());  
        logger.info("File name: " + entry.getName());  
        logger.info("Is the file a directory?: " + entry.isDirectory());  
        logger.info("Does the file exist?: " + entry.isExists());  


        // File Monitoring  监控该目录是否新建子目录，文件，删除等
        File parentDir = FileUtils.getFile(MONITOR_DIR);  
          
        FileAlterationObserver observer = new FileAlterationObserver(parentDir);  
        observer.addListener(new FileAlterationListenerAdaptor() {//一个监视器  
                @Override  
                public void onFileCreate(File file) {  
                    logger.info("File created: " + file.getName());  
                }  
                  
                @Override  
                public void onFileDelete(File file) {  
                    logger.info("File deleted: " + file.getName());  
                }  
                  
                @Override  
                public void onDirectoryCreate(File dir) {  
                    logger.info("Directory created: " + dir.getName());  
                }  
                  
                @Override  
                public void onDirectoryDelete(File dir) {  
                    logger.info("Directory deleted: " + dir.getName());  
                }  
        });  
          
        FileAlterationMonitor monitor = new FileAlterationMonitor(500, observer);  //启动监视器
        try {  
            monitor.start();  
          
            // After we attached the monitor, we can create some files and directories  
            // and see what happens!  
            File newDir = new File(NEW_DIR);  
            File newFile = new File(NEW_FILE);  
            File newDirFile=new File(NEW_DIR_FILE); //在子目录下建立新文件也会被监控到
            
            newDir.mkdirs();  
            newDirFile.createNewFile();
            newFile.createNewFile();  
                  
            Thread.sleep(5000);  
            
            FileDeleteStrategy.NORMAL.delete(newDirFile);  //删除操作
            FileDeleteStrategy.NORMAL.delete(newDir);  //删除操作
            FileDeleteStrategy.NORMAL.delete(newFile);  
              
            Thread.sleep(1000);  
              
            monitor.stop();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}  