package com.spark_java.sample;

import java.util.Arrays;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

public class JSONSql2 {

	public static void main(String[] args) {

		SparkConf conf = new SparkConf().setAppName("JSONSql2").setMaster(
				"local[*]");
		JavaSparkContext sc = new JavaSparkContext(conf);
 
		SQLContext sqlContext = new SQLContext(sc);

		String path = "C:\\people.json";

		DataFrame peopleFromJsonFile = sqlContext.jsonFile(path);
		peopleFromJsonFile.show();

		peopleFromJsonFile.printSchema();

		peopleFromJsonFile.select(peopleFromJsonFile.col( "name")).show();

		peopleFromJsonFile.select(peopleFromJsonFile.col("name"), peopleFromJsonFile.col("age").plus(1)).show();
		
		peopleFromJsonFile.filter(peopleFromJsonFile.col("age").$greater(21)).show();
		peopleFromJsonFile.groupBy("age").count().show();

		peopleFromJsonFile.registerTempTable("people");

		DataFrame teenagers = sqlContext
				.sql("SELECT name FROM people WHERE age >= 13 AND age < 19");

		List<String> jsonData = Arrays
				.asList("{\"name\":\"Yin\",\"address\":{\"city\":\"Columbus\",\"state\":\"Ohio\"}}");
		JavaRDD<String> anotherPeopleRDD = sc.parallelize(jsonData);
		DataFrame peopleFromJsonRDD = sqlContext
				.jsonRDD(anotherPeopleRDD.rdd());
		peopleFromJsonRDD.select("name").show();

		peopleFromJsonRDD.saveAsParquetFile("people.parquet");

	}

}
