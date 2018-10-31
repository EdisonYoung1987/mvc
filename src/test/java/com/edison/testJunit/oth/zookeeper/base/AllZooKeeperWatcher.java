package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *  《ZooKeeper 事件类型详解》
 * @author nileader/nileader@gmail.com
 * 
 */
public class AllZooKeeperWatcher implements Watcher{

	private static final Logger LOG = LoggerFactory.getLogger( AllZooKeeperWatcher.class );
	AtomicInteger seq = new AtomicInteger();
	private static final int SESSION_TIMEOUT = 10000;
	private static final String CONNECTION_STRING = "192.168.111.130:2181,"
										+ "192.168.111.130:2182,192.168.111.130:2183";
	private static final String ZK_PATH = "/nileader";
	private static final String CHILDREN_PATH 	= "/nileader/ch";
	private static final String LOG_PREFIX_OF_MAIN = "【Main】";
	
	private ZooKeeper zk = null;
	private ZookeeperUtil zu=null;
	
	private CountDownLatch connectedSemaphore = new CountDownLatch( 1 );

	public AllZooKeeperWatcher(ZookeeperUtil zu){
		this.zu=zu;
	}
	
	
	/**
	 * 关闭ZK连接
	 */
	public void releaseConnection() {
		if ( this.zk!=null ) {
			try {
				this.zk.close();
			} catch ( InterruptedException e ) {}
		}
	}

	/**
	 *  创建节点
	 * @param path 节点path
	 * @param data 初始数据内容
	 * @param createMode :CreateMode.PERSISTENT/PERSISTENT_SEQUENTIAL/EPHEMERAL/EPHEMERAL_SEQUENTIAL
	 * @return
	 */
	public boolean createPath( String path, String data,CreateMode createMode ) {
		try {
			this.zk.exists( path, true );
			LOG.info( LOG_PREFIX_OF_MAIN + "节点创建成功, Path: "
					+ this.zk.create( path,  data.getBytes(), //
							                  Ids.OPEN_ACL_UNSAFE, //
							                  createMode )
					+ ", content: " + data );
		} catch ( Exception e ) {}
		return true;
	}

	/**
	 * 删除指定节点
	 * @param path 节点path
	 */
	public Stat exists( String path, boolean needWatch ) {
		try {
			return this.zk.exists( path, needWatch );
		} catch ( Exception e ) {return null;}
	}
	
	
	/**
	 * 收到来自Server的Watcher通知后的处理。
	 */
	public void process( WatchedEvent event ) {

		try {
			Thread.currentThread().sleep( 200 );
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

		LOG.info( logPrefix + "收到Watcher通知" );
		LOG.info( logPrefix + "连接状态:\t" + keeperState.toString() );
		LOG.info( logPrefix + "事件类型:\t" + eventType.toString() );

		if ( KeeperState.SyncConnected == keeperState ) {
			// 成功连接上ZK服务器
			if ( EventType.None == eventType ) {
				LOG.info( logPrefix + "成功连接上ZK服务器" );
				connectedSemaphore.countDown();
			} else if ( EventType.NodeCreated == eventType ) {
				LOG.info( logPrefix + "节点创建" );
				this.exists( path, true );
			} else if ( EventType.NodeDataChanged == eventType ) {
				LOG.info( logPrefix + "节点数据更新" );
				LOG.info( logPrefix + "数据内容: " + zu.readData( ZK_PATH ) );
			} else if ( EventType.NodeChildrenChanged == eventType ) {
				LOG.info( logPrefix + "子节点变更" );
				LOG.info( logPrefix + "子节点列表：" + zu.getChildren( ZK_PATH ) );
			} else if ( EventType.NodeDeleted == eventType ) {
				LOG.info( logPrefix + "节点 " + path + " 被删除" );
			}

		} else if ( KeeperState.Disconnected == keeperState ) {
			LOG.info( logPrefix + "与ZK服务器断开连接" );
		} else if ( KeeperState.AuthFailed == keeperState ) {
			LOG.info( logPrefix + "权限检查失败" );
		} else if ( KeeperState.Expired == keeperState ) {
			LOG.info( logPrefix + "会话失效" );
		}

		LOG.info( "--------------------------------------------" );

	}
	
	public static void main( String[] args ) throws InterruptedException, IOException, KeeperException {
//		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		ZookeeperUtil zu=new ZookeeperUtil();

		AllZooKeeperWatcher sample = new AllZooKeeperWatcher(zu);
//		sample.createConnection( CONNECTION_STRING, SESSION_TIMEOUT );
		//使用默认的连接以及watcher
		sample.zk=zu.getZookeeper();
		
		//清理节点
		zu.deleteNode( ZK_PATH );
		if ( sample.createPath( ZK_PATH, System.currentTimeMillis()+"",CreateMode.PERSISTENT ) ) {
			Thread.currentThread().sleep( 3000 );
			//读取数据
			LOG.error("ZK_PATH:",zu.readData( ZK_PATH ));
			//读取子节点
			LOG.error("zu.getChildren( ZK_PATH )={}",zu.getChildren( ZK_PATH ));
			
			//更新数据
			LOG.error("更新数据：{}",ZK_PATH);
			LOG.error("{}",zu.writeData( ZK_PATH, System.currentTimeMillis()+"" ));

			//创建子节点
			sample.createPath( CHILDREN_PATH, System.currentTimeMillis()+"",CreateMode.PERSISTENT );
		}
		Thread.currentThread().sleep( 3000 );
		//清理节点
		zu.deleteNode( ZK_PATH );
		Thread.currentThread().sleep( 3000 );
		sample.releaseConnection();
	}


}
