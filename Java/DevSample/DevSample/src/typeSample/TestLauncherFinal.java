package typeSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TestLauncherFinal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		/*String strType = "HOUR";


		// Case 2)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 있는 시간
		long MinTime = (long) 1447902000460.0;
		long MaxTime = (long) 1447988400730.0;
		long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] {  (long) 1447936200278.0 };
		//long[] userDefineTime = null;
		
		LogFunction functionClass = new LogFunction(MinTime, MaxTime, userDefineTime,  "HOUR");
		
		functionClass.getEffectData();*/
		
		String strType = "DAY";


		// Case 2)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 있는 시간
		long MinTime = (long) 1447902000247.0;
		long MaxTime = (long) 1448247600149.0;
		long[] userDefineTime = new long[] { (long) 1447988400386.0, (long) 1448161200459.0 };
		//long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] {  (long) 1447936200278.0 };
		//long[] userDefineTime = null;
		
		LogFunction functionClass = new LogFunction(MinTime, MaxTime, userDefineTime,  strType);
		
		functionClass.getEffectData();

	}
}
