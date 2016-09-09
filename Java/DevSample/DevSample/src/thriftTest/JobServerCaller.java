package thriftTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.codehaus.jackson.map.ObjectMapper;

import com.bistel.a3.portal.thrift.client.iface.SparkPdMAppService;
import com.bistel.a3.portal.thrift.client.iface.SparkPdMRequest;
import com.bistel.a3.portal.thrift.client.iface.SparkPdMResponse;

public class JobServerCaller {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String host = "a3-spark-master";
		int port = 7914;

		Object in = null;
		try {
			RankRequest _rankRequest = new RankRequest();

			_rankRequest.setFocus(0.5);
			_rankRequest.setToolName("EQP01");
			_rankRequest.setModuleName("CHA");

			List<SummaryParameters> _SummaryParametersList = new ArrayList<SummaryParameters>();
			_SummaryParametersList.add(new SummaryParameters("PRESSURE01", "HOUR", "MIN"));
			_SummaryParametersList.add(new SummaryParameters("PRESSURE01", "HOUR", "MAX"));
			_SummaryParametersList.add(new SummaryParameters("PRESSURE01", "HOUR", "STD"));
			_SummaryParametersList.add(new SummaryParameters("PRESSURE01", "HOUR", "MEAN"));
			
			_SummaryParametersList.add(new SummaryParameters("TEMP01", "HOUR", "MEAN"));
			_SummaryParametersList.add(new SummaryParameters("TEMP02", "HOUR", "MEAN"));
			_SummaryParametersList.add(new SummaryParameters("TEMP03", "HOUR", "MEAN"));
			_SummaryParametersList.add(new SummaryParameters("TEMP04", "HOUR", "MEAN"));
			_rankRequest.setSummaryParameters(_SummaryParametersList);

			// 2015-11-25 00:00:00.000590 ==> 1448377200590
			// 2015-11-25 18:00:00.000501 ==> 1448442000501

			TimePeriod timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1448377200590.0);
			timePeriod.setTo((long) 1448442000501.0);

			_rankRequest.setTimePeriod(timePeriod);
			
			//RankRequest rr = (RankRequest) in;
			ObjectMapper mapper = new ObjectMapper();
			String jsonStr = mapper.writeValueAsString(_rankRequest);

			 TTransport transport;
			 transport = new TFramedTransport(new TSocket(host, port));//new TSocket(host, port);
			 transport.open();
//			TTransport transport;
//			transport = new TSocket("localhost", 7911);
//			transport.open();

			TProtocol protocol = new TBinaryProtocol(transport);
			SparkPdMAppService.Client client = new SparkPdMAppService.Client(
					protocol);

			SparkPdMRequest req = new SparkPdMRequest();
			req.host = host;
			req.port = port;
			req.funcName = "RANKING";
			req.requestContent = jsonStr;
			SparkPdMResponse r = client.run(req);

			transport.close();

			RankInfoResponse strResult = null;

			if (r.resultCode.equals("SUCCESS"))
				strResult = mapper.readValue(r.responseContent,
						RankInfoResponse.class);
			// else
			// return null;

		} catch (TException x) {
			x.printStackTrace();
			// return null;
		} catch (IOException e) {
			e.printStackTrace();
			// return null;
		}
	}

}
