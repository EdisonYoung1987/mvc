package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;

public class ProducerUtil {
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
	
	public ProducerUtil() throws IOException, KeeperException, InterruptedException{
		initPro();
	}
	
	/**
	 * 用于producer注册服务地址信息
	 * @throws InterruptedException 
	 * @throws KeeperException */
	public void registSelf(String serviceName,String addr) throws KeeperException, InterruptedException{
		
		String servicePath=intPath+"/"+serviceName;
		try{//持久节点  内容为ARRS,这样客户端就可以根据该内容确定其子节点都是地址信息
			zk.create(servicePath, "ADDRS".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}catch(NodeExistsException e){
			System.out.println("节点已存在，继续后续处理:"+servicePath);
		}
		
		servicePath=servicePath+"/"+"addr-";
		//地址采用临时节点 addr-000001,addr-00002....内容为ip地址
		zk.create(servicePath, addr.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
	
	/**注销自己已注册的服务*/
	public void cancelSel(String serviceName){
		//TODO
		System.out.println("还未完成");
	}
}
