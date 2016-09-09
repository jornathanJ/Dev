package com.spark_java.sample;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.spark.SparkConf;
//import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
//import org.apache.spark.api.java.function.Function;
//import org.apache.spark.api.java.function.PairFunction;
//
//import scala.Tuple2;

public class TransformDistinct2 {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("TransformMap")
				//.setMaster("local[*]");
				//.set("spark.shuffle.blockTransferService", "nio")
				.set("spark.akka.timeout", "2800s")
				.set("spark.akka.heartbeat.interval", "10000")
		.setMaster("spark://192.168.8.112:7077");
				//.setMaster("spark://a3-spark-master:7077");
		JavaSparkContext sc = new JavaSparkContext(conf);

		List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
		JavaRDD<Integer> x = sc.parallelize(data);
		
		List<Integer> ydata = Arrays.asList(6,7,8,9,10,11,12,13,14,15);
		JavaRDD<Integer> y = sc.parallelize(ydata);
		
		JavaRDD<Integer> z = x.union(y);

		 
		List<Integer> output = z.collect();

		for (Integer tuple : output) {
			System.out.println(tuple);
		}
		
		JavaRDD<Integer> w = z.distinct();
		
		List<Integer> output2 = z.collect();

		for (Integer tuple : output2) {
			System.out.println(tuple);
		}
		
		
		/*byte[] COLUMN_FAMILY = "cf_timeserieshsum".getBytes();

		byte[] TABLE_NAME = "timeserieshsum".getBytes();
		
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
				else{
					System.out.print("Existing table ");
				}
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				
			}
		
		}  catch (Exception es) {
			es.printStackTrace();
		}
		finally{
			
		}*/

//		sc.stop();
//		sc.close();
	}

}
