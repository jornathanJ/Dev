package com.bistel.algo.intellimine.spark.startup;

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

import org.apache.spark.api.java.JavaRDD;
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

public class NewJavaRddSample extends APITestSuit implements Serializable {
	private static Properties properties = new Properties();

	public static void main(String[] args) throws Exception {

		Date sm = new SimpleDateFormat("yyyyMMddHHmmss").parse("20151106112000");
		 
		contextSetUp();
		loader(args[0]);

		String strCause = getStringValue("cause.input.dir");
		String strEffect = getStringValue("effect.output.dir");

		JavaRDD<Parameter> effect = javaSparkContext.emptyRDD();
		JavaRDD<Parameter> cause = javaSparkContext.emptyRDD();

		// Case 01 -- Use RDD as JavaRDD<Parameter>
		// IntellimineSparkJavaRDDsConfig config = new
		// IntellimineSparkJavaRDDsConfig.Builder()
		// .createJavaSparkContext(javaSparkContext)
		// .createCauses(getCauses(getStringValue("cause.input.dir")))
		// .createEffects(getEffects(getStringValue("effect.output.dir")))
		// .createDependencies(new String[]{"step;step,tool"})
		// .build();

		// Case 02 -- Use RDD as FileBase so use ~~~Path ex) createCausesPath
		// IntellimineSparkFileConfig config = new
		// IntellimineSparkFileConfig.Builder()
		// .createJavaSparkContext(javaSparkContext)
		// .createDependencies(new String[]{"step;step,tool"})
		// .createCausesPath(getStringValue("cause.input.dir"))
		// .createEffectsPath(getStringValue("effect.output.dir"))
		// .isRemoveZeroScore(true)
		// .build();

		// //Case 03
		cause = getCauses(getStringValue("cause.input.dir"));
		effect = getEffects(getStringValue("effect.output.dir"));
		
		List<Parameter> res = effect.collect();
		System.out.println(res.toString());

		JavaRDD<Parameter> sampleRDD_effect = javaSparkContext.emptyRDD();
		// sampleRDD_effect.

		IntellimineSparkJavaRDDsConfig config = new IntellimineSparkJavaRDDsConfig.Builder()
				.createJavaSparkContext(javaSparkContext)
				.createCauses(cause)
				.createEffects(effect)
				// Right
				// .createCauses(getCauses(getStringValue("cause.input.dir")))
				// .createEffects(getEffects(getStringValue("effect.output.dir")))
				.usedMCA(false).usedMVA(false)
				//.createDependencies(new String[] { "step;step,tool" })
				.createDependencies(new String[] { "" })
				.isRemoveZeroScore(true).build();

		// Algorithm selection. In this case, we selected SPA.
		SingleParameterAnalysisService singleParameterAnalysisService = new SingleParameterAnalysisImplUsingMap(
				config);

		List<IntellimineResult> intellimineResultJavaRDD = singleParameterAnalysisService
				.getRankScore().collect();

		//System.out.println("Result----------------------------------------");
		System.out.printf("Result----------------------------------------Total is : %d \r\n", intellimineResultJavaRDD.size());
		for (int index = 0; index < intellimineResultJavaRDD.size(); index++) {
			String sParam = intellimineResultJavaRDD.get(index).getParameterName();
			int sRank = intellimineResultJavaRDD.get(index).getRank();
			double sScore = intellimineResultJavaRDD.get(index).getScore();
			
			System.out.printf("Param : %s - Rank is %d - Score is %f \r\n", sParam, sRank, sScore) ;
		}

		javaSparkContext.close();
	}

	
	@SuppressWarnings({ "unchecked", "rawtypes", "serial" })
	public static JavaRDD<Parameter> getCauses(String causesPath) {
		JavaRDD<String> causesString = javaSparkContext.textFile(causesPath);
		return causesString.map(new Function<String, Parameter>() 
		{
			public Parameter call(String line) throws Exception 
			{
				DefaultLineParser defaultLineParser = new DefaultLineParser();
				return ParameterParser.convertStringToParameter(
						defaultLineParser.parse(line), false);
			}
		});
	}

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
