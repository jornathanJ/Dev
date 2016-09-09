package com.portal.rest.pdm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.portal.domain.pdm.Event;
import com.portal.domain.pdm.TimePeriod;

public class TimeManager {


	//private boolean isDown = false;
	private String strType = "";
	private List<Event> eventList = null;

	//구간별 Data
	private List<TimeTableData> timeTableList = new ArrayList<TimeTableData>();

	private List<TimeTableData> timeTableListFull = new ArrayList<TimeTableData>();

	private List<TimeTableData> timeTableListEffect = new ArrayList<TimeTableData>();

	private LogFunction logFuction = null;

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

	public void MakeFullData(){

		double position = 0;
		long startValue = 0;
		long endValue = 0;
		long indexTime = 0;
		String strStatus = "";

		TimeTableData tempData = null;

		for(TimeTableData data : timeTableList ){
			startValue = data.getTimeStartValue();
			endValue = data.getTimeEndValue();
			strStatus = data.getStrStatus();

			Calendar startCalendar = Calendar.getInstance();
			Calendar tempCalender = Calendar.getInstance();

			startCalendar.setTimeInMillis(startValue);
			tempCalender.setTimeInMillis(startValue);

			do {

				tempCalender.add(Calendar.HOUR_OF_DAY, 1);
				indexTime = tempCalender.getTimeInMillis();

				tempData = new TimeTableData(position++
						, (Calendar)startCalendar.clone(), (Calendar)tempCalender.clone()
						, strStatus
						, startCalendar.getTimeInMillis()
						, indexTime);

				if(strStatus.equals("DOWN") == true){
					//System.out.println("DOWN");
				}
				timeTableListFull.add(tempData);

				startCalendar.setTimeInMillis(indexTime);

			}while(indexTime <= endValue);
		}

//		if(timeTableListFull != null && timeTableListFull.size() > 1){
//			timeTableListFull.remove(timeTableListFull.size()-);
//		}
	}

	public TimeManager(String strType, long start, long end, List<Event> eventList){
		startTimeValue = start;
		endTimeValue = end;
		this.eventList = eventList;
		this.strType = strType;

		start = start / 100000;
		start = start * 100000;

		end = end / 100000;
		end = end * 100000;

		cStart = this.convetLontToCalender(start);
		cEnd = this.convetLontToCalender(end);
	}

	public void InitData(){

		double position = 0;
		long tempTimeValue = 0;
		String strStatus = "RUN";
		TimeTableData tempData = null;

		//Normalization Event Data
		for(Event event : eventList){
			tempTimeValue = event.getTimePeriod().getFrom();
			//tempTimeValue = event.getTimePeiod().getFrom();
			if(startTimeValue > tempTimeValue){
				strStatus = "DOWN";

			}

			break;
		}

		tempData = new TimeTableData(position++, cStart, convetLontToCalender(tempTimeValue)
				, strStatus, startTimeValue, tempTimeValue);
		timeTableList.add(tempData);

		long PRE_FROM = startTimeValue;
		long PRE_TO = tempTimeValue;

		long eventFrom = 0;
		long eventTo = 0;

		//Normalization Event Data
		for(Event event : eventList){

			//event.getTimePeiod().setFrom( event.getTimePeiod().getFrom());
			//event.getTimePeiod().setTo( event.getTimePeiod().getTo());

			eventFrom = event.getTimePeriod().getFrom();
			eventTo = event.getTimePeriod().getTo();

			//Hourly에서 Down Event 처리시에
			//1) 앞뒤 시간이 1시간 이내이고
			//2) 같은 시간인 경우는 제외합니다.
			if(this.strType.equals("HOUR") == true){
				long periodCnt = (eventTo - eventFrom) / (1000 * 60 * 60);

				if(periodCnt == 0){
					Calendar start = convetLontToCalender(eventFrom);
					Calendar ed = convetLontToCalender(eventTo);

					int startHour = start.get(Calendar.HOUR_OF_DAY);
					int endHour = ed.get(Calendar.HOUR_OF_DAY);
					if(startHour == endHour){
						System.out.println("Skip DownEvent within 1 hour");
						continue;
					}
				}
			}

			//앞선 Event와 동일한 시간이라면 그냥 Down 시간 구간을 설정
			//그렇지 않은 경우라면 Run에 대한 Event구간을 추가로 설정해야 한다.
			if(PRE_TO != eventFrom){
				tempData = new TimeTableData(position++
						, convetLontToCalender(PRE_TO)
						, convetLontToCalender(eventFrom)
						, "RUN"
						, PRE_TO
						, eventFrom);
				timeTableList.add(tempData);
			}else{

			}

			tempData = new TimeTableData(position++
					, convetLontToCalender(eventFrom)
					, convetLontToCalender(eventTo)
					, "DOWN"
					, eventFrom
					, eventTo);
			timeTableList.add(tempData);

			PRE_TO = eventTo;

		}

		//마지막 시간을 처리 합니다.
		if(PRE_TO < cEnd.getTimeInMillis()){
			tempData = new TimeTableData(position++
					, convetLontToCalender(PRE_TO)
					, convetLontToCalender(endTimeValue)
					, "RUN"
					, PRE_TO
					, endTimeValue);
			timeTableList.add(tempData);
		}


		//this.NomalizeTime(this.strType);
	}

