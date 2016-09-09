package typeSample;

import java.util.Calendar;

public class timeBetweenTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Calendar startTM = Calendar.getInstance();
		try {
			Thread.sleep(2210);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar currentTM = Calendar.getInstance();
		
		System.out.println(String.format("Sec : %d,  Mili : %d", getTimeBetween(startTM, currentTM, "SECOND"), getTimeBetween(startTM, currentTM, "MILI")));
		
	}
	
	public static long getTimeBetween(Calendar start, Calendar end, String strType) {

		long rtnValue = 0;

		switch(strType){
		
		case "MILI":			
			rtnValue = end.getTimeInMillis() - start.getTimeInMillis();
			break;
		
		case "SECOND":			
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000);
			break;
			
		case "MINUTE":			
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60);
			break;
			
		case "HOUR":
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60);
			break;
			
		case "DAY":
			rtnValue = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
			break;
		}

		return rtnValue;
	}

}
