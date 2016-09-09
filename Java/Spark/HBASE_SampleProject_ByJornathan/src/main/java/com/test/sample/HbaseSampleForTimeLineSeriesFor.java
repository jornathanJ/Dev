package com.test.sample;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class HbaseSampleForTimeLineSeriesFor {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_timeseriestrace".getBytes();

	public final static byte[] TABLE_NAME = "timeseriestrace".getBytes();

	// public final static byte[] COL_NAME0 = "KEY".getBytes();
	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	public final static byte[] COL_DAY = "day".getBytes();
	public final static byte[] COL_HOUR = "hour".getBytes();
	public final static byte[] COL_OPERATION = "operation".getBytes();
	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();
	public final static byte[] COL_START_DTTS = "start_dtts".getBytes();
	public final static byte[] COL_END_DTTS = "end_dtts".getBytes();
	public final static byte[] COL_PRODUCT = "product".getBytes();
	public final static byte[] COL_RECIPE = "recipe".getBytes();
	public final static byte[] COL_RECIPE_STEP = "recipe_step".getBytes();
	public final static byte[] COL_PARAMETER = "parameter".getBytes();
	public final static byte[] COL_VALUE = "0".getBytes();
	public final static byte[] COL_TIME = "0_timestamp".getBytes();

	// public final static byte[] COL_EMAIL = "email".getBytes();
	// public final static byte[] COL_PASSWORD = "password".getBytes();

	public static String getTimeData(String strYear, String strMonth,
			String strDay, String strHour, String strMinute, String strSec) {
		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) -1,
				Integer.parseInt(strDay), Integer.parseInt(strHour),
				Integer.parseInt(strMinute), Integer.parseInt(strSec));
		long customTimeValue = dateTemp.getTimeInMillis();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h, hh=12h
		String str = df.format(customTimeValue);

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

			System.out.println("1");
			String strYear = "2015";
			String strMonth = "11";
			String strDay = "18";
			
			String strMinute = "00";
			String strSec = "00";
			String strParameter = "pressure01";
			
			currentImport = new ArrayList<Put>();

			for (int loop = 1; loop < 24; loop++) {
				
				String strHour = String.format("%02d", loop);

				String message = String.format("%s-%s-%s %s:%s:%s", strYear,
						strMonth, strDay, strHour, strMinute, strSec);

				String strYearMonthDayHour = String.format("%s%s%s%s", strYear,
						strMonth, strDay, strHour);
				
				String strStartDate = getTimeData(strYear, strMonth, strDay,
						strHour, strMinute, strSec);
				String strEndDate = getTimeData(strYear, strMonth, strDay,
						strHour, "59", "59");
				
//				String strRowKey = String.format("%s%s%s%s%s",
//						strYearMonthDayHour, "op01", strParameter,
//						strYearMonthDayHour, "5959");
				String strRowKey = String.format("%s%s%s%s",
						strYearMonthDayHour, "default", strParameter, strEndDate);

				

				// Put p = new
				// Put(Bytes.toBytes("2015111709op1param0120151117095959"));
				Put p = new Put(Bytes.toBytes(strRowKey));
				// p.addColumn(COLUMN_FAMILY, COL_NAME,
				// Bytes.toBytes("2015111000op1param0120151110005959999"));
				p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
				p.addColumn(COLUMN_FAMILY, COL_MONTH, Bytes.toBytes(strMonth));
				p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
				p.addColumn(COLUMN_FAMILY, COL_HOUR, Bytes.toBytes(strHour));
				p.addColumn(COLUMN_FAMILY, COL_OPERATION,
						Bytes.toBytes("default"));
				p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes("eqp01"));
				p.addColumn(COLUMN_FAMILY, COL_MODULE, Bytes.toBytes("chB"));
				p.addColumn(COLUMN_FAMILY, COL_START_DTTS,
						Bytes.toBytes(strStartDate)); // 2015-12-16
														// 15:00:00.000963
				p.addColumn(COLUMN_FAMILY, COL_END_DTTS,
						Bytes.toBytes(strEndDate)); // 2015-12-16
													// 15:59:59.000963
				p.addColumn(COLUMN_FAMILY, COL_PRODUCT,
						Bytes.toBytes("default"));
				p.addColumn(COLUMN_FAMILY, COL_RECIPE, Bytes.toBytes("default"));
				p.addColumn(COLUMN_FAMILY, COL_RECIPE_STEP, Bytes.toBytes("0"));
				p.addColumn(COLUMN_FAMILY, COL_PARAMETER,
						Bytes.toBytes(strParameter));

				String[] strTimeData = message.split("[\\s\\:\\-]");

				Calendar dateTemp = Calendar.getInstance();
				dateTemp.set(Integer.parseInt(strTimeData[0]),
						Integer.parseInt(strTimeData[1]),
						Integer.parseInt(strTimeData[2]),
						Integer.parseInt(strTimeData[3]),
						Integer.parseInt(strTimeData[4]),
						Integer.parseInt(strTimeData[5]));
				long customTimeValue = dateTemp.getTimeInMillis();

				int mod = 20;

				StringBuilder valueX = new StringBuilder();
				StringBuilder valueY = new StringBuilder();

				for (int index = 0; index < 3600; index++) {
					int xVlue = index % 5;
					dateTemp.set(Integer.parseInt(strTimeData[0]),
							Integer.parseInt(strTimeData[1]),
							Integer.parseInt(strTimeData[2]),
							Integer.parseInt(strTimeData[3]),
							Integer.parseInt(strTimeData[4]), index);
					customTimeValue = dateTemp.getTimeInMillis();

					valueX.append(String.valueOf(xVlue) + "."
							+ String.valueOf(xVlue) + ",");
					valueY.append(customTimeValue + ",");
				}

				// valueX = ;
				// valueY = valueY.substring(0, valueY.length()-2);

				p.addColumn(COLUMN_FAMILY, COL_VALUE,
						Bytes.toBytes(valueX.substring(0, valueX.length() - 2)));
				p.addColumn(COLUMN_FAMILY, COL_TIME,
						Bytes.toBytes(valueY.substring(0, valueY.length() - 2)));

				
				currentImport.add(p);
			}

			//System.out.println("p : " + p);

			try (Connection conn = ConnectionFactory.createConnection(config);
					Admin admin = conn.getAdmin()) {

				Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
				table.put(currentImport);

				table.close();

				// Get g = new Get(Bytes.toBytes("TheRealMT"));
				// g.addColumn(Bytes.toBytes("info"),Bytes.toBytes("name"));
				// Result _result = table.get(g);
				// System.out.println("r : "+ _result);
				// byte[]b =
				// _result.getValue(Bytes.toBytes("info"),Bytes.toBytes("name"));
				// String email = Bytes.toString(b);
				// System.out.println(email);
				//
				//
				// Scan s =new Scan();
				// ResultScanner rs = null;
				// try {
				// rs = table.getScanner(s);
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				//
				// for (Result r : rs) {
				// for (KeyValue kv : r.raw()) {
				// System.out.println("row:" + new String(kv.getRow()) +"");
				// System.out.println("family:" + new String(kv.getFamily())
				// +":");
				// System.out.println("qualifier:" + new
				// String(kv.getQualifier()) +"");
				// System.out.println("value:" + new String(kv.getValue()));
				// System.out.println("timestamp:" + kv.getTimestamp() +"");
				// System.out.println("-------------------------------------------");
				// }
				// }
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
}
