package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  《ZooKeeper 事件类型详解》
 * @author nileader/nileader@gmail.com
 * 
 */
public class AllZooKeeperWatcher implements Watcher{

	private static final Logger logger = LoggerFactory.getLogger( AllZooKeeperWatcher.class );
	AtomicInteger seq = new AtomicInteger();
	
	private ZookeeperUtil zu=null;
	
	private CountDownLatch connectedSemaphore;

	public AllZooKeeperWatcher(){
	}
	
	public void setZookeeperUtil(ZookeeperUtil zu,CountDownLatch connectedSemaphore){
		this.zu=zu;
		this.connectedSemaphore=zu.connectedSemaphore;
	}
	
	
	/**
	 * 收到来自Server的Watcher通知后的处理。
	 */
	public void process( WatchedEvent event ) {

		try {
			Thread.sleep( 200 );
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if ( event==null ) {
			return;
		}
		// 连接状态
		KeeperState keeperState = event.getState();
		// 事件类型
		EventType eventType = event.getType();
		// 受影响的path
		String path = event.getPath();
		
		String logPrefix = "【Watcher-" + this.seq.incrementAndGet() + "】";

		logger.info( logPrefix + "收到Watcher通知" );
		logger.info( logPrefix + "连接状态:\t" + keeperState.toString() );
		logger.info( logPrefix + "事件类型:\t" + eventType.toString() );
		logger.info( logPrefix + "事件path="+path);

		if ( KeeperState.SyncConnected == keeperState ) {
			// 成功连接上ZK服务器
			if ( EventType.None == eventType ) {
				logger.info( logPrefix + "成功连接上ZK服务器" );
				connectedSemaphore.countDown();
				logger.error("keeperState={},eventType={},计数器减一",keeperState.toString(),eventType.toString());
			} else if ( EventType.NodeCreated == eventType ) {
				logger.info( logPrefix + "节点创建" );
				zu.exists( path, true );
			} else if ( EventType.NodeDataChanged == eventType ) {
				logger.info( logPrefix + "节点数据更新" );
				logger.info( logPrefix + "数据内容: " + zu.readData( path ) );
			} else if ( EventType.NodeChildrenChanged == eventType ) {
				logger.info( logPrefix + "子节点变更" );
				logger.info( logPrefix + "子节点列表：" + zu.getChildren( path ) ); //保留对当前路径子节点的监控
			} else if ( EventType.NodeDeleted == eventType ) {
				logger.info( logPrefix + "节点 " + path + " 被删除" );
			}

		} else if ( KeeperState.Disconnected == keeperState ) {
			logger.info( logPrefix + "与ZK服务器断开连接" );
		} else if ( KeeperState.AuthFailed == keeperState ) {
			logger.info( logPrefix + "权限检查失败" );
		} else if ( KeeperState.Expired == keeperState ) {
			logger.info( logPrefix + "会话失效" );
		}

		logger.info( "--------------------------------------------" );

	}
	
}
