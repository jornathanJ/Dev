package com.bistel.algo.intellimine.spark.caseStudyTest;

import com.bistel.algo.intellimine.model.IntellimineResult;
import com.bistel.algo.intellimine.model.json.NormalResults;
import com.bistel.algo.intellimine.spark.conf.IntellimineSparkFileConfig;
import com.bistel.algo.intellimine.spark.service.NestedParameterAnalysis;
import com.bistel.algo.intellimine.spark.service.SingleParameterAnalysisService;
import com.bistel.algo.intellimine.spark.serviceImpl.NestedParameterAnalysisImplUsingMap;
import com.bistel.algo.intellimine.spark.serviceImpl.SingleParameterAnalysisImplUsingCartesian;
import com.bistel.algo.intellimine.spark.serviceImpl.SingleParameterAnalysisImplUsingMap;
import com.bistel.algo.intellimine.spark.test.APITestSuit;

import org.apache.spark.api.java.JavaRDD;
//import org.hamcrest.core.Is;
//import org.junit.BeforeClass;
//import org.junit.Test;

import scala.sys.SystemProperties;

import java.io.Serializable;
import java.util.List;

//import static org.hamcrest.CoreMatchers.is;
//import static org.junit.Assert.assertNotNull;
//import static org.junit.Assert.assertThat;
//import static org.junit.Assert.fail;

/**
 * Created by jacob on 9/7/15.
 */
public class Case02_Yield_Tool_Test extends APITestSuit implements Serializable {
    private IntellimineSparkFileConfig config;
    private SingleParameterAnalysisService singleParameterAnalysis;

    //@BeforeClass
    public static void setUp() throws Exception {
        contextSetUp();
    }

    //@Test
    public void testGetIntellimineResult() throws Exception {
        config = new IntellimineSparkFileConfig.Builder()
                .createJavaSparkContext(javaSparkContext)
                .createDependencies(new String[]{"step;step,tool"})
                .createCausesPath("src/test/resources/com.bistel.algo.intellimine.spark/caseStudyTest/Case02_Yield_Tool_Test/cause")
                .createEffectsPath("src/test/resources/com.bistel.algo.intellimine.spark/caseStudyTest/Case02_Yield_Tool_Test/effect")
                .isRemoveZeroScore(true)
                .build();


        singleParameterAnalysis = new SingleParameterAnalysisImplUsingMap(config);

        List<IntellimineResult> intellimineResultJavaRDD = singleParameterAnalysis.getRankScore().collect();
        //intellimineResultJavaRDD.size();
        
        for(int index = 0; index < intellimineResultJavaRDD.size(); index++){
        	System.out.println(intellimineResultJavaRDD.get(index).getScore()) ;
        	System.out.println(intellimineResultJavaRDD.get(index).getParameterName()) ;
        }
        
//        assertNotNull(singleParameterAnalysis);
//
//        JavaRDD<NormalResults> normalResultsJavaRDD = singleParameterAnalysis.getIntellimineResult();
//        assertThat("Is equal", (int) normalResultsJavaRDD.count(), Is.is(1));
//        assertThat("Vaildated the name of cause",normalResultsJavaRDD.collect().get(0).getCauseName(), is("tool"));
//        assertThat("Vaildated the value of candidate", normalResultsJavaRDD.collect().get(0).getCandidateValues()[0], is("cmp_c3"));
//        assertThat("Validataion Check for score", normalResultsJavaRDD.collect().get(0).getScore(), Is.is(0.9028735361376893));
    }
}