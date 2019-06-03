package com.edison.testJunit.oth.activeMQ;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JMSProducer {
    private static final String BROKERURL="failover:(tcp://127.0.0.1:61616)";
    private static final String QUEUE_NAME = "TestQueue";

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

            //启动连接
            connection.start();

            //创建会话
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息发送到那个队列
            Destination destination = session.createQueue(QUEUE_NAME);

            // 创建消息发送者并设置持久化模式
            MessageProducer producer =session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化

            //开始发送消息： 创建消息对象，赋值，发送
            for( int i=1; i <= 100; i++) {
                String body="第【"+i+"】条消息";
                TextMessage msg = session.createTextMessage(body);
                msg.setIntProperty("id", i);
                msg.setStringProperty("name","消息"+i);
                producer.send(msg);

                //
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


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
