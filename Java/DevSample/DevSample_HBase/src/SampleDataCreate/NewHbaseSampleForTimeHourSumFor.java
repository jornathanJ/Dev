package SampleDataCreate;

import java.io.IOException;
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

public class NewHbaseSampleForTimeHourSumFor {
	private static String currentStock;

	private static List<Put> currentImport; // cache for bulk load

	public final static byte[] COLUMN_FAMILY = "cf_HourTraceSummary".getBytes();

	public final static byte[] TABLE_NAME = "HourTraceSummary".getBytes();

	public final static byte[] COL_TOOL = "tool".getBytes();
	public final static byte[] COL_MODULE = "module".getBytes();

	public final static byte[] COL_YEAR = "year".getBytes();
	public final static byte[] COL_MONTH = "month".getBytes();
	public final static byte[] COL_DAY = "day".getBytes();
	public final static byte[] COL_HOUR = "hour".getBytes();

	public final static byte[] COL_PARAMETER = "parameter".getBytes();
	public final static byte[] COL_RECIPE = "recipe".getBytes();

	public final static byte[] COL_START = "start_dtts".getBytes();
	public final static byte[] COL_END = "end_dtts".getBytes();

	public final static byte[] COL_W_START = "window_start".getBytes();
	public final static byte[] COL_W_END = "window_end".getBytes();
	public final static byte[] COL_W_UNIT = "window_unit".getBytes();
	public final static byte[] COL_SOURCE_PARAMETER = "source_parameters".getBytes();

	public final static byte[] COL_MIN = "min".getBytes();
	public final static byte[] COL_MAX = "max".getBytes();
	public final static byte[] COL_STD = "std".getBytes();
	public final static byte[] COL_MEAN = "mean".getBytes();

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
		// config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		// config.addResource(new Path(System.getenv("HBASE_CONF_DIR"),
		// "hbase-site.xml"));

		try {

			try (Connection connection = ConnectionFactory.createConnection(config);
					Admin admin = connection.getAdmin()) {

				HTableDescriptor table = new HTableDescriptor(TableName.valueOf(TABLE_NAME));
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

			// String strMinute = "00";
			// String strSec = "00";
			String strParameter = "PRESSURE02";
			String strSourceParameter = "trace.PRESSURE02";
			String strRecipe = "Recipe01";
			String strWindowsUnit = "time";

			String strTool = "EQP02";
			String strModule = "CHA";

			currentImport = new ArrayList<Put>();

			// eqp01 ch01 2015 11 10 00 pdm_param01_sum01 1448996694763

			int position = 0;

			Param_Macro_min _min = new Param_Macro_min();
			Param_Macro_max _max = new Param_Macro_max();
			Param_Macro_STD _std = new Param_Macro_STD();
			Param_Macro_Mean _mean = new Param_Macro_Mean();

			for (int index = 1; index <= 31; index++) {

				strDay = String.format("%02d", index);

				for (int loop = 0; loop < 24; loop++) {
					position++;

					String strStart = String.format("2015-12-%s %s:00:00", strDay, String.format("%02d", loop));
					String strEnd = String.format("2015-12-%s %s:59:59", strDay, String.format("%02d", loop));

					String startValue = String.valueOf(convertTimeStringToLong(strStart));
					String EndtValue = String.valueOf(convertTimeStringToLong(strEnd));

					// strHour = String.format("%02d", loop);
					String strRowKey = String.format("%s%s%s%s%s%s%s%s", strTool, strModule, strYear, strMonth, strDay,
							String.format("%02d", loop), strParameter, EndtValue);

					Put p = new Put(Bytes.toBytes(strRowKey));
					p.addColumn(COLUMN_FAMILY, COL_TOOL, Bytes.toBytes(strTool));
					p.addColumn(COLUMN_FAMILY, COL_MODULE, Bytes.toBytes(strModule));

					p.addColumn(COLUMN_FAMILY, COL_YEAR, Bytes.toBytes(strYear));
					p.addColumn(COLUMN_FAMILY, COL_MONTH, Bytes.toBytes(strMonth));
					p.addColumn(COLUMN_FAMILY, COL_DAY, Bytes.toBytes(strDay));
					p.addColumn(COLUMN_FAMILY, COL_HOUR, Bytes.toBytes(String.format("%02d", loop)));

					p.addColumn(COLUMN_FAMILY, COL_PARAMETER, Bytes.toBytes(strParameter));
					p.addColumn(COLUMN_FAMILY, COL_RECIPE, Bytes.toBytes(strRecipe));

					p.addColumn(COLUMN_FAMILY, COL_START, Bytes.toBytes(startValue));
					p.addColumn(COLUMN_FAMILY, COL_END, Bytes.toBytes(EndtValue));

					p.addColumn(COLUMN_FAMILY, COL_W_START, Bytes.toBytes("0"));
					p.addColumn(COLUMN_FAMILY, COL_W_END, Bytes.toBytes("0"));
					p.addColumn(COLUMN_FAMILY, COL_W_UNIT, Bytes.toBytes(strWindowsUnit));
					p.addColumn(COLUMN_FAMILY, COL_SOURCE_PARAMETER, Bytes.toBytes(strSourceParameter));

					p.addColumn(COLUMN_FAMILY, COL_MIN, Bytes.toBytes(String.format("%f", _min.min_data[position])));
					p.addColumn(COLUMN_FAMILY, COL_MAX, Bytes.toBytes(String.format("%f", _max.max_data[position])));
					p.addColumn(COLUMN_FAMILY, COL_STD, Bytes.toBytes(String.format("%f", _std.std_data[position])));
					p.addColumn(COLUMN_FAMILY, COL_MEAN, Bytes.toBytes(String.format("%f", _mean.mean_data[position])));

					currentImport.add(p);
				}
			}

			try (Connection conn = ConnectionFactory.createConnection(config); Admin admin = conn.getAdmin()) {

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
