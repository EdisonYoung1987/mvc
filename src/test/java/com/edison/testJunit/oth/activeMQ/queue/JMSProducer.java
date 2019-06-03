package com.edison.testJunit.oth.activeMQ.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.concurrent.CountDownLatch;

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

            //2. 异步持久化模式：发送消息后需要进行回调，否则可能存在丢失的可能
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);//进行持久化
            final CountDownLatch messagesSent = new CountDownLatch(200);
            for( int i=1000;i<1200;i++){//发送200条
                String body="第【"+i+"】条消息";
                TextMessage msg = session.createTextMessage(body);
                msg.setIntProperty("id", i);
                producer.send(session.createTextMessage("Hello"), new AsyncCallback() {
                    @Override
                    public void onSuccess() {
                        messagesSent.countDown();
                    }

                    @Override
                    public void onException(JMSException exception) {
                        exception.printStackTrace();
                    }
                });
            }
            try {
                messagesSent.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
