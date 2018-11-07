package com.edison.testJunit.oth.zookeeper.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test {
	private static final Logger logger = LoggerFactory.getLogger( Test.class );
	
	public static void main( String[] args )  {
		ZookeeperUtil zu=null;

		try{
			zu=new ZookeeperUtil();
			AllZooKeeperWatcher allZooKeeperWatcher=new AllZooKeeperWatcher(zu);
	
			//先假设zk中/SERVICES是zk初始化启动时就创建的目录
			// /SERVICES/ORDER/ 下面就是订单服务的地址
			// /SERVICES/USER/  下面就是用户服务的地址
			
			//1. 先把自己这个watcher注册到/SERVICES上,查看是否有服务的增减
			zu.getChildren("/SERVICES",allZooKeeperWatcher);
			
			while(true){
				System.out.println("进入等待");
				//此时使用zkCli.sh/zkCli.cmd创建或删除/SERVICES下面的服务节点或者服务节点下面的地址节点可以收到通知
				/**create /SERVICES/PAY "ADDR
				 * create /SERVICES/PAY/ADDR1 "123"
					create /SERVICES/PAY/ADDR2 "1234"
					create /SERVICES/PAY/ADDR3 "12344"					
					delete /SERVICES/PAY/ADDR3
					create /SERCICES/ORDER "ADDR"
					create /SERVICES/ORDER "ADDR"
					create /SERVICES/ORDER/addr1 "1111"
					create /SERVICES/ORDER/addr2 "1222"
					set /SERVICES/ORDER/addr2 "3333""
				 * */
				Thread.sleep(20000);
			}
		}catch(Exception e){
			if(zu!=null){
				System.out.println("关闭连接");
				zu.releaseConnection();
			}
		}
	}
}
