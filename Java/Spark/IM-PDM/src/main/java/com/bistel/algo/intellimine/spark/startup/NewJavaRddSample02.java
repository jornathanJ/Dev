package com.bistel.algo.intellimine.spark.startup;


/*
 * 추가로 작성해야 할 내용
 * 1) BackEnd(Trift Client에서 오는 요청을 Thrift Server에서 받아서 처리하는 방법
 *   - BackEnd에서 요청하는 Data는 mock Data를 사용하도록 합니다.
 *  
 *  2) 전달 받은 Data를 기준으로 Spark에서 해당 문자열을 RDD로 변환하는 과정
 *  
 *  3) IntelluMine 호출 결과를 JSON 형태로 변결
 *  4) IntelluMine 호출 결과를 DB에 저장
 *  
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import jodd.format.Printf;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;

import breeze.linalg.split;

import com.bistel.algo.intellimine.model.ConstFileFormat;
import com.bistel.algo.intellimine.model.IntellimineResult;
import com.bistel.algo.intellimine.model.Parameter;
import com.bistel.algo.intellimine.spark.conf.IntellimineSparkFileConfig;
import com.bistel.algo.intellimine.spark.conf.IntellimineSparkJavaRDDsConfig;
import com.bistel.algo.intellimine.spark.parser.DefaultLineParser;
import com.bistel.algo.intellimine.spark.parser.ParameterParser;
import com.bistel.algo.intellimine.spark.service.IntellimineResultService;
import com.bistel.algo.intellimine.spark.service.SingleParameterAnalysisService;
import com.bistel.algo.intellimine.spark.serviceImpl.IntellimineServiceImpl;
import com.bistel.algo.intellimine.spark.serviceImpl.SingleParameterAnalysisImplUsingMap;
import com.bistel.algo.intellimine.spark.startup.IntellimineSparkRunningBasedOnJavaRDDsConfig;
import com.bistel.algo.intellimine.spark.test.APITestSuit;
import com.codahale.metrics.MetricRegistryListener.Base;

public class NewJavaRddSample02 extends APITestSuit implements Serializable {
	private static Properties properties = new Properties();

	public static void main(String[] args) throws Exception {

		Date sm = new SimpleDateFormat("yyyyMMddHHmmss")
				.parse("20151106112000");

		contextSetUp();
		// loader(args[0]);


		//String strEffectValue = "SR	<0.842	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	20151105,NA,0.8,20151106,NA,0.9,20151107,NA,0.8,20151108,NA,0.9,20151105,NA,0.8,20151106,NA,0.9,20151107,NA,0.8,20151108,NA,0.9,";
		//String strCauseValue = "TK		017_CMP	NA	CAT	toolA	20151105,NA,0.812,20151106,NA,0.912,20151107,NA,0.812,20151108,NA,0.912,";
		
		//날자만 추가한 형태(상관 없음)
		//String strEffectValue = "SR	<0.842	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	2015112605,NA,0.8,2015112606,NA,0.9,2015112607,NA,0.8,2015112608,NA,0.9,2015112605,NA,0.8,2015112606,NA,0.9,2015112607,NA,0.8,2015112608,NA,0.9,";
	    //String strCauseValue = "TK		017_CMP	NA	CAT	toolA	2015112605,NA,0.812,2015112606,NA,0.912,2015112607,NA,0.812,2015112608,NA,0.912,";
	    
    	//날자만 추가한 형태(상관 없음) + 값을 변경
 		//String strEffectValue = "SR	<8.42	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	2015112605,NA,8,2015112606,NA,9,2015112607,NA,8,2015112608,NA,9,2015112605,NA,8,2015112606,NA,9,2015112607,NA,8,2015112608,NA,9,";
  	    //String strCauseValue = "TK		017_CMP	NA	CAT	toolA	2015112605,NA,0.812,2015112606,NA,0.912,2015112607,NA,0.812,2015112608,NA,0.912,";
		
		//String strEffectValue = "SR	<0.842	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	2015112605,NA,0.8,2015112606,NA,0.9,2015112607,NA,0.8,2015112608,NA,0.9,2015112605,NA,0.8,2015112606,NA,0.9,2015112607,NA,0.8,2015112608,NA,0.9,";
		//String strCauseValue = "TK		017_CMP	NA	CAT	toolA	2015112605,NA,0.812,2015112606,NA,0.912,2015112607,NA,0.812,2015112608,NA,0.912,2015112605,NA,0.812,2015112606,NA,0.912,2015112607,NA,0.812,2015112608,NA,0.912,";
		
		//String strEffectValue = "SR	<7.01	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	20151101000000,NA,0.00,20151101010000,NA,1.00,20151101020000,NA,2.00,20151101030000,NA,3.00,20151101040000,NA,4.00,20151101050000,NA,5.00,20151101060000,NA,6.00,20151101070000,NA,7.00,20151101080000,NA,8.00,20151101090000,NA,9.00,";
		//String strCauseValue = "TK		017_CMP	NA	CONT	toolA	20151101000000,NA,0.00,20151101010000,NA,1.00,20151101020000,NA,2.00,20151101030000,NA,3.00,20151101040000,NA,4.00,20151101050000,NA,5.00,20151101060000,NA,6.00,20151101070000,NA,7.00,20151101080000,NA,8.00,20151101090000,NA,9.00,";
		
		String strEffectValue = "SR	<12.01	Case_Study1	IntelliMine1	CONT	Case01-Yield (Tool)	2015110100,NA,0.00,2015110101,NA,1.00,2015110102,NA,2.00,2015110103,NA,3.00,2015110104,NA,4.00,2015110105,NA,5.00,2015110106,NA,6.00,2015110107,NA,7.00,2015110108,NA,8.00,2015110109,NA,9.00,2015110110,NA,10.00,2015110111,NA,11.00,2015110112,NA,12.00,2015110113,NA,13.00,2015110114,NA,14.00,2015110115,NA,15.00,2015110116,NA,16.00,2015110117,NA,17.00,2015110118,NA,18.00,2015110119,NA,19.00,2015110120,NA,20.00,2015110121,NA,21.00,2015110122,NA,22.00,2015110123,NA,23.00,2015110200,NA,0.00,2015110201,NA,1.00,2015110202,NA,2.00,2015110203,NA,3.00,2015110204,NA,4.00,2015110205,NA,5.00,2015110206,NA,6.00,2015110207,NA,7.00,2015110208,NA,8.00,2015110209,NA,9.00,2015110210,NA,10.00,2015110211,NA,11.00,2015110212,NA,12.00,2015110213,NA,13.00,2015110214,NA,14.00,2015110215,NA,15.00,2015110216,NA,16.00,2015110217,NA,17.00,2015110218,NA,18.00,2015110219,NA,19.00,2015110220,NA,20.00,2015110221,NA,21.00,2015110222,NA,22.00,2015110223,NA,23.00,";
		String strCauseValue = "TK		017_CMP	NA	CONT	pressure01_MIN	2015110100,NA,0.00,2015110101,NA,1.00,2015110102,NA,2.00,2015110103,NA,3.00,2015110104,NA,4.00,2015110105,NA,5.00,2015110106,NA,6.00,2015110107,NA,7.00,2015110108,NA,8.00,2015110109,NA,9.00,2015110110,NA,10.00,2015110111,NA,11.00,2015110112,NA,12.00,2015110113,NA,13.00,2015110114,NA,14.00,2015110115,NA,15.00,2015110116,NA,16.00,2015110117,NA,17.00,2015110118,NA,18.00,2015110119,NA,19.00,2015110120,NA,20.00,2015110121,NA,21.00,2015110122,NA,22.00,2015110123,NA,23.00,2015110200,NA,0.00,2015110201,NA,1.00,2015110202,NA,2.00,2015110203,NA,3.00,2015110204,NA,4.00,2015110205,NA,5.00,2015110206,NA,6.00,2015110207,NA,7.00,2015110208,NA,8.00,2015110209,NA,9.00,2015110210,NA,10.00,2015110211,NA,11.00,2015110212,NA,12.00,2015110213,NA,13.00,2015110214,NA,14.00,2015110215,NA,15.00,2015110216,NA,16.00,2015110217,NA,17.00,2015110218,NA,18.00,2015110219,NA,19.00,2015110220,NA,20.00,2015110221,NA,21.00,2015110222,NA,22.00,2015110223,NA,23.00,";

		
		List<String> listEffect = Arrays.asList(strEffectValue);
		List<String> listCause = Arrays.asList(strCauseValue);

		JavaRDD<String> RDDEffect = javaSparkContext.parallelize(listEffect);
		JavaRDD<String> RDDCause = javaSparkContext.parallelize(listCause);
		

		// strRDDEffect.foreach(new VoidFunction<String>() {
		// private static final long serialVersionUID = 1L;
		//
		// @Override
		// public void call(String s) {
		// System.out.println(s);
		// }
		// });
		

		JavaRDD<Parameter> effectUserDefine = RDDEffect
				.map(new Function<String, Parameter>() {
					private static final long serialVersionUID = 1L;

					public Parameter call(String line) throws Exception {
						System.out.println(line);
						DefaultLineParser defaultLineParser = new DefaultLineParser();
						return ParameterParser.convertStringToParameter(
								defaultLineParser.parse(line), true);
					}
				});

		JavaRDD<Parameter> causeUserDefine = RDDCause
				.map(new Function<String, Parameter>() {
					private static final long serialVersionUID = 1L;

					public Parameter call(String line) throws Exception {
						System.out.println(line);
						DefaultLineParser defaultLineParser = new DefaultLineParser();
						return ParameterParser.convertStringToParameter(
								defaultLineParser.parse(line), false);
					}
				});

		IntellimineSparkJavaRDDsConfig config = new IntellimineSparkJavaRDDsConfig.Builder()
				.createJavaSparkContext(javaSparkContext)
				.createCauses(causeUserDefine)
				.createEffects(effectUserDefine)
				.usedMCA(false)
				.usedMVA(false)
				.createDependencies(new String[] { "" })
				.isRemoveZeroScore(true).build();

		// Algorithm selection. In this case, we selected SPA.
		SingleParameterAnalysisService singleParameterAnalysisService = new SingleParameterAnalysisImplUsingMap(
				config);

		List<IntellimineResult> intellimineResultJavaRDD = singleParameterAnalysisService
				.getRankScore().collect();

		System.out .printf("Result----------------------------------------Total is : %d \r\n", intellimineResultJavaRDD.size());
		for (int index = 0; index < intellimineResultJavaRDD.size(); index++) {
			String sParam = intellimineResultJavaRDD.get(index) .getParameterName();
			int sRank = intellimineResultJavaRDD.get(index).getRank();
			double sScore = intellimineResultJavaRDD.get(index).getScore();

			System.out.printf("Param : %s - Rank is %d - Score is %f \r\n", sParam, sRank, sScore);
		}

		javaSparkContext.close();
	}

	// @SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	// public static JavaRDD<Parameter> getCauses(String causesPath) {
	// JavaRDD<String> causesString = javaSparkContext.textFile(causesPath);
	// return causesString.map(new Function<String, Parameter>()
	// {
	// public Parameter call(String line) throws Exception
	// {
	// DefaultLineParser defaultLineParser = new DefaultLineParser();
	// return ParameterParser.convertStringToParameter(
	// defaultLineParser.parse(line), false);
	// }
	// });
	// }

	public static void loader(String filePath) throws FileNotFoundException,
			IOException {
		properties.load(new FileInputStream(filePath));
	}

	public static String[] getArrayValue(String key) {
		List<String> temp = new ArrayList<String>();
		String[] strKey = properties.getProperty(key).split(";");
		for (String value : strKey) {
			temp.add(value);
		}
		return temp.toArray(new String[temp.size()]);
	}

	public static Boolean getBooleanValue(String key) {
		return Boolean.parseBoolean(properties.getProperty(key, "false"));
	}

	public static String getStringValue(String key) {
		return properties.getProperty(key);
	}

}
