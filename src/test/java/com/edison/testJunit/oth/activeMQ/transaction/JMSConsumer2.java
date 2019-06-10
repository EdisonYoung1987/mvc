package com.edison.testJunit.oth.activeMQ.transaction;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**另一个消费者，看是否能够在第一个消费者未提交或者回滚前重复消费*/
public class JMSConsumer2 {
    private static final String BROKERURL="tcp://127.0.0.1:61616";
    private static final String QUEUE_NAME = "TestQueue?consumer.prefetchSize=5";
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

            //启动连接
            connection.start();

            //创建会话
            Session session=connection.createSession(false,Session.AUTO_ACKNOWLEDGE);

            // 消息的目的地，消息来源队列
            Destination destination = session.createQueue(QUEUE_NAME);

            // 创建消息消费者
            MessageConsumer consumer =session.createConsumer(destination);

            //进行消费
            while(true){
                TextMessage message= (TextMessage) consumer.receive();
                count++;
                System.out.println("收到消息："+message.getText());
                int msgId=message.getIntProperty("id");
                System.out.println("    id="+msgId);
                System.out.println("    name="+message.getStringProperty("name"));
                //message.acknowledge(); 这里是自动确认的
                System.out.println("消息是否为重传的："+message.getJMSRedelivered());

                if(msgId==100){
                    break;
                }
            }
            System.out.println("消息已消费完成");

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
