package com.thlriftSample.Client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.bistel.a3.portal.spark.common.SparkAppCode;
import com.bistel.a3.portal.spark.common.SparkAppService;
import com.bistel.a3.portal.spark.common.SparkRequest;
import com.bistel.a3.portal.spark.common.SparkResponse;
import com.bistel.a3.portal.spark.common.SparkResponseCode;
import com.bistel.a3.portal.spark.service.SparkClient;
import com.thriftSample.shared.SharedStruct;
import com.thriftSample.tutorial.Calculator;
import com.thriftSample.tutorial.InvalidOperation;
import com.thriftSample.tutorial.Operation;
import com.thriftSample.tutorial.Work;



public class JavaClient {
	  

	public static void main(String [] args) {

//	    if (args.length != 1) {
//	      System.out.println("Please enter 'simple' or 'secure'");
//	      System.exit(0);
//	    }

	    try {
	      TTransport transport;
	      transport = new TSocket("localhost", 7911);
	        transport.open();
	        
//	      if (args[0].contains("simple")) {
//	        transport = new TSocket("localhost", 9090);
//	        transport.open();
//	      }
//	      else {
//	        /*
//	         * Similar to the server, you can use the parameters to setup client parameters or
//	         * use the default settings. On the client side, you will need a TrustStore which
//	         * contains the trusted certificate along with the public key. 
//	         * For this example it's a self-signed cert. 
//	         */
//	        TSSLTransportParameters params = new TSSLTransportParameters();
//	        params.setTrustStore("../../lib/java/test/.truststore", "thrift", "SunX509", "JKS");
//	        /*
//	         * Get a client transport instead of a server transport. The connection is opened on
//	         * invocation of the factory method, no need to specifically call open()
//	         */
//	        transport = TSSLTransportFactory.getClientSocket("localhost", 9091, 0, params);
//	      }

	      TProtocol protocol = new  TBinaryProtocol(transport);
	      Calculator.Client client = new Calculator.Client(protocol);

	      perform(client);
	      
	      String strJSONData =                                                                
	    		   "{                                                           "
	    		  + "    \"moduleId\" : \"BISTel/EIC/PDO/A3_AREA:A3EQP01/ChA\",  "
	    		  + "    \"recipeId\" : \"RCP1\",                                "
	    		  + "    \"from\" : 1433894400000,                               "
	    		  + "    \"to\" : 1433959200000,                                 "
	    		  + "    \"sumTypes\" : [\"AVG\", \"MIN\", \"MAX\"],             "
	    		  + "    \"sumInfos\" :                                          "
	    		  + "    [                                                       "
	    		  + "        {                                                   "
	    		  + "            \"paramAlias\" : \"COOL\",                      "
	    		  + "            \"windows\" :                                   "
	    		  + "            [                                               "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"1\"                      "
	    		  + "                },                                          "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"2\"                      "
	    		  + "                },                                          "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"3\"                      "
	    		  + "                }                                           "
	    		  + "            ]                                               "
	    		  + "        },                                                  "
	    		  + "        {                                                   "
	    		  + "            \"paramAlias\" : \"PARAM001\",                  "
	    		  + "            \"windows\" :                                   "
	    		  + "            [                                               "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"3\"                      "
	    		  + "                },                                          "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"4\"                      "
	    		  + "                },                                          "
	    		  + "                {                                           "
	    		  + "                    \"stepId\" : \"5\"                      "
	    		  + "                }                                           "
	    		  + "            ]                                               "
	    		  + "        }                                                   "
	    		  + "    ]                                                       "
	    		  + "}";
	      
//	      for(int idx = 0; idx < args.length; idx++){
//	    	  strJSONData = strJSONData.concat(args[idx]).concat("\r\n");
//	      }
	      
	      runClient("115", strJSONData );

	      transport.close();
	    } catch (TException x) {
	      x.printStackTrace();
	    } 
	  }

	private static String host = "localhost";
	private static String port = "7911";
	
	private String activityId = "7911";
	private String requestContent = "";
	  
	  
	  public static void  runClient(String activityId, String requestContent) {
			try {
				
				SparkClient.host = host;
				SparkClient.port = Integer.parseInt(port);
				
				SparkClient client = SparkClient.getInstance();

				SparkRequest request = new SparkRequest();
				request = new SparkRequest();
				request.setCode(SparkAppCode.ADHOC_SUMMARY);
				request.setActivityId(activityId);
				request.setRequestContent(requestContent);

				SparkResponse result = client.run(request);
				if (result.getCode() == SparkResponseCode.SUCCESS) {
					//return Response.ok().entity(Response.Status.OK).build();
				} else {
					//return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
				}
			} catch (TException e) {
				//return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
			}
		}
	  
	  
	  
	  public static SparkResponse run(SparkRequest request) throws TException {
			SparkResponse result = new SparkResponse();
			TTransport trans = null;
			
			String host = "localhost";
			int port = 7911;
			
			try {
				request.setHost(host);
				request.setPort(port);
				
				trans = new TFramedTransport(new TSocket(host, port));
				TProtocol prot = new TBinaryProtocol(trans);
				
				SparkAppService.Client client = new SparkAppService.Client(prot);
				trans.open();
				
				result = client.run(request);
				
			} catch (Exception e) {
				result = new SparkResponse();
				result.setCode(SparkResponseCode.FAIL);
				result.setResponseContent(e.getMessage());
			} finally {
				if (trans != null && trans.isOpen())
					trans.close();
			}
			return result;
		}

	  private static void perform(Calculator.Client client) throws TException
	  {
	    client.ping();
	    System.out.println("ping()");

	    int sum = client.add(1,1);
	    System.out.println("1+1=" + sum);

	    Work work = new Work();

	    work.op = Operation.DIVIDE;
	    work.num1 = 1;
	    work.num2 = 0;
	    try {
	      int quotient = client.calculate(1, work);
	      System.out.println("Whoa we can divide by 0");
	    } catch (InvalidOperation io) {
	      System.out.println("Invalid operation: " + io.why);
	    }

	    work.op = Operation.SUBTRACT;
	    work.num1 = 15;
	    work.num2 = 10;
	    try {
	      int diff = client.calculate(1, work);
	      System.out.println("15-10=" + diff);
	    } catch (InvalidOperation io) {
	      System.out.println("Invalid operation: " + io.why);
	    }

	    SharedStruct log = client.getStruct(1);
	    System.out.println("Check log: " + log.value);
	  }
	}
