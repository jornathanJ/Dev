package typeSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class effectValueGenerator {

	public static void main(String[] args) {

		String strType = "DAY";

		//Hour Sample
		long MinTime = (long) 1447902000460.0;
		long MaxTime = (long) 1447988400730.0;
		long[] userDefineTime = new long[] { (long) 1447923600016.0, (long) 1447934400758.0 };
		
		printLongTime(MinTime);
		getEffectData(MinTime, MaxTime, userDefineTime, "HOUR");
		
		//Day Sample
//		long MinTime = (long) 1447902000319.0;
//		long MaxTime = (long) 1448247600766.0;
//		long[] userDefineTime = new long[] { (long) 1447876800332.0 };
//		getEffectData(MinTime, MaxTime, null, "DAY");
		
		
		
		
	}
	
	public static void printLongTime(long value){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h, hh=12h

		String strCustom = df.format(value);
		//System.out.printf("%d : %s %n", value,  strCustom);
	}
	
	public static void getEffectData(long minTime, long maxTime, long [] userEvent, String strType){
		
		HashMap<Integer, long[]> effectDataSet = new HashMap<Integer, long[]>();
		HashMap<Integer, long[]> tempDataSet = new HashMap<Integer, long[]>();
		
		Calendar cMax = Calendar.getInstance();
		Calendar cMin = Calendar.getInstance();
		Calendar currentPosition = Calendar.getInstance();

		cMin.setTimeInMillis(minTime);
		cMax.setTimeInMillis(maxTime);
		currentPosition.setTimeInMillis(minTime);
		
		HashMap<Integer, Long> mTimeSequence = new HashMap<Integer, Long>();
		
		if(userEvent == null || userEvent.length == 0){
			mTimeSequence.put(0, minTime);
			mTimeSequence.put(1, maxTime);
		}
		else{
			mTimeSequence.put(0, minTime);
			for(int index = 0; index < userEvent.length; index++ ){
				mTimeSequence.put(index+1, userEvent[index]);
			}
			mTimeSequence.put(userEvent.length + 1, maxTime);
		}
		
		int resultIndex = 0;
		
		for(int loop = 0; loop < mTimeSequence.size()-1; loop++){
			
			
			long startTm = mTimeSequence.get(loop);
			long endTm = mTimeSequence.get(loop+1);
			
			printLongTime(startTm);
			printLongTime(endTm);
			
			long tempTm = startTm;
			while(true){
				if(tempTm >=endTm ){
					break;
				}
				else{
					if(strType.equals("DAY") == true){
						printLongTime(tempTm);
						tempTm = tempTm + (1000 * 60 * 60 * 24);
						printLongTime(tempTm);
					}
					else{
						printLongTime(tempTm);
						tempTm = tempTm + (1000 * 60 * 60);
						printLongTime(tempTm);
					}
				}
			}
			
			tempDataSet = calculateEffectData(startTm, tempTm, strType);
			//tempDataSet = calculateEffectData(startTm, endTm, strType);
			for(int index = 0; index < tempDataSet.size(); index++){
				effectDataSet.put(resultIndex, tempDataSet.get(index));
				resultIndex++;
			}
		}
		
		//System.out.println("Finish");
	}
	
	
	
	
	public static HashMap<Integer, long[]> calculateEffectData(long minTime, long maxTime, String strType){
		
		HashMap<Integer, long[]> effectDataSet = new HashMap<Integer, long[]>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); 
		
//		long minTime = (long) 1450364400727.0;
//		long maxTime = (long) 1453129199798.0;
		Calendar cMax = Calendar.getInstance();
		Calendar cMin = Calendar.getInstance();
		Calendar timePosition = Calendar.getInstance();

		cMin.setTimeInMillis(minTime);
		cMax.setTimeInMillis(maxTime);
		timePosition.setTimeInMillis(minTime);
		
		long timeGap = 0;
		if(strType.equals("DAY") == true){
			timeGap = (cMax.getTimeInMillis() - cMin.getTimeInMillis())/ (1000 * 60 * 60 * 24);
		}
		else{
			timeGap = (cMax.getTimeInMillis() - cMin.getTimeInMillis())/ (1000 * 60 * 60);
		}

		int temp = 0;
		int periodIndex = 0;
		long loopMax = timeGap + 1;
		long betweenTimeValue = (cMax.getTimeInMillis() - cMin.getTimeInMillis())/ loopMax;
		
		double initialValue = 9.0 / loopMax;

		//로그 함수 특성상 최소 값이 1이 되게 만들어야 합니다.
		for (double index = 1 + initialValue; index <= 10; index += initialValue) {

			if(strType.equals("DAY") == true){
				timePosition.setTimeInMillis(cMin.getTimeInMillis() + ((1000 * 60 * 60 * 24)*periodIndex));
			}
			else{
				timePosition.setTimeInMillis(cMin.getTimeInMillis() + (betweenTimeValue*periodIndex));
				int currentSeconds = timePosition.get(Calendar.SECOND);
				if(currentSeconds == 59){
					timePosition.add(Calendar.SECOND, 1);
				}
			}
			
			
			String strEffectTime = df.format(timePosition.getTime());
			
			System.out.format("index is :%d, log10(%.2f) = %.3f [%s] %n",
					temp++, index, 1 - Math.log10(index), strEffectTime);
			
			effectDataSet.put(periodIndex, new long[]{timePosition.getTimeInMillis(), (long) (1 - Math.log10(index))});
			
			periodIndex++;
		}
		
		return effectDataSet;
	}
}


