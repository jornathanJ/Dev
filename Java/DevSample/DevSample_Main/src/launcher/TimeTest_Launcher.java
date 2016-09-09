package launcher;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeTest_Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String strTime = "20151210001230000000";

		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS"); // HH=24h, hh=12h
		String conTime = "";
		try {
			conTime = Long.toString(df.parse(strTime).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTimeInMillis(df.parse(strTime).getTime());
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		printCalendar(calendar, true);

		//System.out.println(calendar);
//		DateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSSSSS"); // HH=24h, hh=12h
//		Date date = new Date(conTime);
//		String strResult = df2.format(date);
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
