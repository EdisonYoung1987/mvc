package com.edison.testJunit.oth.zookeeper.base;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.NodeExistsException;
import org.apache.zookeeper.ZooDefs.Ids;

public class ProducerUtil {
	/**
	 * 用于producer注册服务地址信息
	 * @throws InterruptedException 
	 * @throws KeeperException */
	public void registSelf(String serviceName,String addr) throws KeeperException, InterruptedException{
		
		String servicePath=intPath+"/"+serviceName;
		try{//持久节点
			zk.create(servicePath, "-".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}catch(NodeExistsException e){
			System.out.println("节点已存在，继续后续处理:"+servicePath);
		}
		
		servicePath=servicePath+"/"+"addr-";
		//地址采用临时节点 addr-000001,addr-00002....内容为ip地址
		zk.create(servicePath, addr.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
	}
}
