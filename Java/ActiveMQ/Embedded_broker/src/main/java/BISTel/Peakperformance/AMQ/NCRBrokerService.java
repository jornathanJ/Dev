package BISTel.Peakperformance.AMQ;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.region.policy.PolicyEntry;
import org.apache.activemq.broker.region.policy.PolicyMap;

public class NCRBrokerService {

	public void run(String[] args) throws Exception{

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

    }

    public static void main(String[] args) throws Exception {

    	NCRBrokerService _broker = new NCRBrokerService();
    	//_broker.run(args);
    	
    	try{
			_broker.run(args);
		}catch(Exception e){
			System.out.println(e.toString());
		}
    	
    	System.out.println("NCRBrokerService start");
 
    }   
}
