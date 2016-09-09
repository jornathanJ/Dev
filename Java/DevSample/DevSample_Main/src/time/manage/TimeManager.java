package time.manage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.portal.domain.pdm.Event;
import com.portal.rest.pdm.TimeTableData;

public class TimeManager {

	private String strType = "";
	private List<Event> eventList = null;

	//Period Data
	private List<TimeTableData> timeTableList = new ArrayList<TimeTableData>();
	
	//Full Data
	private List<TimeTableData> timeTableListFull = new ArrayList<TimeTableData>();

	private long origin_startTimeValue = (long) 1447902000460.0;
	private long origin_endTimeValue = (long) 1447988400730.0;

	private long startTimeValue = (long) 1447902000460.0;
	private long endTimeValue = (long) 1447988400730.0;

	private Calendar cStart = Calendar.getInstance();
	private Calendar cEnd = Calendar.getInstance();

	public List<TimeTableData> getTimeTableList() {
		return timeTableList;
	}

	public void setTimeTableList(List<TimeTableData> timeTableList) {
		this.timeTableList = timeTableList;
	}

	public List<TimeTableData> getTimeTableListFull() {
		return timeTableListFull;
	}

	public void setTimeTableListFull(List<TimeTableData> timeTableListFull) {
		this.timeTableListFull = timeTableListFull;
	}

	public TimeManager(String strType, long start, long end, List<Event> eventList) {
		
		this.eventList = eventList;
		this.strType = strType;
		
		origin_startTimeValue = start;
		origin_endTimeValue = end;

		startTimeValue = this.normalizeTime(start);
		endTimeValue = this.normalizeTime(end);

		cStart = this.convetLontToCalender(startTimeValue);
		cEnd = this.convetLontToCalender(endTimeValue);

		cStart = this.setTime(cStart, "START", this.strType, true);
		cEnd = this.setTime(cEnd, "END", this.strType, true);
		
		startTimeValue = cStart.getTimeInMillis();
		endTimeValue = cEnd.getTimeInMillis();

		this.printCalendar(cStart, true);
		this.printCalendar(cEnd, true);

		this.printEventtime();
	}

	public void printEventtime() {

		System.out.println("**************printEventtime(Start)*****************");
		int index = 0;
		for (Event eventData : this.eventList) {
			long startTime = eventData.getTimePeriod().getFrom();
			long endTime = eventData.getTimePeriod().getTo();

			System.out.println(String.format("%d - [%s][%s]", index++, this.printLongAsCalendar(startTime, false),
					this.printLongAsCalendar(endTime, false)));
		}
		System.out.println("**************printEventtime(End)*****************");
	}

	public String validateTimeStatus(long timeValue) {
		String strStatus = "RUN";

		for (Event eventData : this.eventList) {

			long startTime = eventData.getTimePeriod().getFrom();
			long endTime = eventData.getTimePeriod().getTo();

			if (startTime <= timeValue && timeValue <= endTime) {
				strStatus = "DOWN";
				break;
			}
		}

		return strStatus;
	}

	public void setStatus() {

		int index = 0;

		for (TimeTableData data : timeTableListFull) {
			long startTime = data.getTimeStartValue();
			long endTime = data.getTimeEndValue();

			// System.out.println(String.format("%d - [%s][%s]", index++,
			// this.printLongAsCalendar(startTime, false),
			// this.printLongAsCalendar(endTime, false)));

			String strStStat = this.validateTimeStatus(startTime);
			// String strEdStat = this.validateTimeStatus(endTime);

			// Start 지점이 Run이면 Status는 Run입니다.
			if (strStStat.equals("RUN") == true) {
				data.setStrStatus("RUN");
			}
		}
	}

	public void MakeFullData() {

		long eventFrom = startTimeValue;
		long eventTo = endTimeValue;

		double timePeriodCount = 0;

		timePeriodCount = this.getTimeBetween(this.cStart, this.cEnd, this.strType);

		TimeTableData tempData = null;

		for (int index = 0; index < timePeriodCount; index++) {

			if (this.strType.equals("HOUR") == true) {
				eventFrom = addTimeValue(startTimeValue, index, Calendar.HOUR_OF_DAY);
				eventTo = addTimeValue(startTimeValue, index + 1, Calendar.HOUR_OF_DAY);
			} else {
				eventFrom = addTimeValue(startTimeValue, index, Calendar.DAY_OF_YEAR);
				eventTo = addTimeValue(startTimeValue, index + 1, Calendar.DAY_OF_YEAR);
			}

			tempData = new TimeTableData(index, convetLontToCalender(eventFrom), convetLontToCalender(eventTo), "DOWN",
					eventFrom, eventTo);
			timeTableListFull.add(tempData);
		}
		
		this.setStatus();
	}
	
