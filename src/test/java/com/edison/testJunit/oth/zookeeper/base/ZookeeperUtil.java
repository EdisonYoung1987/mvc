package com.edison.testJunit.oth.zookeeper.base;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.KeeperException.NodeExistsException;
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
 * 主要是zk连接的初始化工作、创建zk初始数据等*/
public class ZookeeperUtil implements Watcher{
	private static final Logger LOG =  LoggerFactory.getLogger( AllZooKeeperWatcher.class );

	private  ZooKeeper  zk=null;
	private static Properties property;
	private static final int SESSIONTIMEOUT=30*2000;
	
	/** 信号量，阻塞程序执行，用于等待zookeeper连接成功，发送成功信号 */
    static final CountDownLatch connectedSemaphore = new CountDownLatch(1);
	
    static final int RETRY=3;//删除等操作的重试次数
    
	/**
	 * 初始化zookeeper连接，并创建初始目录*/
	public  void initZk() throws IOException, KeeperException, InterruptedException{
		//通过配置文件获取zk地址列表，实际spring项目中可以通过注解@Value("${zookeeper_addrs}")
		property=new Properties();
		property.load(ClassLoader.getSystemResourceAsStream("dataSource.properties"));
		//String addrs=getProperty("zookeeper_addrs");
		String addrs="127.0.0.1:2181";//暂时用不上集群，懒得开虚拟机，先测试本地
		LOG.error("地址:{}",addrs);
		
		//多个地址端口用,隔开192.168.111.130:2181,192.168.111.130:2182,192.168.111.130:2183
		LOG.error("zu开始建立连接");
		this.zk=new ZooKeeper(addrs, SESSIONTIMEOUT, this);//zk客户端连接是个异步过程
		LOG.error("zu开始进入连接等待");
		connectedSemaphore.await();//直到连接线程返回。
		LOG.error("zu的await返回");

		//创建初始公共目录
		String intPath=getProperty("intPath");//初始化的公共目录
		LOG.error("初始化目录：[{}]",intPath);
		try{
			zk.create(intPath, "-".getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
		}catch(NodeExistsException e){
			LOG.error("节点{}已存在，继续后续处理",intPath);
		}
		return;
	}
	
	public ZookeeperUtil() throws IOException, KeeperException, InterruptedException{
		initZk();
	}

	/**zu只处理建立连接时的watcher事件*/
	public void process(WatchedEvent event) {
		// 连接状态
		KeeperState keeperState = event.getState();
		// 事件类型
		EventType eventType = event.getType();
		if(keeperState==KeeperState.SyncConnected){
			if(eventType==EventType.None){
				connectedSemaphore.countDown();//相当于计数减一？
				LOG.error("keeperState={},eventType={},计数器减一",keeperState.toString(),eventType.toString());
			}else{
//				LOG.error("eventType={}",eventType.toString()); //每个node的变动，这个keeperState都会被探测，日志没必要
			}
		}
	}
	
	/**根据key获取参数值*/
	public String getProperty(String key){
		return property.getProperty(key);
	}
	
	/**获取zookeeper连接*/
	public ZooKeeper getZookeeper(){
		return this.zk;
	}
	
	/**删除指定节点,包括子节点
	 *  @param path 节点path*/
	public void deleteNode(String path){
		deleteNode( path,RETRY ) ;
	}
	/**
	 * 删除指定节点
	 * @param path 节点path
	 * @param reTry 重试次数
	 */
	public void deleteNode( String path,int reTry ) {
		LOG.info("删除节点path：{}" , path );
		if(reTry==0){//已达到重试次数
			return ;
		}
		
		try {
			this.zk.delete( path, -1 );
			
			LOG.info("删除节点成功，path：{}" , path );
		} catch ( KeeperException ke ) {
			if(ke.code()==Code.NONODE ){//节点不存在
				return;
			}else if(ke.code()==Code.NOTEMPTY){//还有子节点
				try {
					List<String> childList=this.zk.getChildren(path, null);
					for(String child:childList){
						deleteNode(path+"/"+child,reTry);//子节点不是完整路径
						deleteNode(path,reTry-1);
					}
				} catch (KeeperException e) {//只有一种情况：当前节点不存在
					return;
				} catch (InterruptedException e) {
					deleteNode(path,reTry-1);//再次尝试删除
				}
			}
		} catch(InterruptedException ie){
			deleteNode(path,reTry-1);
		}
	}
	

	/**
	 * 读取指定节点数据内容
	 * @param path 节点path
	 * @return null表示不存在，否则返回节点内容
	 */
	public String readData( String path ) {
		return readData(path,true);//默认保留节点上的watcher
	}
	
	/**
	 * 读取指定节点数据内容
	 * @param path 节点path
	 * @param needWatch true-保留节点上面的watcher
	 * @return null表示不存在，否则返回节点内容
	 */
	public String readData( String path, boolean needWatch ) {
		try {
			return new String( this.zk.getData( path, needWatch, null ) );
		} catch ( Exception e ) {
			return null;
		}
	}
	
	/**
	 * 获取子节点
	 * @param path 节点path
	 */
	public List<String> getChildren( String path ) {
		return getChildren(path,true);
	}
	/**
	 * 获取子节点
	 * @param path 节点path
	 * @param needWatch true-保留节点上面的watcher
	 */
	public List<String> getChildren( String path, boolean needWatch ) {
		try {
			return this.zk.getChildren( path, needWatch );
		} catch ( Exception e ) {
			return null;
		}
	}
	
	/**
	 * 更新指定节点数据内容
	 * @param path 节点path
	 * @param data  数据内容
	 * @return
	 */
	public Stat writeData( String path, String data ) {
		try {
			return zk.setData( path, data.getBytes(), -1);
		} catch ( Exception e ) {
			return null;
		}
	}
	
}
