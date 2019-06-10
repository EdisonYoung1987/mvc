package com.edison.testJunit.oth.activeMQ.redelivery;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
/**创建一个queue，并定义其重传规则
 * 发现生产端设置重传规则对于消费端没有卵用。。。
 * 可能是因为重传策略是和connection绑定的吧。。。*/
public class JMSProducer {
    private static final String BROKERURL="failover:(tcp://127.0.0.1:61616)";
    private static final String QUEUE_NAME = "RedeliveryQueue";

    public static void main(String[] args)  {
        ActiveMQConnectionFactory connectionFactory=null;
        ActiveMQConnection connection=null;
        try {
            //连接工厂
            connectionFactory=new ActiveMQConnectionFactory(BROKERURL);
            ((ActiveMQConnectionFactory)connectionFactory).setDispatchAsync(true);

            //创建连接
            connection=(ActiveMQConnection)connectionFactory.createConnection();
            connection.setDispatchAsync(true);

            //启动连接
            connection.start();

            //创建会话
            ActiveMQSession session=(ActiveMQSession)connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息发送到那个队列
            ActiveMQDestination destination = (ActiveMQDestination) session.createQueue(QUEUE_NAME);

            // 创建消息发送者并设置持久化模式
            ActiveMQMessageProducer producer =(ActiveMQMessageProducer)session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);//非持久化

            //1. 异步非持久化模式：开始发送消息： 创建消息对象，赋值，发送
            for( int i=1; i <= 1; i++) {
                String body="第【"+i+"】条消息";
                TextMessage msg = session.createTextMessage(body);
                msg.setIntProperty("id", i);
                msg.setStringProperty("name","消息"+i);
                producer.send(msg);
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
