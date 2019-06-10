package com.edison.testJunit.oth.activeMQ.redelivery;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.broker.region.policy.RedeliveryPolicyMap;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;
/**这里验证消息在事务中被消费，然后再回滚是否消息又回到broker中*/
public class JMSConsumer {
    private static final String BROKERURL="failover:(tcp://127.0.0.1:61616)";
    private static final String QUEUE_NAME = "RedeliveryQueue?consumer.prefetchSize=5";
    private static int count=0;

    public static void main(String[] args)  {
        ConnectionFactory connectionFactory=null;
        Connection connection=null;
        try {
            //连接工厂
            connectionFactory=new ActiveMQConnectionFactory(BROKERURL);
            ((ActiveMQConnectionFactory)connectionFactory).setDispatchAsync(true);

            //创建连接
            connection=connectionFactory.createConnection();
            ((ActiveMQConnection)connection).setDispatchAsync(true);

            //定义消息重传规则--生产端无效
            RedeliveryPolicy queuePolicy =((ActiveMQConnection) connection).getRedeliveryPolicy();
            queuePolicy.setInitialRedeliveryDelay(4500);//第一次重传间隔3秒
            queuePolicy.setRedeliveryDelay(1500);//后续重传基础间隔：1.5秒
            queuePolicy.setUseExponentialBackOff(true);//开启间隔时间倍数增长(第三次及以后)
            queuePolicy.setBackOffMultiplier(1.5);//增长倍数
            queuePolicy.setMaximumRedeliveryDelay(10); //最大重传间隔时间

            //冲突避免： 间隔时间=间隔时间 ± 间隔时间*表示collisionAvoidanceFactor*随机double(<1)
            queuePolicy.setUseCollisionAvoidance(true); //开启重传冲突避免
            queuePolicy.setCollisionAvoidancePercent((short) 15);//表示collisionAvoidanceFactor=15*0.01

            //重传次数限制
            queuePolicy.setMaximumRedeliveries(7);
//            RedeliveryPolicyMap map = ((ActiveMQConnection)connection).getRedeliveryPolicyMap();
            ((ActiveMQConnection) connection).setRedeliveryPolicy(queuePolicy);

            //启动连接
            connection.start();

            //创建会话
            Session session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息来源队列
            Destination destination = session.createQueue(QUEUE_NAME);
//          这样可以设置重传策略到指定队列
            RedeliveryPolicyMap map = ((ActiveMQConnection)connection).getRedeliveryPolicyMap();
            map.put((ActiveMQDestination) destination,queuePolicy);
            ((ActiveMQConnection) connection).setRedeliveryPolicyMap(map);

            // 创建消息消费者
            MessageConsumer consumer =session.createConsumer(destination);

            //进行消费
            long start=System.currentTimeMillis();
            while(true){
                TextMessage message= (TextMessage) consumer.receive();
                long next=System.currentTimeMillis();
                System.out.println("接收消息时间："+next+" 距离上次间隔为："+(next-start)/(double)1000);
                start=next;
                count++;
                int msgId=message.getIntProperty("id");
                System.out.println("    id="+msgId);
                System.out.println("消息是否为重传的："+message.getJMSRedelivered());
                session.rollback();//回滚，下一步重新接受该消息
            }


        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            System.out.println("总共消费："+count);

            if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
