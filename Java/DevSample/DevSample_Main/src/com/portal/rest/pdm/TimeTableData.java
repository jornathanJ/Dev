package com.portal.rest.pdm;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeTableData {



	@Override
	public String toString() {
		String strHeader = String.format("%s%s%s%s%s%s%s"
				, "IDX\t\t"
				, "CalendarST\t\t\t\t\t\t"
				, "CalendarED\t\t\t\t\t\t"
				, "Status\t"
				, "TimeStartValue\t"
				, "TimeEndValue\t"
				, "DataValue\t"
				, "FocusResult\t"
				);

		String strSplit = "----------------------------------------------------------------------------------------------------------------------";


		String strTemp0 = String.format("%.1f", this.getPosition());
		String strTemp1 = String.format("%d", this.getTimeStartValue());
		String strTemp2 = String.format("%d", this.getTimeEndValue());
		String strTemp3 = String.format("%.0f", this.dataPointCnt);
		String strTemp4 = String.format("%f", this.dataValue);
		String strValue = String.format("%s \t %s \t %s \t %s \t %s \t %s \t %s \t %s \t %s"
				, strTemp0
				, printCalendar(this.getCalendarStartTime(), false)
				, printCalendar(this.getCalendarEndTime(), false)
				, this.strStatus
				, strTemp1//data.getTimeStartValue()
				, strTemp2//data.getTimeEndValue()
				, strTemp3
				, strTemp4
				, this.strFocusResult
				);

		return String.format("%s\r\n%s\r\n%s", strHeader, strSplit, strValue);
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

	private double position = 0;
	private Calendar calendarStartTime = null;
	private Calendar calendarEndTime = null;
	private String strStatus = "";
	private long timeStartValue = 0;
	private long timeEndValue = 0;
	private double dataPointCnt = Double.NaN;
	private double dataValue = Double.NaN;
	private String strFocusResult = "BAD";

	public String getStrFocusResult() {
		return strFocusResult;
	}

	public void setStrFocusResult(String strFocusResult) {
		this.strFocusResult = strFocusResult;
	}

	public TimeTableData(double position
			, Calendar calendarStartTime
			, Calendar calendarEndTime
			, String strStatus
			, long timeStartValue
			, long timeEndValue
			){
		this.position = position;
		this.calendarStartTime = calendarStartTime;
		this.calendarEndTime = calendarEndTime;
		this.strStatus = strStatus;
		this.timeStartValue = timeStartValue;
		this.timeEndValue = timeEndValue;
	}

	public double getPosition() {
		return position;
	}

	public void setPosition(double position) {
		this.position = position;
	}

	public Calendar getCalendarStartTime() {
		return calendarStartTime;
	}

	public void setCalendarStartTime(Calendar calendarStartTime) {
		this.calendarStartTime = calendarStartTime;
	}

	public Calendar getCalendarEndTime() {
		return calendarEndTime;
	}

	public void setCalendarEndTime(Calendar calendarEndTime) {
		this.calendarEndTime = calendarEndTime;
	}

	public String getStrStatus() {
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public long getTimeStartValue() {
		return timeStartValue;
	}

	public void setTimeStartValue(long timeStartValue) {
		this.timeStartValue = timeStartValue;
	}

	public long getTimeEndValue() {
		return timeEndValue;
	}

	public void setTimeEndValue(long timeEndValue) {
		this.timeEndValue = timeEndValue;
	}

	public double getDataPointCnt() {
		return dataPointCnt;
	}

	public void setDataPointCnt(double dataPointCnt) {
		this.dataPointCnt = dataPointCnt;
	}

	public double getDataValue() {
		return dataValue;
	}

	public void setDataValue(double dataValue) {
		this.dataValue = dataValue;
	}

}
