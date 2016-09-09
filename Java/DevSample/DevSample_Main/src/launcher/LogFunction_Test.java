package launcher;

import java.util.ArrayList;
import java.util.List;

import com.portal.domain.pdm.Event;
import com.portal.domain.pdm.TimePeriod;
import com.portal.rest.pdm.TimeManager;

public class LogFunction_Test {

	public LogFunction_Test(){

	}

	public void Run(){

//		Test #1
//		long minTime = (long) 1450105200587.0;
//		long maxTime = (long)1450191600119.0;
//		long[] userDefineTime = new long[2];
//		userDefineTime[0] = (long)1450130850861.0;
//		userDefineTime[1] = (long)1450134510482.0;
//		String strTimeType = "HOUR";

//		Test #1-1
//		long minTime = (long) 1450105200587.0;
//		long maxTime = (long)1450191600119.0;
//		long[] userDefineTime = new long[2];
//		userDefineTime[0] = (long)1450130850861.0;
//		userDefineTime[1] = (long)1450139430613.0;
//		String strTimeType = "HOUR";

//		Test #2 -- 시간 사이에 Down이 있는 경우
//		long minTime = (long) 1449846000439.0;
//		long maxTime = (long)1449864000857.0;
//		long[] userDefineTime = new long[2];
//		userDefineTime[0] = (long)1449858000439.0;
//		userDefineTime[1] = (long)1449860000439.0;
//		String strTimeType = "HOUR";

//		Test #3
//		long minTime = (long) 1450105200587.0;
//		long maxTime = (long)1450191600119.0;
//		long[] userDefineTime = new long[2];
//		userDefineTime[0] = (long)1450116000000.0;
//		userDefineTime[1] = (long)1450134000000.0;
//		String strTimeType = "HOUR";

		long minTime = (long) 1450278000466.0;
		long maxTime = (long)1450364399466.0;
		long[] userDefineTime = new long[2];
		userDefineTime[0] = (long)1450303650000.0;
		userDefineTime[1] = (long)1450307310000.0;
		String strTimeType = "HOUR";

		String strTemp = String.format("%d", minTime);
		String strTemp2 = String.format("%.2f", 0.0);

		List<Event> eventList = new ArrayList<Event>();
		Event _Event = new Event();

		_Event = new Event();
		TimePeriod timePeriod = new TimePeriod();
		timePeriod.setFrom(userDefineTime[0]);
		timePeriod.setTo(userDefineTime[1]);
		_Event.setTimePeriod(timePeriod);
		eventList.add(_Event);

		_Event = new Event();
		timePeriod = new TimePeriod();
		timePeriod.setFrom((long)1450339890000.0);
		timePeriod.setTo((long)1450343490000.0);
		_Event.setTimePeriod(timePeriod);
		eventList.add(_Event);

		_Event = new Event();
		timePeriod = new TimePeriod();
		timePeriod.setFrom((long)1450356335000.0);
		timePeriod.setTo((long)1450359900000.0);
		_Event.setTimePeriod(timePeriod);
		eventList.add(_Event);





		TimeManager timeManager = new TimeManager("HOUR", minTime, maxTime, eventList);
		timeManager.InitData();
		timeManager.printPretty(timeManager.getTimeTableList());


		timeManager.NomalizeTime("HOUR", false);

		System.out.println("**********NomalizeTime**********");
		timeManager.printPretty(timeManager.getTimeTableList());


		timeManager.AddTimeBetweenCount();
		System.out.println("AddTimeBetweenCount------------");
		timeManager.printPretty(timeManager.getTimeTableList());

		long totalCntOfTime = timeManager.getTimeBetween();
		//System.out.println(String.format("total point Count is %d", totalCntOfTime));


		timeManager.MakeFullData();
//		System.out.println("**********MakeFullData**********");
//		timeManager.printPretty(timeManager.getTimeTableListFull());


		//timeManager.setEffectData(0.4, 0.5, 0.5, DistributionType.WEIBULL);
		System.out.println("**********setEffectData**********");
		timeManager.printPretty(timeManager.getTimeTableListFull());

		//TimeManager timeManagerEffect = timeManager.makeEffectTimeData(0.4, 0.5, 0.5, DistributionType.WEIBULL);
		//timeManagerEffect.printPretty(timeManagerEffect.getTimeTableListFull());
	}
}
