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

public class OLD_NewHbaseSampleForTimeDaySumFor {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_DayTraceSummary".getBytes();

	public final static byte[] TABLE_NAME = "DayTraceSummary".getBytes();

	// public final static byte[] COL_NAME0 = "KEY".getBytes();
	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();
	
	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	//public final static byte[] COL_DAY = "day".getBytes();
	//public final static byte[] COL_HOUR = "hour".getBytes();
	
	public final static byte[] COL_RECIPE = "recipe".getBytes();
	public final static byte[] COL_PARAMETER = "parameter".getBytes();

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
			String strMonth = "11";
			String strDay = "25";
			String strHour = "00";

//			String strMinute = "00";
//			String strSec = "00";
			String strParameter = "PRESSURE01";
			String strRecipe = "Recipe01";

			String strTool = "EQP01";
			String strModule = "CHC";
			
			currentImport = new ArrayList<Put>();

			for (int loop = 1; loop <= 12; loop++) {
				
				//strHour = String.format("%02d", loop);
				String strRowKey = String.format("%s%s%s%s%s",
						strTool, strModule, strYear, String.format("%02d", loop), strParameter);

				Put p = new Put(Bytes.toBytes(strRowKey));
				p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes(strTool));
				p.addColumn(COLUMN_FAMILY, COL_MODULE, Bytes.toBytes(strModule));
				p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
				p.addColumn(COLUMN_FAMILY, COL_MONTH, Bytes.toBytes(strMonth));
				//p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
				p.addColumn(COLUMN_FAMILY, COL_RECIPE, Bytes.toBytes(strRecipe));
				p.addColumn(COLUMN_FAMILY, COL_PARAMETER, Bytes.toBytes(strParameter));
				
				for(int colIdx = 1; colIdx <= 31; colIdx++ ){
					String strValue = String.format("%d", colIdx);
					String strColName = String.format("%s_%s", String.format("%02d", colIdx), "min");
					p.addColumn(COLUMN_FAMILY, strColName.getBytes(), Bytes.toBytes(strValue));
				}
				
				for(int colIdx = 1; colIdx <= 31; colIdx++ ){
					String strValue = String.format("%d", colIdx);
					String strColName = String.format("%s_%s", String.format("%02d", colIdx), "max");
					p.addColumn(COLUMN_FAMILY, strColName.getBytes(), Bytes.toBytes(strValue));
				}
				
				for(int colIdx = 1; colIdx <= 31; colIdx++ ){
					String strValue = String.format("%d", colIdx);
					String strColName = String.format("%s_%s", String.format("%02d", colIdx), "std");
					p.addColumn(COLUMN_FAMILY, strColName.getBytes(), Bytes.toBytes(strValue));
				}
				
				for(int colIdx = 1; colIdx <= 31; colIdx++ ){
					String strValue = String.format("%d", colIdx);
					String strColName = String.format("%s_%s", String.format("%02d", colIdx), "mean");
					p.addColumn(COLUMN_FAMILY, strColName.getBytes(), Bytes.toBytes(strValue));
				}
				
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
}
