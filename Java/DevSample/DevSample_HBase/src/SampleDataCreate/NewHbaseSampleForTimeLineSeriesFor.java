package SampleDataCreate;

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

public class NewHbaseSampleForTimeLineSeriesFor {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_TimeTrace".getBytes();

	public final static byte[] TABLE_NAME = "TimeTrace".getBytes();

	// public final static byte[] COL_NAME0 = "KEY".getBytes();
	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();

	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	public final static byte[] COL_DAY = "day".getBytes();
	public final static byte[] COL_HOUR = "hour".getBytes();
	public final static byte[] COL_MINUTE = "min".getBytes();

	public final static byte[] COL_RECIPE = "recipe".getBytes();
	// public final static byte[] COL_OPERATION = "operation".getBytes();
	// public final static byte[] COL_RECIPE_STEP = "recipe_step".getBytes();
	// public final static byte[] COL_START_DTTS = "start_dtts".getBytes();
	// public final static byte[] COL_END_DTTS = "end_dtts".getBytes();
	// public final static byte[] COL_PRODUCT = "product".getBytes();

	public final static byte[] COL_PARAMETER = "parameter".getBytes();

	public final static byte[] COL_SEC = "second".getBytes();

	public final static byte[] COL_00 = "00".getBytes();
	public final static byte[] COL_01 = "01".getBytes();
	public final static byte[] COL_02 = "02".getBytes();
	public final static byte[] COL_03 = "03".getBytes();
	public final static byte[] COL_04 = "04".getBytes();
	public final static byte[] COL_05 = "05".getBytes();
	public final static byte[] COL_06 = "06".getBytes();
	public final static byte[] COL_07 = "07".getBytes();
	public final static byte[] COL_08 = "08".getBytes();
	public final static byte[] COL_09 = "09".getBytes();
	public final static byte[] COL_10 = "10".getBytes();
	public final static byte[] COL_11 = "11".getBytes();
	public final static byte[] COL_12 = "12".getBytes();
	public final static byte[] COL_13 = "13".getBytes();
	public final static byte[] COL_14 = "14".getBytes();
	public final static byte[] COL_15 = "15".getBytes();
	public final static byte[] COL_16 = "16".getBytes();
	public final static byte[] COL_17 = "17".getBytes();
	public final static byte[] COL_18 = "18".getBytes();
	public final static byte[] COL_19 = "19".getBytes();
	public final static byte[] COL_20 = "20".getBytes();
	public final static byte[] COL_21 = "21".getBytes();
	public final static byte[] COL_22 = "22".getBytes();
	public final static byte[] COL_23 = "23".getBytes();
	public final static byte[] COL_24 = "24".getBytes();
	public final static byte[] COL_25 = "25".getBytes();
	public final static byte[] COL_26 = "26".getBytes();
	public final static byte[] COL_27 = "27".getBytes();
	public final static byte[] COL_28 = "28".getBytes();
	public final static byte[] COL_29 = "29".getBytes();
	public final static byte[] COL_30 = "30".getBytes();
	public final static byte[] COL_31 = "31".getBytes();
	public final static byte[] COL_32 = "32".getBytes();
	public final static byte[] COL_33 = "33".getBytes();
	public final static byte[] COL_34 = "34".getBytes();
	public final static byte[] COL_35 = "35".getBytes();
	public final static byte[] COL_36 = "36".getBytes();
	public final static byte[] COL_37 = "37".getBytes();
	public final static byte[] COL_38 = "38".getBytes();
	public final static byte[] COL_39 = "39".getBytes();
	public final static byte[] COL_40 = "40".getBytes();
	public final static byte[] COL_41 = "41".getBytes();
	public final static byte[] COL_42 = "42".getBytes();
	public final static byte[] COL_43 = "43".getBytes();
	public final static byte[] COL_44 = "44".getBytes();
	public final static byte[] COL_45 = "45".getBytes();
	public final static byte[] COL_46 = "46".getBytes();
	public final static byte[] COL_47 = "47".getBytes();
	public final static byte[] COL_48 = "48".getBytes();
	public final static byte[] COL_49 = "49".getBytes();
	public final static byte[] COL_50 = "50".getBytes();
	public final static byte[] COL_51 = "51".getBytes();
	public final static byte[] COL_52 = "52".getBytes();
	public final static byte[] COL_53 = "53".getBytes();
	public final static byte[] COL_54 = "54".getBytes();
	public final static byte[] COL_55 = "55".getBytes();
	public final static byte[] COL_56 = "56".getBytes();
	public final static byte[] COL_57 = "57".getBytes();
	public final static byte[] COL_58 = "58".getBytes();
	public final static byte[] COL_59 = "59".getBytes();