	public void NomalizeTime(String strType, boolean bShowConsole){
		for(TimeTableData data : timeTableList){

			Calendar startCalendar = this.setTime(data.getCalendarStartTime(), "START", strType, bShowConsole);
			Calendar endCalender = this.setTime(data.getCalendarEndTime(), "END", strType, bShowConsole);

			data.setCalendarStartTime(  startCalendar);
			data.setCalendarEndTime(  endCalender);

			data.setTimeStartValue( startCalendar.getTimeInMillis());
			data.setTimeEndValue( endCalender.getTimeInMillis());
		}
	}

	public Calendar setTime(Calendar value, String strType, String strTimePeriod, boolean bShowConsole) {

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
					timeData.add(Calendar.MINUTE, 60-currentMinute);
				}
			} else {
				if (currentMinute > 0) {
					timeData.add(Calendar.MINUTE, -currentMinute);
				} else if (currentMinute == 0) {
					//timeData.add(Calendar.HOUR, 1);
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

		if(bShowConsole == true){
			System.out.format("Set Times strType[%s] :  [%s] --> [%s] %n", strType, printCalendar(value, false),
					printCalendar(timeData, false));
		}

		return timeData;
	}

	public void printPretty(List<TimeTableData> timeTableList){

		String strHeader = String.format("%s%s%s%s%s%s%s%s%s"
				, "IDX\t"
				, "CalendarST\t\t\t"
				, "CalendarED\t\t\t"
				, "Status\t"
				, "TimeStartValue\t"
				, "TimeEndValue\t"
				, "DataPointCnt\t"
				, "DataValue\t"
				, "FocusResult\t"
				);
		System.out.println(strHeader);
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

		for(TimeTableData data : timeTableList){

			String strTemp0 = String.format("%.1f", data.getPosition());
			String strTemp1 = String.format("%d", data.getTimeStartValue());
			String strTemp2 = String.format("%d", data.getTimeEndValue());
			String strTemp3 = String.format("%.0f", data.getDataPointCnt());
			String strTemp4 = String.format("%f", data.getDataValue());
			String strValue = String.format("%s \t %s \t %s \t %s \t %s \t %s \t %s \t\t %s \t\t %s"
					, strTemp0
					, printCalendar(data.getCalendarStartTime(), false)
					, printCalendar(data.getCalendarEndTime(), false)
					, data.getStrStatus()
					, strTemp1//data.getTimeStartValue()
					, strTemp2//data.getTimeEndValue()
					, strTemp3
					, strTemp4
					, data.getStrFocusResult()
					);

			System.out.println(strValue);
		}
	}

	public Calendar convetLontToCalender(long timeValue){
		Calendar rtnValue = Calendar.getInstance();
		rtnValue.setTimeInMillis(timeValue);

		return rtnValue;
	}

	/**
	 * 한글 주석이 이쁘게 달린다.
	 * @param customTime
	 * @param strType "START" || "END"
	 * @param strTimePeriod -- HOUR | DAY
	 * @return
	 */
	public long setTime(long customTime, String strType, String strTimePeriod) {

		//long customTime = value.getTimeInMillis();
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
				//현재 설정된 시간보다 1시간 더 가져 오도록 합니다. - HBase 조회를 위해서
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
				//현재 설정된 시간보다 1일간 더 가져 오도록 합니다. - HBase 조회를 위해서
				timeData.add(Calendar.DAY_OF_YEAR, 1);
			}
		}

		System.out.format("Set Times strType[%s] :  [%s] --> [%s] %n", strType
				, this.printLongAsCalendar(customTime, false),
				printCalendar(timeData, false));

		return timeData.getTimeInMillis();
	}

	public String printLongAsCalendar(long value, boolean showInConsole) {
		//long value = values.getTimeInMillis();
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

	public long getTimeBetween() {

		//전체 Count는 1개 더 나옵니다.
		return getTimeBetween(cStart, cEnd, strType) + 1;
	}

	public long getTimeBetween(Calendar start, Calendar end, String strType) {

		long rtnValue = 0;

		if (strType.equals("HOUR") == true) {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		} else {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		}

		/*System.out.format("getTimeBetween strType[%s] :  [%s] --> [%s] Value is  : %d %n", strType,
				printCalendar(start, false), printCalendar(end, false), rtnValue);*/

		return rtnValue;
	}

	public long getTimeBetween(Calendar start, Calendar end, String strType, boolean bRemoveTail) {

		long rtnValue = 0;

		if (strType.equals("HOUR") == true) {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
		} else {
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		}

		/*System.out.format("getTimeBetween strType[%s] :  [%s] --> [%s] Value is  : %d %n", strType,
				printCalendar(start, false), printCalendar(end, false), rtnValue);*/

		if(bRemoveTail == true){
			return rtnValue-1;
		}
		else{
			return rtnValue;
		}
	}

	public void AddTimeBetweenCount(){

		for(TimeTableData data : timeTableList){

			long cnt = 0;
			Calendar start = data.getCalendarStartTime();
			Calendar end = data.getCalendarEndTime();

			//printCalendar(start, true);
			//printCalendar(end, true);

			cnt = getTimeBetween(start, end, this.strType, false);

			data.setDataPointCnt(cnt);
		}
	}

//	public void setEffectData(double focus, double alpha, double beta, DistributionType distributionType){
//
//		PDMController _PDMController = new PDMController();
//
////		alpha = 0.5;
////		beta = 0.5;
//		//int sampleCnt =0;
//
//
//		int timeTableIndex = 0;
//
//		//구간별 Count를 가져 온다.
//		for(TimeTableData data : timeTableList){
//
//			double cnt = data.getDataPointCnt();
//			String strStatus = data.getStrStatus();
//
//			if(strStatus.equals("DOWN") && cnt == 0){
//				cnt = 1;
//			}else{
//				cnt = cnt +1;
//			}
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
//
//			//this.printPretty(timeTableListFull);
//
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
//
//		}
//
//
//	}

//	public TimeManager makeEffectTimeData(double focus, double shapeParameter, double scaleParameter, DistributionType distributionType){
//
//		//this.setEffectData(shapeParameter, scaleParameter, distributionType);
//
//
//
//		System.out.println("**********setEffectData**********");
//		this.printPretty(timeTableListFull);
//
//		List<Event> eventList = new ArrayList<Event>();
//
//		double position = 0;
//		boolean bStart = true;
//		timeTableListEffect = new ArrayList<TimeTableData>();
//
//		Event event = new Event();
//		TimePeriod timePeriod = new TimePeriod();
//		event.setTimePeriod(timePeriod);
//		//eventList.add(event);
//
//		String strStatus_CUR = "GOOD";
//		String strStatus_PRE = "GOOD";
//		long preTimeValue = 0;
//
//		for(TimeTableData data : timeTableListFull){
//			double dValue = data.getDataValue();
//
//			//System.out.println(data.getPosition());
//
//			//if(dValue == Double.NaN)
//			if (Double.isNaN(dValue)){
//				dValue = 0;
//			}
//
//			if(focus < dValue){
//				//이전 결과가 BAD인 경우라면 시간 구간이 끝난 것을 의미 합니다.
//				if(strStatus_PRE.equals("BAD") == true){
//					if(timePeriod.getFrom() == 0){
//						timePeriod.setFrom(data.getTimeStartValue());
//					}
//					else{
//						//timePeriod.setTo(data.getTimeStartValue());
//						timePeriod.setTo(preTimeValue);
//						eventList.add(event);
//
//						event = new Event();
//						timePeriod = new TimePeriod();
//						event.setTimePeriod(timePeriod);
//						strStatus_PRE = "GOOD";
//					}
//				}
//			}
//			else{
//				//BAD
//				//System.out.println("BAD");
//				if(strStatus_PRE.equals("GOOD") == true){
//					strStatus_PRE = "BAD";
//
//					if(timePeriod.getFrom() == 0){
//						timePeriod.setFrom(data.getTimeStartValue());
//					}
//					else{
//						if(strStatus_PRE.equals("BAD") == false){
//							//timePeriod.setTo(data.getTimeStartValue());
//							timePeriod.setTo(preTimeValue);
//						}
//					}
//				}
//				else{
//					preTimeValue = data.getTimeStartValue();
//				}
//			}
//		}
//
//		if(timePeriod.getFrom() != 0){
//			timePeriod.setTo(preTimeValue);
//			eventList.add(event);
//		}
//
//		for(Event eventData : eventList){
//
//			System.out.println(
//					String.format("%d - %d"
//							,eventData.getTimePeriod().getFrom(), eventData.getTimePeriod().getTo())
//					);
//		}
//
//		System.out.println("Finish Make Event");
//
//
//
//		TimeManager rtnValue = new TimeManager(this.strType, cStart.getTimeInMillis(), cEnd.getTimeInMillis(), eventList);
//
//		rtnValue.InitData();
//		System.out.println("**********InitData**********");
//		rtnValue.printPretty(rtnValue.getTimeTableList());
//
//		//rtnValue.NomalizeTime("HOUR", false);
//		//rtnValue.AddTimeBetweenCount();
//
//		rtnValue.MakeFullData();
//		//timeManager.printPretty(timeManager.getTimeTableList());
//
//		return rtnValue;
//
//	}
}
