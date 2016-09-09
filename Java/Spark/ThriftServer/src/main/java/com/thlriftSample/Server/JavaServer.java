package com.thlriftSample.Server;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TNonblockingServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;

import com.thriftSample.shared.SharedStruct;
import com.thriftSample.tutorial.Calculator;
import com.thriftSample.tutorial.InvalidOperation;
import com.thriftSample.tutorial.Operation;
import com.thriftSample.tutorial.Work;

import org.apache.thrift.TEnum;





import java.util.HashMap;

public class JavaServer {
	public static class CalculatorHandler implements Calculator.Iface {

		private HashMap<Integer, SharedStruct> log;

		public CalculatorHandler() {
			log = new HashMap<Integer, SharedStruct>();
		}

		public void ping() {
			System.out.println("ping()");
		}

		public int add(int n1, int n2) {
			System.out.println("add(" + n1 + "," + n2 + ")");
			return n1 + n2;
		}

		public int calculate(int logid, Work work) throws InvalidOperation {
			System.out.println("calculate(" + logid + ", {" + work.op + ","
					+ work.num1 + "," + work.num2 + "})");
			int val = 0;
			switch (work.op.getValue()) {
			case 1:
				val = work.num1 + work.num2;
				break;
			case 2:
				val = work.num1 - work.num2;
				break;
			case 3:
				val = work.num1 * work.num2;
				break;
			case 4:
				if (work.num2 == 0) {
					InvalidOperation io = new InvalidOperation();
					io.whatOp = work.op.getValue();
					io.why = "Cannot divide by 0";
					throw io;
				}
				val = work.num1 / work.num2;
				break;
			default:
				InvalidOperation io = new InvalidOperation();
				io.whatOp = work.op.getValue();
				io.why = "Unknown operation";
				throw io;
			}

			SharedStruct entry = new SharedStruct();
			entry.key = logid;
			entry.value = Integer.toString(val);
			log.put(logid, entry);

			return val;
		}

		public SharedStruct getStruct(int key) {
			System.out.println("getStruct(" + key + ")");
			return log.get(key);
		}

		public void zip() {
			System.out.println("zip()");
		}

	}

	public static CalculatorHandler handler;

	public static Calculator.Processor processor;

	public static void main(String[] args) {
		try {
			handler = new CalculatorHandler();
			processor = new Calculator.Processor(handler);

			Runnable simple = new Runnable() {
				public void run() {
					simple(processor);
				}
			};
			Runnable secure = new Runnable() {
				public void run() {
					secure(processor);
				}
			};

			new Thread(simple).start();
			new Thread(secure).start();
		} catch (Exception x) {
			x.printStackTrace();
		}
	}

	public static void simple(Calculator.Processor processor) {
		try {
			TServerTransport serverTransport = new TServerSocket(9090);
			TServer server = new TSimpleServer(
					new Args(serverTransport).processor(processor));

			// Use this for a multithreaded server
			// TServer server = new TThreadPoolServer(new
			// TThreadPoolServer.Args(serverTransport).processor(processor));

			System.out.println("Starting the simple server...");
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void secure(Calculator.Processor processor) {
		try {
//			/*
//			 * Use TSSLTransportParameters to setup the required SSL parameters.
//			 * In this example we are setting the keystore and the keystore
//			 * password. Other things like algorithms, cipher suites, client
//			 * auth etc can be set.
//			 */
//			TSSLTransportParameters params = new TSSLTransportParameters();
//			// The Keystore contains the private key
//			params.setKeyStore("../../lib/java/test/.keystore", "thrift", null,
//					null);
//
//			/*
//			 * Use any of the TSSLTransportFactory to get a server transport
//			 * with the appropriate SSL configuration. You can use the default
//			 * settings if properties are set in the command line. Ex:
//			 * -Djavax.net.ssl.keyStore=.keystore and
//			 * -Djavax.net.ssl.keyStorePassword=thrift
//			 * 
//			 * Note: You need not explicitly call open(). The underlying server
//			 * socket is bound on return from the factory class.
//			 */
//			TServerTransport serverTransport = TSSLTransportFactory
//					.getServerSocket(9091, 0, null, params);
//			TServer server = new TSimpleServer(
//					new Args(serverTransport).processor(processor));
//
//			// Use this for a multi threaded server
//			// TServer server = new TThreadPoolServer(new
//			// TThreadPoolServer.Args(serverTransport).processor(processor));
//
//			System.out.println("Starting the secure server...");
//			server.serve();
			
			
			TNonblockingServerTransport socket= new org.apache.thrift.transport.TNonblockingServerSocket(7911);
			TServer server = new TNonblockingServer(new TNonblockingServer.Args(socket).processor(processor)
							.protocolFactory(new TBinaryProtocol.Factory()));
			
			//ServerLogger.getInstance().getLogger().info("Server Start... " + port);
			System.out.println("Server Start...");
			
			server.serve();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}