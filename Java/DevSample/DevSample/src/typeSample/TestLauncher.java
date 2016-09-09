package typeSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class TestLauncher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String strType = "HOUR";

		
		// functionClass.makeData(10, (long)1447902000319.0, strType);

		// Case 1)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 없는 시간
//		 long MinTime = (long) 1447902000460.0;
//		 long MaxTime = (long) 1447988400730.0;

		// Case 2)Hour Sample - Custom시간이 0개 + 중간에 끊어지는 게 있는 시간
		long MinTime = (long) 1447902000460.0;
		long MaxTime = (long) 1447988400730.0;
		long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
		//long[] userDefineTime = new long[] {  (long) 1447936200278.0 };
		//long[] userDefineTime = null;
		double d = MinTime;
		System.out.printf("%.2f", d);
		
		LogFunction functionClass = new LogFunction(MinTime, MaxTime, userDefineTime,  "DAY");

		Calendar cMax = Calendar.getInstance();
		Calendar cMin = Calendar.getInstance();
//		Calendar currentPosition = Calendar.getInstance();
//
//		MinTime = MinTime / 100000;
//		MinTime = MinTime * 100000;
//		MaxTime = MaxTime / 100000;
//		MaxTime = MaxTime * 100000;
//		
		cMin.setTimeInMillis(MinTime);
		cMax.setTimeInMillis(MaxTime);
//		currentPosition.setTimeInMillis(MinTime);

		HashMap<Integer, Calendar> mTimeSequence = new HashMap<Integer, Calendar>();

		if (userDefineTime == null || userDefineTime.length == 0) {
			mTimeSequence.put(0, functionClass.setTime(cMin, "START", "HOUR"));
			mTimeSequence.put(1, functionClass.setTime(cMax, "END", "HOUR"));
		} else {
			mTimeSequence.put(0, functionClass.setTime(cMin, "START", "HOUR"));
			
			for (int index = 0; index < userDefineTime.length; index++) {
				Calendar temp = Calendar.getInstance();
				
				long tempTime = userDefineTime[index] / 100000;
				tempTime = tempTime * 100000;
				
				temp.setTimeInMillis(tempTime);
				mTimeSequence.put(index + 1, functionClass.setTime(temp, "START", "HOUR"));
			}
			
			mTimeSequence.put(userDefineTime.length + 1, functionClass.setTime(cMax, "END", "HOUR"));
		}

		
		
		for(int index = 0; index < mTimeSequence.size()-1; index++ ) {

			long timeGap = 0;
			Calendar start = (Calendar) mTimeSequence.get(index).clone();
			Calendar end = (Calendar) mTimeSequence.get(index+1).clone();
			
			if (strType.equals("DAY") == true) {
				timeGap = functionClass.getTimeBetween(start, end, "DAY");
			} else {
				timeGap = functionClass.getTimeBetween(start, end, "HOUR");
			}
			
			functionClass.makeData((int) (timeGap), start.getTimeInMillis(), strType);
		} 
	}
	
	public static String printCalendar(Calendar values, boolean showInConsole){
		long value = values.getTimeInMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h, hh=12h

		String strCustom = df.format(value);
		if(showInConsole == true){
			System.out.printf("%d : %s %n", value,  strCustom);
		}
		
		return strCustom;
	}
	
	public static String printLongTime(Date values, boolean showInConsole){
		long value = values.getTime();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h, hh=12h

		String strCustom = df.format(value);
		if(showInConsole == true){
			System.out.printf("%d : %s %n", value,  strCustom);
		}
		
		return strCustom;
	}
}

//long timeGap = 0;
//if (strType.equals("DAY") == true) {
//	timeGap = (cMax.getTimeInMillis() - cMin.getTimeInMillis()) / (1000 * 60 * 60 * 24);
//} else {
//	// 분과 초 단위가 0을 넘어선 값이 하나라도 있다면 그 다음 시간을 할당한다.
//	int currentSeconds = cMin.get(Calendar.SECOND);
//	int currentMinute = cMin.get(Calendar.MINUTE);
//	if (currentSeconds > 0) {
//		cMin.add(Calendar.SECOND, -currentSeconds);
//	}
//	if (currentMinute > 0) {
//		cMin.add(Calendar.MINUTE, 60 - currentMinute);
//	}
//	timeGap = (cMax.getTimeInMillis() - cMin.getTimeInMillis()) / (1000 * 60 * 60);
//}
//
//functionClass.makeData((int) timeGap, cMin.getTimeInMillis(), strType);
