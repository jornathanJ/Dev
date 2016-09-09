package SampleDataCreate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class DeleteData {

	public final static byte[] COLUMN_FAMILY = "cf_HourTraceSummary".getBytes();

	public final static byte[] TABLE_NAME = "HourTraceSummary".getBytes();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Instantiating Configuration class
		Configuration config = HBaseConfiguration.create();
		config.set("hbase.zookeeper.quorum", "a3-spark-master");
		// config.set("hbase.zookeeper.quorum", "localhost");
		config.set("hbase.zookeeper.property.clientPort", "2181");
		// config.addResource(new Path(System.getenv("HBASE_CONF_DIR"),
		// "hbase-site.xml"));

		String strYear = "2015";
		String strMonth = "12";
		String strDay = "01";
		String strHour = "00";

		// String strMinute = "00";
		// String strSec = "00";
		String strParameter = "TEMP01";
		String strSourceParameter = "trace.TEMP01";
		String strRecipe = "Recipe01";
		String strWindowsUnit = "time";

		String strTool = "EQP02";
		String strModule = "CHA";

		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();

		String startTimeSting = String.format("2015-12-%s %s:00:00", strDay, strHour);
		String[] strTimeData = startTimeSting.split("[\\s\\:\\-]");

		Calendar dateTemp = Calendar.getInstance();
		dateTemp.set(Integer.parseInt(strTimeData[0]), Integer.parseInt(strTimeData[1]) - 1,
				Integer.parseInt(strTimeData[2]), Integer.parseInt(strTimeData[3]), Integer.parseInt(strTimeData[4]),
				Integer.parseInt(strTimeData[5]));
		long customTimeValue = dateTemp.getTimeInMillis();

		startCalendar.setTimeInMillis(dateTemp.getTimeInMillis());
		endCalendar.setTimeInMillis(dateTemp.getTimeInMillis());

		Param_Macro_min _min = new Param_Macro_min();

		List<Delete> _deleteList = new ArrayList<Delete>();

		for (int loop = 0; loop < _min.min_data.length; loop++)
		// for (int loop = 1; loop < 3; loop++)
		{



			startCalendar.setTimeInMillis(dateTemp.getTimeInMillis());

			startCalendar.add(Calendar.HOUR, loop);

			endCalendar.setTimeInMillis(startCalendar.getTimeInMillis());
			endCalendar.add(Calendar.MINUTE, 59 * (loop + 1));
			endCalendar.add(Calendar.SECOND, 59 * (loop + 1));

			strDay = String.format("%02d", startCalendar.get(Calendar.DAY_OF_MONTH));
			strHour = String.format("%02d", startCalendar.get(Calendar.HOUR_OF_DAY));

			// strHour = String.format("%02d", loop);
			String strRowKey = String.format("%s%s%s%s%s%s%s%s", strTool, strModule, strYear, strMonth, strDay, strHour,
					strParameter, endCalendar.getTimeInMillis());

			System.out.println(strRowKey);

			Delete delete = new Delete(Bytes.toBytes(strRowKey));
			_deleteList.add(delete);
		}

		try (Connection conn = ConnectionFactory.createConnection(config); Admin admin = conn.getAdmin()) {

			//Delete delete = new Delete(Bytes.toBytes("EQP03CHA2015120902TEMP011449604797693"));

			Table table = conn.getTable(TableName.valueOf(TABLE_NAME));
			table.delete(_deleteList);

			table.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// currentImport.clear();
		}

		System.out.println("Finish Delete");

	}

}
