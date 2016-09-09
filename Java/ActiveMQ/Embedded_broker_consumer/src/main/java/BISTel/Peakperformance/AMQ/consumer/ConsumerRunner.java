package BISTel.Peakperformance.AMQ.consumer;

import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;

//import BISTel.Peakperformance.AMQ.NCRBrokerService;

public class ConsumerRunner {

	public void run(String[] args) throws Exception {
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
				"failover://(tcp://localhost:61616)");
		cf.setAlwaysSessionAsync(false);
		cf.setOptimizeAcknowledge(true);
		Connection connection = cf.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session
				.createTopic("test.topic?consumer.prefetchSize=32766");
		MessageConsumer consumer = session.createConsumer(topic);
		final AtomicInteger count = new AtomicInteger();
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				TextMessage textMessage = (TextMessage) message;
				try {
					System.out.println("ConsumerRunner textMessage" + textMessage.getText());
				
					if (count.incrementAndGet() % 10000 == 0)
						System.err.println("Got = " + textMessage.getText());
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});

	}
	
	public static void main(String[] args) throws Exception {

		ConsumerRunner _consumer = new ConsumerRunner();
		try{
			_consumer.run(args);
		}catch(Exception e){
			System.out.println(e.toString());
		}
    	System.out.println("ConsumerRunner start");    	
 
    }
}
