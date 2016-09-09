package PDM_EffectSampleData;

import java.math.BigDecimal;
import java.util.HashMap;

public class TestLauncherFinal {

	public static void main(String[] args) {
		
		BigDecimal b = new BigDecimal(String.valueOf("1448377260000.1024111") );
		
		System.out.printf(b.toString());

		HashMap<Integer, double[]> rtnValue = new HashMap<Integer, double[]>();

		// Case 2)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 있는 시간
		long MinTime = (long) 1447902000460.0;
		long MaxTime = (long) 1447988400730.0;
		long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		
		//Custom Event가 아무것도 없는 경우는 아래와 같이 null을 사용합니다.
		//long[] userDefineTime = null;
		
		LogFunction functionClass = new LogFunction(MinTime, MaxTime, userDefineTime,  "HOUR");
		
		rtnValue = functionClass.getEffectData();
		
		// Case 2)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 있는 시간
//		long MinTime = (long) 1447902000247.0;
//		long MaxTime = (long) 1448247600149.0;
//		long[] userDefineTime = new long[] { (long) 1447988400386.0, (long) 1448161200459.0 };
		//long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] {  (long) 1447936200278.0 };
		//long[] userDefineTime = null;
		
		//LogFunction functionClass = new LogFunction(MinTime, MaxTime, userDefineTime,  "DAY");
		//rtnValue = functionClass.getEffectData();
		
		System.out.printf("Result is ============================ \r\n");
		
		for (int index = 0; index < rtnValue.size() - 1; index++) {
			System.out.printf("index :  %02d, [time(%s) : %.3f, Result : %.3f]\r\n"
					, index, functionClass.printLongTime((long) rtnValue.get(index)[0], false)
					, rtnValue.get(index)[0], rtnValue.get(index)[1]);
		}

	}
}
