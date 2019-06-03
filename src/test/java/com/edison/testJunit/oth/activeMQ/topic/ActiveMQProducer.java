package com.edison.testJunit.oth.activeMQ.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;

import javax.jms.*;

public class ActiveMQProducer {
    private static final String BROKERURI="tcp://localhost:61616";
    public static void main(String[] args) {
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory(BROKERURI);
        ActiveMQConnection connection=null;
        try{
            connection=(ActiveMQConnection) connectionFactory.createConnection();
            connection.setUseAsyncSend(false);//或者new ActiveMQConnectionFactory("tcp://locahost:61616?jms.useAsyncSend=true");
//            connection.setDispatchAsync(true);

            //启动连接
            connection.start();

            //创建会话
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息发送到那个队列
            Destination destination = session.createTopic("TEST_TOPIC");

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
        }catch(JMSException e){
            e.printStackTrace();
        }
    }
}
