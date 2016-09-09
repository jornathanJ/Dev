package SampleDataCreate;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class TimeTracePOC {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_TimeTrace".getBytes();

	public final static byte[] TABLE_NAME = "TimeTrace".getBytes();

	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();

	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	public final static byte[] COL_DAY = "day".getBytes();
	public final static byte[] COL_HOUR = "hour".getBytes();
	public final static byte[] COL_MINUTE = "min".getBytes();
	public final static byte[] COL_SEC = "second".getBytes();

	public final static byte[] COL_PARAMETER = "parameter".getBytes();
	public final static byte[] COL_RECIPE = "recipe".getBytes();

	public final static byte[] COL_START = "start_dtts".getBytes();
	public final static byte[] COL_END = "end_dtts".getBytes();
	public final static byte[] COL_DATATYPE = "datatype".getBytes();

//	public final static byte[] COL_W_START = "window_start".getBytes();
//	public final static byte[] COL_W_END = "window_end".getBytes();
//	public final static byte[] COL_W_UNIT = "window_unit".getBytes();
//	public final static byte[] COL_SOURCE_PARAMETER = "source_parameters".getBytes();

//	public final static byte[] COL_MIN = "min".getBytes();
//	public final static byte[] COL_MAX = "max".getBytes();
//	public final static byte[] COL_STD = "std".getBytes();
//	public final static byte[] COL_MEAN = "mean".getBytes();

	// public final static byte[] COL_EMAIL = "email".getBytes();
	// public final static byte[] COL_PASSWORD = "password".getBytes();

	public static String getTimeData(String strYear, String strMonth, String strDay, String strHour, String strMinute,
			String strSec) {
		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1, Integer.parseInt(strDay),
				Integer.parseInt(strHour), Integer.parseInt(strMinute), Integer.parseInt(strSec));
		long customTimeValue = dateTemp.getTimeInMillis();

		return Long.toString(customTimeValue);
	}

	public static void main(String[] args) {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "a3-spark-master");
		//config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		// config.addResource(new Path(System.getenv("HBASE_CONF_DIR"),
		// "hbase-site.xml"));

		try {

			try (Connection connection = ConnectionFactory
					.createConnection(config);
					Admin admin = connection.getAdmin()) {

				HTableDescriptor table = new HTableDescriptor(
						TableName.valueOf(TABLE_NAME));
				table.addFamily(new HColumnDescriptor(COLUMN_FAMILY));

				if (!admin.tableExists(table.getTableName())) {
					System.out.print("Creating table. ");
					admin.createTable(table);
					System.out.println(" Done.");
				}
			}

			String strYear = "2015";
			String strMonth = "12";
			String strDay = "01";
			String strHour = "00";
			String strMinute = "00";

			//String strMinute = "00";
			//String strSec = "00";
			String strParameter = "TEMP02";
			//String strSourceParameter = "trace.PRESSURE01";
			String strRecipe = "Recipe01";
			String strWindowsUnit = "time";

			String strTool = "EQP02";
			String strModule = "CHA";

			String strSecondDefault = "20";


			// eqp01 ch01 2015 11 10 00 pdm_param01_sum01 1448996694763

			Param_Macro_min _min = new Param_Macro_min();
			Param_Macro_max _max = new Param_Macro_max();
			Param_Macro_STD _std = new Param_Macro_STD();
			Param_Macro_Mean _mean = new Param_Macro_Mean();

			Calendar startCalendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();

			String startTimeSting = String.format("2015-12-%s %s:00:00" , strDay, strHour);
			String[] strTimeData = startTimeSting.split("[\\s\\:\\-]");

			Calendar dateTemp = Calendar.getInstance();
			dateTemp.set(Integer.parseInt(strTimeData[0]), Integer.parseInt(strTimeData[1]) - 1,
					Integer.parseInt(strTimeData[2]), Integer.parseInt(strTimeData[3]),
					Integer.parseInt(strTimeData[4]), Integer.parseInt(strTimeData[5]));
			long customTimeValue = dateTemp.getTimeInMillis();

			startCalendar.setTimeInMillis(dateTemp.getTimeInMillis());
			endCalendar.setTimeInMillis(dateTemp.getTimeInMillis());

			//printCalendar(startCalendar, true);

			//String strCustom = df.format(customTimeValue);


//			String strStart = String.format("2015-12-%s %s:00:00" , strDay, String.format("%02d", loop));
//			String strEnd = String.format("2015-12-%s %s:59:59" , strDay, String.format("%02d", loop));
//


			currentImport = new ArrayList<Put>();

			int minutePosition = 0;
			//int hourPosition = 0;
			int initPosition = 0;
			for (int loop = 0; loop < 10000; loop++)
			//for (int loop = initPosition; loop < initPosition + 1600; loop += 10)
			{


				long tempValue = dateTemp.getTimeInMillis();
				tempValue = tempValue / 100000;
				tempValue = tempValue * 100000;
				startCalendar.setTimeInMillis(tempValue);


				minutePosition++;
				minutePosition++;
				minutePosition++;
				minutePosition++;

				startCalendar.add(Calendar.MINUTE, minutePosition++);

				endCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
				//endCalendar.add(Calendar.MINUTE, 59);
				endCalendar.add(Calendar.SECOND, 59);

				strDay = String.format("%02d", startCalendar.get(Calendar.DAY_OF_MONTH));
				strHour = String.format("%02d", startCalendar.get(Calendar.HOUR_OF_DAY));
				strMinute = String.format("%02d", startCalendar.get(Calendar.MINUTE));



				//strHour = String.format("%02d", loop);
				String strRowKey = String.format("%s%s%s%s%s%s%s%s%s",
						strTool, strModule, strYear, strMonth , strDay, strHour, strMinute, strParameter, endCalendar.getTimeInMillis());

				String strOutString = String.format("[ Add Data - %s] %s - %s", strRowKey, printCalendar(startCalendar, false), printCalendar(endCalendar, false));

				String startValue =  String.valueOf(startCalendar.getTimeInMillis());
				String EndtValue =  String.valueOf(endCalendar.getTimeInMillis());

				//System.out.println(strRowKey);
				//System.out.println( String.format("2015-12-%s %s:00:00",strDay, strHour) );


				Put p = new Put(Bytes.toBytes(strRowKey));
				p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes(strTool));
				p.addColumn(COLUMN_FAMILY, COL_MODULE, Bytes.toBytes(strModule));

				p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
				p.addColumn(COLUMN_FAMILY, COL_MONTH, Bytes.toBytes(strMonth));
				p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
				p.addColumn(COLUMN_FAMILY, COL_HOUR, Bytes.toBytes(strHour));
				p.addColumn(COLUMN_FAMILY, COL_MINUTE, Bytes.toBytes(strMinute));

				p.addColumn(COLUMN_FAMILY, COL_PARAMETER, Bytes.toBytes(strParameter));
				p.addColumn(COLUMN_FAMILY, COL_RECIPE, Bytes.toBytes(strRecipe));

				p.addColumn(COLUMN_FAMILY, COL_START, Bytes.toBytes(startValue));
				p.addColumn(COLUMN_FAMILY, COL_END, Bytes.toBytes(EndtValue));
				p.addColumn(COLUMN_FAMILY, COL_DATATYPE, Bytes.toBytes("CONTINUOUS"));

				p.addColumn(COLUMN_FAMILY, COL_SEC,Bytes.toBytes(strSecondDefault));


				StringBuilder sbMin = new StringBuilder();
//				StringBuilder sbMax = new StringBuilder();
//				StringBuilder sbSTD = new StringBuilder();
//				StringBuilder sbMean = new StringBuilder();

				for(int k = 20; k < 21; k++){
					String strTemp = String.format("%02d", k);
					sbMin = new StringBuilder();

					for(int pos = 0; pos <= 1024; pos++){
						//sbMin.append(String.format("%f,", _min.min_data[initPosition]));
						//sbMin.append(String.format("%f,", _max.max_data[initPosition]));
						//sbMin.append(String.format("%f,", _std.std_data[initPosition]));
						sbMin.append(String.format("%f,", _mean.mean_data[initPosition]));
						//sbMax.append(String.format("%f", _max.max_data[initPosition]));
//						sbSTD.append(String.format("%f", _std.std_data[initPosition]));
//						sbMean.append(String.format("%f", _mean.mean_data[initPosition]));

						initPosition++;
						if(initPosition == 6500){
							initPosition = 0;
						}
					}

					p.addColumn(COLUMN_FAMILY, Bytes.toBytes(strTemp), Bytes.toBytes(sbMin.substring(0, sbMin.length()-1)));
//					p.addColumn(COLUMN_FAMILY, COL_MAX, Bytes.toBytes(sbMax.toString()));
//					p.addColumn(COLUMN_FAMILY, COL_STD, Bytes.toBytes(sbSTD.toString()));
//					p.addColumn(COLUMN_FAMILY, COL_MEAN, Bytes.toBytes(sbMean.toString()));
				}


				//System.out.println(String.format("%s  [%s]", strOutString, sbMin.toString()));
				System.out.println(strOutString);




				currentImport.add(p);



			}

			try (Connection conn = ConnectionFactory.createConnection(config);
					Admin admin = conn.getAdmin()) {

				Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
				table.put(currentImport);

				table.close();


			} finally {
				currentImport.clear();
			}



			System.out.println("finish");

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception es) {
			es.printStackTrace();
		}

	}

	public static String printCalendar(Calendar values, boolean showInConsole) {
		long value = values.getTimeInMillis();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h

		String strCustom = df.format(value);
		if (showInConsole == true) {
			System.out.printf("%d : %s %n", value, strCustom);
		}

		return String.format("%d : %s %n", value, strCustom);
	}

	public static long convertTimeStringToLong(String strTime) {
		String[] strTimeData = strTime.split("[\\s\\:\\-]");

		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strTimeData[0]), Integer.parseInt(strTimeData[1]) - 1,
				Integer.parseInt(strTimeData[2]), Integer.parseInt(strTimeData[3]), Integer.parseInt(strTimeData[4]),
				Integer.parseInt(strTimeData[5]));
		long customTimeValue = dateTemp.getTimeInMillis();

		return customTimeValue;
	}


}
