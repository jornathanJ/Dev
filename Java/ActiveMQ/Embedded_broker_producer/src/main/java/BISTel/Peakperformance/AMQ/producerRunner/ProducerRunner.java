package BISTel.Peakperformance.AMQ.producerRunner;

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



public class ProducerRunner {

	public void run(String[] args) throws Exception {
		
		BrokerService broker = new BrokerService();
		broker.setBrokerName("fast");
		broker.getSystemUsage().getMemoryUsage().setLimit(64*1024*1024);
		PolicyEntry policy = new PolicyEntry();
		policy.setMemoryLimit(4 * 1024 *1024);
		policy.setProducerFlowControl(false);
		PolicyMap pMap = new PolicyMap();
		pMap.setDefaultEntry(policy);
		broker.setDestinationPolicy(pMap);
		broker.addConnector("tcp://localhost:61616");
		
		//broker.
		
		broker.start();
		
		ActiveMQConnectionFactory cf = new ActiveMQConnectionFactory(
				"vm://fast");
		cf.setCopyMessageOnSend(false);
		Connection connection = cf.createConnection();
		connection.start();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("test.topic");
		final MessageProducer producer = session.createProducer(topic);
		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		for (int i = 0; i < 1000000; i++) {
			Thread.sleep(1000);
			TextMessage message = session.createTextMessage("Test:" + i);
			producer.send(message);
		}

		System.out.println("Finish send Text");  
	}
	
	public static void main(String[] args) throws Exception {

		ProducerRunner _ProducerRunner = new ProducerRunner();
		try{
			_ProducerRunner.run(args);
		}catch(Exception e){
			System.out.println(e.toString());
		}
    	System.out.println("ConsumerRunner start");    	
 
    }
}
