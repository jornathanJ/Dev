package com.test.sample;

import java.io.IOException;
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

public class NewHbaseSampleForTimeHourSumFor {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_DayTraceSummary".getBytes();

	public final static byte[] TABLE_NAME = "DayTraceSummary".getBytes();

	// public final static byte[] COL_NAME0 = "KEY".getBytes();
	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();
	
	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	public final static byte[] COL_DAY = "day".getBytes();
	//public final static byte[] COL_HOUR = "hour".getBytes();
	
	//public final static byte[] COL_OPERATION = "operation".getBytes();
//	public final static byte[] COL_START_DTTS = "start_dtts".getBytes();
	//public final static byte[] COL_END_DTTS = "end_dtts".getBytes();
	//public final static byte[] COL_PRODUCT = "product".getBytes();
	public final static byte[] COL_RECIPE = "recipe".getBytes();
	public final static byte[] COL_PARAMETER = "parameter".getBytes();
	//public final static byte[] COL_RECIPE_STEP = "recipe_step".getBytes();
	
	public final static byte[] COL_VALUE_windows_start="window_start".getBytes();
	public final static byte[] COL_VALUE_windows_end="window_end".getBytes();
	public final static byte[] COL_VALUE_windows_unit="window_unit".getBytes();
	
	public final static byte[] COL_VALUE_MIN	="min".getBytes();
	public final static byte[] COL_VALUE_MAX="max".getBytes();
	public final static byte[] COL_VALUE_STD="std".getBytes();
	public final static byte[] COL_VALUE_MEAN="mean".getBytes();
	
	

	public static String getTimeData(String strYear, String strMonth,
			String strDay, String strHour, String strMinute, String strSec) {
		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strYear), Integer.parseInt(strMonth)-1,
				Integer.parseInt(strDay), Integer.parseInt(strHour),
				Integer.parseInt(strMinute), Integer.parseInt(strSec));
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
			String strDay = "30";
			String strHour = "00";

			String strMinute = "00";
			String strSec = "00";
			String strParameter = "PRESSURE01";
			String strRecipe = "Recipe01";

			String strTool = "EQP02";
			String strModule = "CHA";
			
			String strWindow_Start= "window_start";
			String strWindow_End = "window_end";
			String strWindow_Unit = "window_unit";
			
			currentImport = new ArrayList<Put>();

			for (int loop = 6; loop <= 7; loop++) {
				
				strDay = String.format("%02d", loop);
				String strRowKey = String.format("%s%s%s%s%s%s",
						strTool, strModule, strYear, strMonth , strDay, strParameter);

				
				
				Put p = new Put(Bytes.toBytes(strRowKey));
				p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes(strTool));
				p.addColumn(COLUMN_FAMILY, COL_MODULE, Bytes.toBytes(strModule));
				p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
				p.addColumn(COLUMN_FAMILY, COL_MONTH, Bytes.toBytes(strMonth));
				p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
				//p.addColumn(COLUMN_FAMILY, COL_HOUR, Bytes.toBytes(strHour));
				p.addColumn(COLUMN_FAMILY, COL_RECIPE, Bytes.toBytes(strRecipe));
				p.addColumn(COLUMN_FAMILY, COL_PARAMETER, Bytes.toBytes(strParameter));
				
				p.addColumn(COLUMN_FAMILY, COL_VALUE_windows_start, Bytes.toBytes(strParameter));
				p.addColumn(COLUMN_FAMILY, COL_VALUE_windows_end, Bytes.toBytes(strParameter));
				p.addColumn(COLUMN_FAMILY, COL_VALUE_windows_unit, Bytes.toBytes(strParameter));
				
				String strValueMin = String.format("%.3f", 10 + 0.1*loop);
				String strValueMax = String.format("%.3f", 90 + 0.1*loop);
				String strValueSTD = String.format("%.3f", 50 + 0.1*loop + 0.01*loop);
				String strValueMEAN = String.format("%.3f", 65 + 0.1*loop);
				
				p.addColumn(COLUMN_FAMILY, COL_VALUE_MIN, Bytes.toBytes(strValueMin));
				p.addColumn(COLUMN_FAMILY, COL_VALUE_MAX, Bytes.toBytes(strValueMax));
				p.addColumn(COLUMN_FAMILY, COL_VALUE_STD, Bytes.toBytes(strValueSTD));
				p.addColumn(COLUMN_FAMILY, COL_VALUE_MEAN, Bytes.toBytes(strValueMEAN));
				
				currentImport.add(p);
			}


			try (Connection conn = ConnectionFactory.createConnection(config);
					Admin admin = conn.getAdmin()) {

				Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
				table.put(currentImport);

				table.close();

				
			} catch (Exception es) {
				es.printStackTrace();
			}finally {
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
