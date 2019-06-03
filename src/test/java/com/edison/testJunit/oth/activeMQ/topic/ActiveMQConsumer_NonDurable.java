package com.edison.testJunit.oth.activeMQ.topic;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageConsumer;

import javax.jms.*;
/**非持久化的订阅者，在他离线期间被投递到topic的消息不会在他重新启动后推送给他*/
public class ActiveMQConsumer_NonDurable {
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
            ActiveMQMessageConsumer consumer = (ActiveMQMessageConsumer) session.createConsumer(destination);

            //设置监听器
            consumer.setMessageListener(new MyMessageListener());

        }catch(JMSException e){
            e.printStackTrace();
        }finally{
           /* if(connection!=null){
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }*/
        }
    }

    private static class MyMessageListener implements MessageListener {
        @Override
        public void onMessage(Message message) {
            TextMessage textMessage=(TextMessage)message;
            try {
                System.out.println("收到消息："+textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
    }
}
