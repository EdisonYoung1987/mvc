package com.edison.testJunit.oth.activeMQ.transaction;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;
/**发送端，开启事务，在send消息之后，sleep一定时间，查看console队列中是否有消息，再提交事务，看消息是否存在*/
public class JMSProducer {
    private static final String BROKERURL="failover:(tcp://127.0.0.1:61616)";
    private static final String QUEUE_NAME = "TestQueue";

    public static void main(String[] args)  {
        ActiveMQConnectionFactory connectionFactory=null;
        ActiveMQConnection connection=null;
        try {
            //连接工厂
            connectionFactory=new ActiveMQConnectionFactory(BROKERURL);
            connectionFactory.setDispatchAsync(true);

            //创建连接
            connection=(ActiveMQConnection)connectionFactory.createConnection();
            connection.setDispatchAsync(true);

            //启动连接
            connection.start();

            //创建会话
            Session session=connection.createSession(true,Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息发送到那个队列
            Destination destination = session.createQueue(QUEUE_NAME);

            // 创建消息发送者并设置持久化模式
            ActiveMQMessageProducer producer =(ActiveMQMessageProducer)session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化

            //1. 异步非持久化模式：开始发送消息： 创建消息对象，赋值，发送
            for( int i=1; i <= 100; i++) {
                String body="第【"+i+"】条消息";
                TextMessage msg = session.createTextMessage(body);
                msg.setIntProperty("id", i);
                msg.setStringProperty("name","消息"+i);
                producer.send(msg);
            }
            System.out.println("100条消息已经发送完毕");

            try {//sleep 10秒，用于观察队列中是否已经有了数据
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            session.commit();//推测应该在这里才会将消息发送到broker

        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
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
