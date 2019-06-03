package com.edison.testJunit.oth.activeMQ.queue;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.*;

public class JMSConsumer {
    private static final String BROKERURL="failover:(tcp://127.0.0.1:61616)";
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
                TextMessage message= (TextMessage) consumer.receive(5000);
                if(message==null)//超过5s还没有消息，则认为消费完了
                    break;
                count++;
                System.out.println("收到消息："+message.getText());
                int msgId=message.getIntProperty("id");
                System.out.println("    id="+msgId);
                System.out.println("    name="+message.getStringProperty("name"));
                //message.acknowledge(); 这里是自动确认的

                try{//sleep 1-2秒
//                    Thread.sleep(new Random().nextInt(1000)+1000);
                }catch (Exception e){ }

                if(msgId==100){
                    try{//sleep 1-2秒
                        Thread.sleep(10000);
                        consumer.close();
                        session.close();
                        break;
                    }catch (Exception e){ }
                    ((ActiveMQConnection) connection).destroyDestination((ActiveMQDestination) destination);
                }
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
