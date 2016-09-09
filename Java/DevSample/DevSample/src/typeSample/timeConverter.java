package typeSample;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class timeConverter { 

	public static void main(String[] args) throws Exception {
		
		try{
		long today = System.currentTimeMillis(); // long 형의 현재시간
		//System.out.println(today);
		//today = Long.valueOf("1447775999171").longValue();

		//2015111800defaulttemp021447775999565

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h, hh=12h
		String str = df.format(today);
		System.out.println("today is [ " + str + " : " + today + " ] ");

//		Date date = new Date(today);
//		System.out.println(date);
		
		//"2015-11-17 10:00:00.000000";
		
		String message;
        Scanner scan = new Scanner(System.in);      // 문자 입력을 인자로 Scanner 생성
        
        message = scan.nextLine(); // 키보드 문자 입력
		
		String [] strTimeData = message.split("[\\s\\:\\-]");	
		
		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(  Integer.parseInt(strTimeData[0]),  Integer.parseInt(strTimeData[1])-1,  Integer.parseInt(strTimeData[2]),  Integer.parseInt(strTimeData[3]),  Integer.parseInt(strTimeData[4]),  Integer.parseInt(strTimeData[5]));
		long customTimeValue = dateTemp.getTimeInMillis();

		String strCustom = df.format(customTimeValue);
		//System.out.println("today is [ " + str + " : " + today + " ] ");
		
		System.out.println("입력 메시지: \"" + strCustom + " ==> " + customTimeValue);
		
		}
		catch(Exception ex){
			
		}finally{
			
		}
		
        
//        while(true){
//        
//        System.out.println("날자를 입력하세요: yyyy-MM-dd HH:mm:ss ");
//        
//        	
//			message = scan.nextLine(); // 키보드 문자 입력
//			
//			String [] strTimeData = message.split("[\\-\\s\\:]*");
//			
//			Calendar dateTemp = Calendar.getInstance();
//			dateTemp.set(year, month, date, hourOfDay, minute, second);
//			long customTimeValue = dateTemp.getTimeInMillis();
//
//			String strCustom = df.format(customTimeValue);
//			//System.out.println("today is [ " + str + " : " + today + " ] ");
//			
//			System.out.println("입력 메시지: \"" + strCustom + "\"");
//			
//			if(message.equals("EXIT")== true){
//				break;
//			}
//        }
	}
}
