package com.bistel.a3.portal.spark.service;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.bistel.a3.portal.spark.common.SparkAppService;
import com.bistel.a3.portal.spark.common.SparkRequest;
import com.bistel.a3.portal.spark.common.SparkResponse;
import com.bistel.a3.portal.spark.common.SparkResponseCode;

public class SparkClient implements SparkAppService.Iface {

	public static String host;
	public static int port;

	private static SparkClient sparkAppExecutor = null;

	public static synchronized SparkClient getInstance() {
		if (sparkAppExecutor == null) {
			sparkAppExecutor = new SparkClient();
		}
		return sparkAppExecutor;
	}
	
	@Override
	public SparkResponse run(SparkRequest request) throws TException {
		SparkResponse result = new SparkResponse();
		TTransport trans = null;
		
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
}