	public void AddTimeBetweenCount() {

		boolean isNew = true;
		long cnt = 0;
		String strStatus = "";
		TimeTableData tempData = null;
		long startValue = 0;
		Calendar startCalendar = null;
		
		long endValue = 0;
		Calendar endCalendar = null;
		
		double position = 0;
		
		for (TimeTableData data : timeTableListFull) {

			if(isNew == true){
				startValue = data.getTimeStartValue();
				startCalendar = data.getCalendarStartTime(); 
				strStatus = data.getStrStatus();
				cnt = 1;
				isNew = false;
				continue;
			}
			else{
				endValue = data.getTimeEndValue();
				endCalendar = data.getCalendarEndTime();
			}
			
			
			if(strStatus.equals(data.getStrStatus()) == true){
				cnt++;
				continue;
			}
			else{
				//상태가 변경이 되면 이전 Data를 List에 추가한다.
				tempData = new TimeTableData(position++, startCalendar, data.getCalendarStartTime(), strStatus,
						startValue, data.getTimeStartValue());
				tempData.setDataPointCnt(cnt);
				timeTableList.add(tempData);
				
				
				startValue = data.getTimeStartValue();
				startCalendar = data.getCalendarStartTime(); 
				strStatus = data.getStrStatus();
				cnt = 1;
			}
		}
		
		tempData = new TimeTableData(position++, startCalendar, endCalendar, strStatus,
				startValue, endValue);
		tempData.setDataPointCnt(cnt);
		timeTableList.add(tempData);
	}
	
//	public void setEffectData(double focus, double alpha, double beta, DistributionType distributionType){
//
//		PDMController _PDMController = new PDMController();
//		int timeTableIndex = 0;
//
//		//구간별 Count를 가져 온다.
//		for(TimeTableData data : timeTableList){
//
//			double cnt = data.getDataPointCnt();
//			String strStatus = data.getStrStatus();
//			
//			double [] effectData =_PDMController.getSurvivalFunctionValue((int)cnt, alpha, beta, distributionType);
//			
//			for(int index = 0 ; index < cnt; index++){
//				TimeTableData dataTemp = timeTableListFull.get(index + timeTableIndex);
//
//				if(strStatus.equals("DOWN") == false)
//					dataTemp.setDataValue(effectData[index]);
//			}
//
//			timeTableIndex += (int)cnt;
//		}
//
//		for(TimeTableData data : timeTableListFull){
//
//			double dValue = data.getDataValue();
//
//			if (Double.isNaN(dValue)){
//				data.setStrFocusResult("BAD");
//				continue;
//			}
//
//			if(dValue < focus){
//				data.setStrFocusResult("BAD");
//			}
//			else{
//				data.setStrFocusResult("GOOD");
//			}
//		}
//	}

	public long addTimeValue(long value, int addValue, int field) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(value);

		calendar.add(field, addValue);

		long returnValue = calendar.getTimeInMillis();

