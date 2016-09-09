package com.bistel.algo.intellimine.spark.startup;

import com.bistel.algo.core.util.StopWatch;
import com.bistel.algo.intellimine.spark.conf.IntellimineSparkJavaRDDsConfig;
import com.bistel.algo.intellimine.spark.service.SingleParameterAnalysisService;
import com.bistel.algo.intellimine.spark.serviceImpl.SingleParameterAnalysisImplUsingMap;
import com.bistel.algo.intellimine.spark.test.APITestSuit;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


/**
 * The Class IntellimineRunning.
 */
public class IntellimineSparkRunningBasedOnJavaRDDsConfig extends APITestSuit {

    /**
     * The Constant logger.
     */
    private static final Logger logger = Logger.getLogger(IntellimineSparkRunningBasedOnJavaRDDsConfig.class);
    private static Properties properties = new Properties();

    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        logger.info("Starting Intellimine Algorithm...");
        if (args.length != 2) {
            System.out.printf("Usage : <intellimine.conf> <output>\n");
            System.exit(-1);
        }

        // File Loader.
        loader(args[0]);


        
        // Contexts SetUp
        //contextSetUp("Spark-IntellimineService");

        contextSetUp();

        // Define the configuration based on file to run algorithm.
        IntellimineSparkJavaRDDsConfig config = new IntellimineSparkJavaRDDsConfig.Builder()
                .createJavaSparkContext(javaSparkContext)
                .createCauses(getCauses(getStringValue("cause.input.dir")))
                .createEffects(getEffects(getStringValue("effect.input.dir")))
                .createDependencies(getArrayValue("dependencies"))
                .build();


        StopWatch sw = new StopWatch("IntellimineService");
        sw.start("SingleParameterAnalysisService");
        SingleParameterAnalysisService singleParameterAnalysisService = new SingleParameterAnalysisImplUsingMap(config);

        //singleParameterAnalysisService.getIntellimineResult().saveAsTextFile(args[1]);
        sw.stop();

        System.out.println(sw.prettySecPrint());
        logger.info("Complete.");
    }


    public static void loader(String filePath) throws FileNotFoundException, IOException {
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
