package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class ConsumerUtil {
	private static ZookeeperUtil zu; //zk工具
	private static ZooKeeper zk; //zk连接
	private String intPath; //初始化路径，如/SERVICE中的SERVICE
	
	/**
	 * 生产者初始化工作：建立于zookeeper集群的连接
	 * @throws InterruptedException 
	 * @throws KeeperException 
	 * @throws IOException */
	public void initPro() throws IOException, KeeperException, InterruptedException{
		zu=new ZookeeperUtil();
		this.intPath=zu.getProperty("intPath");
		zk=zu.getZookeeper();
	}
	
	public ConsumerUtil() throws IOException, KeeperException, InterruptedException{
		initPro();
	}
	
}
