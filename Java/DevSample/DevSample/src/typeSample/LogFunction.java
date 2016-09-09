package typeSample;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class LogFunction {

	private long _MinTime = (long) 1447902000460.0;
	private long _MaxTime = (long) 1447988400730.0;
	private long[] _UserDefineTime = new long[] { (long) 1447925400427.0, (long) 1447936200278.0 };
	private String _StrTimeType = "DAY";
	
	HashMap<Integer, double[]> rtnValue = new HashMap<Integer, double[]>(); 

	public HashMap<Integer, Calendar> mTimeSequence = new HashMap<Integer, Calendar>();

	public LogFunction(long minTime, long maxTime, long[] userDefineTime, String strTimeType) {
		_MinTime = minTime;
		_MaxTime = maxTime;
		_UserDefineTime = userDefineTime;
		_StrTimeType = strTimeType;
	}

	public void getEffectData() {
		Calendar cMax = Calendar.getInstance();
		Calendar cMin = Calendar.getInstance();

		cMin.setTimeInMillis(_MinTime);
		cMax.setTimeInMillis(_MaxTime);

		if (_UserDefineTime == null || _UserDefineTime.length == 0) {
			mTimeSequence.put(0, this.setTime(cMin, "START", _StrTimeType));
			mTimeSequence.put(1, this.setTime(cMax, "END", _StrTimeType));
		} else {
			mTimeSequence.put(0, this.setTime(cMin, "START", _StrTimeType));

			for (int index = 0; index < _UserDefineTime.length; index++) {
				Calendar temp = Calendar.getInstance();

				long tempTime = _UserDefineTime[index] / 100000;
				tempTime = tempTime * 100000;

				temp.setTimeInMillis(tempTime);
				mTimeSequence.put(index + 1, this.setTime(temp, "START", _StrTimeType));
			}

			mTimeSequence.put(_UserDefineTime.length + 1, this.setTime(cMax, "END", _StrTimeType));
		}

		System.out.print("Start Print \r\n");

		for (int index = 0; index < mTimeSequence.size(); index++) {
			printCalendar(mTimeSequence.get(index), true);
		}

		System.out.print("Start Print End\r\n");

		for (int index = 0; index < mTimeSequence.size() - 1; index++) {

			long timeGap = 0;
			Calendar start = (Calendar) mTimeSequence.get(index).clone();
			Calendar end = (Calendar) mTimeSequence.get(index + 1).clone();

			if (_StrTimeType.equals("DAY") == true) {
				timeGap = this.getTimeBetween(start, end, "DAY");
			} else {
				timeGap = this.getTimeBetween(start, end, "HOUR");
			}

			rtnValue = this.makeData((int) (timeGap), start.getTimeInMillis(), _StrTimeType);
		}
	}

	public long getTimeBetween(Calendar start, Calendar end, String strType) {

		long rtnValue = 0;

		if (strType.equals("HOUR") == true) {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		} else {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		}

		System.out.format("getTimeBetween strType[%s] :  [%s] --> [%s] Value is  : %d %n", strType,
				printCalendar(start, false), printCalendar(end, false), rtnValue);

		return rtnValue;
	}

	public Calendar setTime(Calendar value, String strType, String strTimePeriod) {

		long customTime = value.getTimeInMillis();
		customTime = customTime / 100000;
		customTime = customTime * 100000;

		Calendar timeData = Calendar.getInstance();
		timeData.setTimeInMillis(customTime);
		
		int currentSeconds = timeData.get(Calendar.SECOND);
		int currentMinute = timeData.get(Calendar.MINUTE);
		int currentHours = timeData.get(Calendar.HOUR_OF_DAY);

		if (strTimePeriod.equals("HOUR") == true) {

			if (currentSeconds > 0) {
				timeData.add(Calendar.SECOND, -currentSeconds);
			}

			if (strType.equals("START") == true) {
				if (currentMinute > 0) {
					timeData.add(Calendar.MINUTE, 60 - currentMinute);
				}
			} else {
				if (currentMinute > 0) {
					timeData.add(Calendar.MINUTE, 60 - currentMinute);
				} else if (currentMinute == 0) {
					timeData.add(Calendar.HOUR, 1);
				}
			}
		}
		else{
			timeData.add(Calendar.HOUR_OF_DAY,  -currentHours);
			timeData.add(Calendar.MINUTE, -currentMinute);
			timeData.add(Calendar.SECOND, -currentSeconds);
			
			if (strType.equals("END") == true) {
				timeData.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		
		System.out.format("Set Times strType[%s] :  [%s] --> [%s] %n", strType, printCalendar(value, false),
				printCalendar(timeData, false));
		
		return timeData;
	}

	public HashMap<Integer, double[]> makeData(int sampleCnt, long startTime, String strType) {

		HashMap<Integer, double[]> rtnValue = new HashMap<Integer, double[]>(); 
		
		long timeValue = startTime / 100000;
		timeValue = timeValue * 100000;
		printLongTime(timeValue, true);

		int temp = 0;
		double initialValue = 9.0 / sampleCnt;

		for (double index = 0; index < sampleCnt; index += 1) {

			long resultTime = 0;
			if (strType.equals("HOUR") == true) {
				resultTime = timeValue + (1000 * 60 * 60) * temp;
			} else {
				resultTime = timeValue + (1000 * 60 * 60 * 24) * temp;
			}

			// System.out.format("index is :%d, log10(%.2f) = %.3f [%d - %s] %n",
			// temp, index, 1 - Math.log10( 1 + index * initialValue),
			// resultTime, printLongTime(resultTime, false));
			double result =  1 - Math.log10(1 + (index * initialValue));

//			System.out.format("index is :%d, log10(%.2f) = %.3f [%d - %s] %n", temp, index,
//					 result, resultTime, printLongTime(resultTime, false));
			
			System.out.printf("index is :%d, log10(%.2f) = %.3f [%d - %s] %n", temp, index,
					 result, resultTime, printLongTime(resultTime, false));

			rtnValue.put(temp, new double []{ resultTime, (long) result});
			
			temp++;
		}
		
		return rtnValue;
	}

	public String printLongTime(long value, boolean showInConsole) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h

		String strCustom = df.format(value);
		if (showInConsole == true) {
			System.out.printf("%d : %s %n", value, strCustom);
		}

		return strCustom;
	}

	public String printLongTime(long value) {
		return printLongTime(value, false);

	}

	public static String printCalendar(Calendar values, boolean showInConsole) {
		long value = values.getTimeInMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h

		String strCustom = df.format(value);
		if (showInConsole == true) {
			System.out.printf("%d : %s %n", value, strCustom);
		}

		return strCustom;
	}

}