	public static String getData() {
		// String rtnValue = "";
		StringBuilder sb = new StringBuilder();
		for (int index = 1; index <= 1024; index++) {
			double d = 0.1 * index;

			sb.append(String.format("%.1f", d));
			sb.append(",");
		}

		return sb.substring(0, sb.length() - 1);
	}

	// public final static byte[] COL_VALUE = "0".getBytes();
	// public final static byte[] COL_TIME = "0_timestamp".getBytes();

	// public final static byte[] COL_EMAIL = "email".getBytes();
	// public final static byte[] COL_PASSWORD = "password".getBytes();

	public static String getTimeData(String strYear, String strMonth,
			String strDay, String strHour, String strMinute, String strSec) {
		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strYear), Integer.parseInt(strMonth) - 1,
				Integer.parseInt(strDay), Integer.parseInt(strHour),
				Integer.parseInt(strMinute), Integer.parseInt(strSec));
		long customTimeValue = dateTemp.getTimeInMillis();

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS"); // HH=24h,
																			// hh=12h
		String str = df.format(customTimeValue);

		return Long.toString(customTimeValue);
	}

	public static void main(String[] args) {
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "a3-spark-master");
		// config.set("hbase.zookeeper.quorum", "localhost");
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
			String strDay = "26";
			String strHour = "00";

			String strMinute = "00";
			String strSec = "00";
			String strParameter = "PRESSURE01";
			String strRecipe = "Recipe01";

			String strTool = "EQP01";
			String strModule = "CHA";

			String strSecondDefault = "00,01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59";

			currentImport = new ArrayList<Put>();
			
			String strValue = getData();

			for (int hour = 0; hour < 24; hour++) {

				for (int loop = 0; loop < 60; loop++) {

					strHour = String.format("%02d", hour);
					strMinute = String.format("%02d", loop);

					String message = String.format("%s-%s-%s %s:%s:%s",
							strYear, strMonth, strDay, strHour, strMinute,
							strSec);

					String strYearMonthDayHour = String.format("%s%s%s%s",
							strYear, strMonth, strDay, strHour);

					String strStartDate = getTimeData(strYear, strMonth,
							strDay, strHour, strMinute, strSec);
					String strEndDate = getTimeData(strYear, strMonth, strDay,
							strHour, "59", "59");

					String strRowKey = String.format("%s%s%s%s%s", strTool,
							strModule, strYearMonthDayHour, strMinute,
							strParameter);

					Put p = new Put(Bytes.toBytes(strRowKey));
					p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes(strTool));
					p.addColumn(COLUMN_FAMILY, COL_MODULE,
							Bytes.toBytes(strModule));

					p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
					p.addColumn(COLUMN_FAMILY, COL_MONTH,
							Bytes.toBytes(strMonth));
					p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
					p.addColumn(COLUMN_FAMILY, COL_HOUR, Bytes.toBytes(strHour));
					p.addColumn(COLUMN_FAMILY, COL_MINUTE,
							Bytes.toBytes(strMinute));

					p.addColumn(COLUMN_FAMILY, COL_RECIPE,
							Bytes.toBytes(strRecipe));

					p.addColumn(COLUMN_FAMILY, COL_PARAMETER,
							Bytes.toBytes(strParameter));
					p.addColumn(COLUMN_FAMILY, COL_SEC,
							Bytes.toBytes(strSecondDefault));

					p.addColumn(COLUMN_FAMILY, COL_00	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_01	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_02	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_03	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_04	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_05	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_06	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_07	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_08	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_09	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_10	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_11	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_12	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_13	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_14	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_15	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_16	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_17	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_18	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_19	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_20	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_21	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_22	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_23	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_24	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_25	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_26	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_27	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_28	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_29	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_30	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_31	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_32	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_33	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_34	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_35	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_36	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_37	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_38	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_39	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_40	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_41	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_42	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_43	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_44	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_45	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_46	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_47	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_48	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_49	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_50	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_51	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_52	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_53	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_54	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_55	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_56	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_57	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_58	, Bytes.toBytes(strValue));
					p.addColumn(COLUMN_FAMILY, COL_59	, Bytes.toBytes(strValue));


					currentImport.add(p);
				}
			}

			// System.out.println("p : " + p);

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