		return returnValue;
	}

	public long normalizeTime(long value) {

		long rtnValue = value;
		rtnValue = rtnValue / 100000;
		rtnValue = rtnValue * 100000;

		return rtnValue;

	}

	public Calendar convetLontToCalender(long timeValue) {
		Calendar rtnValue = Calendar.getInstance();
		rtnValue.setTimeInMillis(timeValue);

		return rtnValue;
	}

	public void printPretty(List<TimeTableData> timeTableList) {

		System.out.println("");
		System.out
				.println("|-------------------------------------------------------------printEventtime(Start)-----------------------------------------------------------------------------|");

		String strHeader = String.format("%s%s%s%s%s%s%s%s%s", "IDX\t", "CalendarST\t\t\t", "CalendarED\t\t\t",
				"Status\t", "TimeStartValue\t", "TimeEndValue\t", "DataPointCnt\t", "DataValue\t", "FocusResult\t");
		System.out.println(strHeader);
		System.out
				.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

		for (TimeTableData data : timeTableList) {

			String strTemp0 = String.format("%.1f", data.getPosition());
			String strTemp1 = String.format("%d", data.getTimeStartValue());
			String strTemp2 = String.format("%d", data.getTimeEndValue());
			String strTemp3 = String.format("%.0f", data.getDataPointCnt());
			String strTemp4 = String.format("%f", data.getDataValue());
			String strValue = String.format("%s \t %s \t %s \t %s \t %s \t %s \t %s \t\t %s \t\t %s", strTemp0,
					printCalendar(data.getCalendarStartTime(), false), printCalendar(data.getCalendarEndTime(), false),
					data.getStrStatus(), strTemp1// data.getTimeStartValue()
					, strTemp2// data.getTimeEndValue()
					, strTemp3, strTemp4, data.getStrFocusResult());

			System.out.println(strValue);
		}

		System.out
				.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

	}

	public String printLongAsCalendar(long value, boolean showInConsole) {
		// long value = values.getTimeInMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h

		String strCustom = df.format(value);
		if (showInConsole == true) {
			System.out.printf("%d : %s %n", value, strCustom);
		}

		return strCustom;
	}

	public String printCalendar(Calendar values, boolean showInConsole) {
		long value = values.getTimeInMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h

		String strCustom = df.format(value);
		if (showInConsole == true) {
			System.out.printf("%d : %s %n", value, strCustom);
		}

		return strCustom;
	}

	public double getTimeBetween(Calendar start, Calendar end, String strType) {

		double rtnValue = 0;

		if (strType.equals("HOUR") == true) {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		} else {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		}

		/*
		 * System.out.format(
		 * "getTimeBetween strType[%s] :  [%s] --> [%s] Value is  : %d %n",
		 * strType, printCalendar(start, false), printCalendar(end, false),
		 * rtnValue);
		 */

		return rtnValue;
	}

	public long getTimeBetween(Calendar start, Calendar end, String strType, boolean bRemoveTail) {

		long rtnValue = 0;

		if (strType.equals("HOUR") == true) {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		} else {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		}

		/*
		 * System.out.format(
		 * "getTimeBetween strType[%s] :  [%s] --> [%s] Value is  : %d %n",
		 * strType, printCalendar(start, false), printCalendar(end, false),
		 * rtnValue);
		 */

		if (bRemoveTail == true) {
			return rtnValue - 1;
		} else {
			return rtnValue;
		}
	}

	

	public Calendar setTime(Calendar value, String strStartorEnd, String strType, boolean bShowConsole) {

		long customTime = value.getTimeInMillis();
		customTime = customTime / 100000;
		customTime = customTime * 100000;

		Calendar timeData = Calendar.getInstance();
		timeData.setTimeInMillis(customTime);

		int currentSeconds = timeData.get(Calendar.SECOND);
		int currentMinute = timeData.get(Calendar.MINUTE);
		int currentHours = timeData.get(Calendar.HOUR_OF_DAY);

		if (strType.equals("HOUR") == true) {

			if (currentSeconds > 0) {
				timeData.add(Calendar.SECOND, -currentSeconds);
			}

			if (strStartorEnd.equals("START") == true) {
				if (currentMinute > 0) {
					timeData.add(Calendar.MINUTE, 60 - currentMinute);
				}
			} else {
				if (currentMinute > 0) {
					timeData.add(Calendar.MINUTE, -currentMinute);
				} else if (currentMinute == 0) {
					// timeData.add(Calendar.HOUR, 1);
				}
			}
		} else {
			timeData.add(Calendar.HOUR_OF_DAY, -currentHours);
			timeData.add(Calendar.MINUTE, -currentMinute);
			timeData.add(Calendar.SECOND, -currentSeconds);

			if (strStartorEnd.equals("END") == true) {
				timeData.add(Calendar.DAY_OF_YEAR, 1);
			}
		}

		if (bShowConsole == true) {
			System.out.format("Set Times strType[%s] :  [%s] --> [%s] %n", strType, printCalendar(value, false),
					printCalendar(timeData, false));
		}

		return timeData;
	}
	
	
}
