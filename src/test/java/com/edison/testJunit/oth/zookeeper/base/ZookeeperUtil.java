package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;
import java.util.Properties;

import org.apache.zookeeper.ZooKeeper;


/**
 * 主要是zk连接的初始化工作等*/
public class ZookeeperUtil {
	private static ZooKeeper  zk=null;
	public static void initZk() throws IOException{
		//通过配置文件获取zk地址列表，实际spring项目中可以通过注解
		//@Value("${zookeeper_addrs}")
		Properties property=new Properties();
		property.load(ClassLoader.getSystemResourceAsStream("dataSource.properties"));
		String addrs=property.getProperty("zookeeper_addrs");
		
		String[] addrList=addrs.split(",");
		for(String addr:addrList){
			System.out.println("地址:"+addr);
			
		}
		zk=new Z
		return true;
	}
}
