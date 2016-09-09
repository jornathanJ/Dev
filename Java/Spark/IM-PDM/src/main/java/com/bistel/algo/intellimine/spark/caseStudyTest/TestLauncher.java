package com.bistel.algo.intellimine.spark.caseStudyTest;

import com.bistel.algo.intellimine.spark.conf.IntellimineSparkFileConfig;
import com.bistel.algo.intellimine.spark.service.SingleParameterAnalysisService;


public class TestLauncher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		try {
			
			Case02_Yield_Tool_Test t = new Case02_Yield_Tool_Test();
			Case02_Yield_Tool_Test.setUp();
			t.testGetIntellimineResult();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
