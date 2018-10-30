package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 主要是zk连接的初始化工作、创建zk初始数据等*/
public class ZookeeperUtil implements Watcher{
	private static final Logger LOG =  LoggerFactory.getLogger( AllZooKeeperWatcher.class );

	private  ZooKeeper  zk=null;
	private static Properties property;
	private static final int SESSIONTIMEOUT=3*2000;
	
	/** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
	/**
	 * 初始化zookeeper连接，并创建初始目录*/
	public  void initZk() throws IOException, KeeperException, InterruptedException{
		//通过配置文件获取zk地址列表，实际spring项目中可以通过注解@Value("${zookeeper_addrs}")
		property=new Properties();
		property.load(ClassLoader.getSystemResourceAsStream("dataSource.properties"));
		String addrs=getProperty("zookeeper_addrs");
		
		String[] addrList=addrs.split(",");
		for(String addr:addrList){
			LOG.info("地址:"+addr);
		}
		
		//多个地址端口用,隔开192.168.111.130:2181,192.168.111.130:2182,192.168.111.130:2183
		this.zk=new ZooKeeper(addrs, SESSIONTIMEOUT, this);//zk客户端连接是个异步过程
		connectedSemaphore.await();//直到连接线程返回。

		//创建初始公共目录
		String intPath=getProperty("intPath");//初始化的公共目录
		try{
			zk.create(intPath, "-".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}catch(NodeExistsException e){
			System.out.println("节点已存在，继续后续处理:"+intPath);
		}
		return;
	}
	
	public ZookeeperUtil() throws IOException, KeeperException, InterruptedException{
		initZk();
	}

	public void process(WatchedEvent event) {
		System.out.println("总的watcher");
	}
	
	/**根据key获取参数值*/
	public String getProperty(String key){
		return property.getProperty(key);
	}
	
	/**获取zookeeper连接*/
	public ZooKeeper getZookeeper(){
		return this.zk;
	}
	
}
