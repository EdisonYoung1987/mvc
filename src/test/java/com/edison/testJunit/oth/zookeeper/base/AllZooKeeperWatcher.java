package com.edison.testJunit.oth.zookeeper.base;

import java.nio.file.FileSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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
	private static HashMap<String,List<String>> addrMap=new HashMap<String,List<String>>(); //用于保存服务-地址列表
	AtomicInteger seq = new AtomicInteger();
	
	private ZookeeperUtil zu=null;
	
	public AllZooKeeperWatcher(ZookeeperUtil zu){
		this.zu=zu;
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
			} else if ( EventType.NodeCreated == eventType ) {
				logger.info( logPrefix + "节点创建" );
				zu.exists( path, true );
			} else if ( EventType.NodeDataChanged == eventType ) {
				logger.info( logPrefix + "节点数据更新" );
				logger.info( logPrefix + "数据内容: " + zu.readData( path,this) ); //重新注册watcher
				System.out.println("TODO 需要重新更新地址列表");
			} else if ( EventType.NodeChildrenChanged == eventType ) {
				logger.info( logPrefix + "子节点变更" );
				
				if("/SERVICES".equals(path)){//说明是新增/删除了类似 /SERVICES/ORDER 或者  /SERVICES/USER之类的服务
					//List<String> services=zu.getChildren(path, true);//这个不会重新注册watcher
					List<String> services=zu.getChildren(path, this);//重新注册watcher

					//检查是否是新增服务
					System.out.println("目前线上服务有：");
					for(String service:services){
						System.out.println("service=["+service+"]");
						String fullpath=path+"/"+service;
						if(!addrMap.containsKey(service)){//新增服务，关注新服务子节点地址列表的变化
							addrMap.put(service, new ArrayList<String>());
							zu.getChildren(fullpath, this);//设置对新的服务的地址子节点的关注
						}
					}
					//TODO 服务下线：暂不处理
				}else{//关注的服务地址子节点有变更
					int index=path.lastIndexOf("/");
					String service=path.substring(index+1);
					logger.info("服务{}的地址子节点有变更",service);
					List<String> addrList=new ArrayList<String>();
					List<String > childlist=zu.getChildren(path, this);//重新注册watcher
					for(String child : childlist){
						String addr=zu.readData(path+"/"+child,this); //关注节点内容改变
						if(addr!=null){
							addrList.add(addr);
							System.out.println("地址有："+addr);
						}
					}
					addrMap.put(service, addrList);
				}
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
