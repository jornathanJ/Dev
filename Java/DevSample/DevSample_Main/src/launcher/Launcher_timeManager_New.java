package launcher;

import java.util.ArrayList;
import java.util.List;

import time.manage.TimeManager;

import com.portal.domain.pdm.Event;
import com.portal.domain.pdm.TimePeriod;

public class Launcher_timeManager_New {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// String strTimeType = "HOUR";
		// long minTime = (long) 1450278000466.0;
		// long maxTime = (long) 1450364400000.0;

		String strTimeType = "HOUR";
		long minTime = (long) 1443625200203.0;
		// long maxTime = (long) 1450421999000.0;
		long maxTime = (long) 1448892000072.0;
		
		//double [] data =  (double [])(double [])dataMap;

		// 2015-11-01 0:00
		// 결과값: "2015-11-01 00:00:00.000216 ==> 1446303600216
		// 2015-11-30 0:00
		// 결과값: "2015-11-30 00:00:00.000057 ==> 1448809200057

		// 2015-12-12 00:00:00.000000 - 1449849599000
		// 2015-12-18 15:00:00.000000 - 1450421999000

		// 2015-12-15 15:00:00.000000 - 1450162799000
		// 2015-12-16 08:00:00.000000 - 1450223999000

		String strTemp = String.format("%d", minTime);
		String strTemp2 = String.format("%.2f", 0.0);

		List<Event> eventList = getEventDemo(4);

		TimeManager _manager = new TimeManager(strTimeType, minTime, maxTime, eventList);
		_manager.MakeFullData();
		// _manager.setStatus();
		_manager.AddTimeBetweenCount();
		//_manager.setEffectData(0.8, 0.9, 0.002, DistributionType.WEIBULL);
		_manager.printPretty(_manager.getTimeTableList());

		_manager.printPretty(_manager.getTimeTableListFull());
	}

	public static List<Event> getEventDemo(int index) {

		List<Event> eventList = new ArrayList<Event>();

		Event _Event = new Event();
		TimePeriod timePeriod = new TimePeriod();

		switch (index) {

		case 0: // Hour #1

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450303650000.0);
			timePeriod.setTo((long) 1450307310000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450339890000.0);
			timePeriod.setTo((long) 1450359900000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);
			break;

		case 1: // Hour #2
			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450303650000.0);
			timePeriod.setTo((long) 1450307310000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450339890000.0);
			timePeriod.setTo((long) 1450343490000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450356335000.0);
			timePeriod.setTo((long) 1450359900000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);
			break;

		case 2: // Day #1

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1450162799000.0);
			timePeriod.setTo((long) 1450223999000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			// _Event = new Event();
			// timePeriod = new TimePeriod();
			// timePeriod.setFrom((long) 1450339890000.0);
			// timePeriod.setTo((long) 1450359900000.0);
			// _Event.setTimePeriod(timePeriod);
			// eventList.add(_Event);
			break;

		case 3: // Day #2

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1447167600000.0);
			timePeriod.setTo((long) 1447340400000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1447858800000.0);
			timePeriod.setTo((long) 1448377200000.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);
			break;
			
		case 4: // HOUR #3

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1445299200946.0);
			timePeriod.setTo((long) 1445572800928.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);

			_Event = new Event();
			timePeriod = new TimePeriod();
			timePeriod.setFrom((long) 1446285600839.0);
			timePeriod.setTo((long) 1447081200107.0);
			_Event.setTimePeriod(timePeriod);
			eventList.add(_Event);
			break;
			
			
		}
		

		return eventList;
	}

}
