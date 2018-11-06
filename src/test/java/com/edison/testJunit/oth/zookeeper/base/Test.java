package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static final Logger logger = LoggerFactory.getLogger( Test.class );

	private static final String ZK_PATH = "/nileader";
	private static final String CHILDREN_PATH 	= "/nileader/ch";
	
	public static void main( String[] args ) throws InterruptedException, IOException, KeeperException {
		ZookeeperUtil zu=new ZookeeperUtil();

		System.out.println("进入等待");
		while(true){
			System.out.println("SLEEP");
			zu.createPath("/test", "123", CreateMode.PERSISTENT);
			Thread.sleep(10000);
			zu.deleteNode("/test");
			System.out.println("WAKEUP");
			Thread.sleep(10000);
		}
		
		//先把自己这个watcher注册到/上
//		zu.getChildren("/"); //这里设置只对"/"的子节点变更设置和删除敏感
//		logger.info("已设置对/的子节点的关注");
		
		/*//清理节点
		zu.deleteNode("/test");
		Thread.sleep(3000);  //这里之所以要sleep 3秒，是因为delete和和后面的创建离得太近，完全有可能zookeeper的watcher
							//事件到达的时候，ZK_PATH子节点已经创建，测试时候会以为删除/test没有触发事件
		zu.deleteNode( ZK_PATH ); //因为当前是不存在该节点的，所以前面的watcher并未被触发
		Thread.sleep(3000);
		if ( zu.createPath( ZK_PATH, System.currentTimeMillis()+"",CreateMode.PERSISTENT ) ) {//这里触发了对/子节点的关注watcher
			Thread.sleep( 3000 );
			zu.createPath("/test", "test", CreateMode.PERSISTENT);
			Thread.sleep(3000);
			//读取数据
			logger.error("ZK_PATH:{}",zu.readData( ZK_PATH ));
			//读取子节点
			logger.error("zu.getChildren( ZK_PATH )={}",zu.getChildren( ZK_PATH ));
			
			//更新数据
			logger.error("更新数据：{}",ZK_PATH);
			logger.error("{}",zu.writeData( ZK_PATH, System.currentTimeMillis()+"" ));

			//创建子节点
			zu.createPath( CHILDREN_PATH, System.currentTimeMillis()+"",CreateMode.PERSISTENT );
			Thread.sleep(3000);
		}
		Thread.sleep( 3000 );
		//清理节点
		zu.deleteNode( ZK_PATH );
		Thread.sleep( 3000 );*/
//		zu.releaseConnection();
	}
}
