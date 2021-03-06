package com.edison.testJunit.oth.redisCluster;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * 一个jedis连接redis集群的客户端，包含批量匹配删除key操作*/
public class JedisClient {

	public static void main(String[] args) {
		Set<HostAndPort> nodes = new HashSet<HostAndPort>();

		nodes.add(new HostAndPort("192.168.2.130",6379));
		nodes.add(new HostAndPort("192.168.2.130",6380));
		nodes.add(new HostAndPort("192.168.2.130",6381));
		nodes.add(new HostAndPort("192.168.2.130",6382));
		nodes.add(new HostAndPort("192.168.2.130",6383));
		nodes.add(new HostAndPort("192.168.2.130",6384));
		nodes.add(new HostAndPort("192.168.2.130",6385));
		nodes.add(new HostAndPort("192.168.2.130",6386));
		
		//执行JedisCluster对象中的方法，方法和redis一一对应。	
		JedisCluster cluster = new JedisCluster(nodes);
		cluster.set("cluster-test", "my jedis cluster test");	
		String result = cluster.get("cluster-test");	
		System.out.println(result);	//程序结束时需要关闭JedisCluster对象	cluster.close();
		
		cluster.lpush("mylist:{info}:yangchuan","age:18","hight:173","wife:wuyao" );
		String str=null;
		while( (str=cluster.lpop("mylist:{info}:yangchuan"))!=null){
			System.out.println(str);
		}
		
		cluster.set("abbc", "x");
		cluster.set("abc", "y");
		cluster.set("abbcd", "x");
		//集群批量删除
		delRedisClusterByPattern(cluster, "*bc*",10 );
		
		try{
			cluster.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	/** 从RedisCluster批量删除指定pattern的数据
	 * @param scanCounter--每次扫描个数
	*/
	public static void delRedisClusterByPattern(JedisCluster jedisCluster,String pattern, int scanCounter) {
		// 获取所有节点的JedisPool
		Map<String, JedisPool> jedisPoolMap = jedisCluster.getClusterNodes();
		for (Entry<String, JedisPool> entry : jedisPoolMap.entrySet()) {
			// 获取每个节点的Jedis连接
			Jedis jedis = entry.getValue().getResource();
			
			// 只删除主节点数据
			if (!isMaster(jedis)) {
				continue;
			}
			// 使用Pipeline每次删除指定前缀的数据
			Pipeline pipeline = jedis.pipelined();
			
			// 使用scan扫描指定前缀的数据
			String cursor = "0";
			// 指定扫描参数： 每次扫描个数和pattern
			ScanParams params = new ScanParams().count(scanCounter).match(pattern);
			while (true) {
				// 执行扫描
				ScanResult<String> scanResult = jedis.scan(cursor, params);
				// 删除的key列表
				List<String> keyList = scanResult.getResult();
				if (keyList != null && keyList.size() > 0) {
					for (String key : keyList) {
						pipeline.del(key);
					}
					// 批量删除
					pipeline.syncAndReturnAll();
				}
				cursor = scanResult.getStringCursor();
				// 如果游标变为0， 说明扫描完毕  jedis.scan在扫描完毕时，会将cursor重置为0，否则为下次扫描的起始游标位置(偏移量)
				if ("0".equals(cursor)) {
					break;
				}
		
			}
		}
	}
	
	// 判断当前Redis是否为master节点
	private static boolean isMaster(Jedis jedis) {
		String[] data = jedis.info("Replication").split("\r\n");
		for (String line : data) {
			if ("role:master".equals(line.trim())) {
				return true;
			}
		}
		return false;
	}

	public static void delkeysByPattern(JedisCluster jedisCluster,String pattern, int scanCounter){
		Map<String, JedisPool> nodeMap=jedisCluster.getClusterNodes();
		for(String nodeKey:nodeMap.keySet()){
			JedisPool jedisPool=nodeMap.get(nodeKey);
			Jedis jedis=jedisPool.getResource();

			String infoData=jedis.info("replication");
			String[] datas=infoData.split("\r\n");
			boolean isMaster=false;
			for(String data:datas){
				if("role:master".matches(data.trim())){
					isMaster=true;
				}
			}
			if(!isMaster){
				continue;
			}

			String cursor="0";
			Pipeline pipeline=jedis.pipelined();
			while(true) {
				ScanResult<String> scanResult = jedis.scan(cursor, new ScanParams().match(pattern).count(scanCounter));
				cursor=scanResult.getStringCursor();
				List<String> keys=scanResult.getResult();
				for(String key:keys)
					pipeline.del(key);
				if(cursor.equalsIgnoreCase("0")){
					break;
				}
			}

			pipeline.syncAndReturnAll();
		}
	}
}
