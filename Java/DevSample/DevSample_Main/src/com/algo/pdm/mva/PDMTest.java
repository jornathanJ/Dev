package com.algo.pdm.mva;


import java.util.HashMap;
import java.util.Map;






public class PDMTest {


	//final static Logger _logger = LoggerFactory.getLogger(PDMTest.class);
	
	public void createNewModel(String ditribPath, String trainDataPath, String validDataPath) {
//		StringBuilder sbLog = new StringBuilder();
//
//		DistributionType distributionType = DistributionType.WEIBULL;
//		AbstractController pdmController = new PDMController();
//
//		double controlConfidenceLevel = 0.9973;
////		double specConfidenceLevel = 0.99999943;
////		Boolean isNormalized = true;
//		double alpha = 1.5d;
//		double beta = 0.00002d;
//		int sampleCount = -1;
//
//		Matrix matDistribData = FileUtil.loadCSVFile(ditribPath);
//		sampleCount = matDistribData.depth();
//		_logger.debug("sample count : " + sampleCount);
//
//		// #1. Get the survival function value using the Weibull distribution
//		_logger.debug("Survival function value");
//		double[] survivalFunctionValue = pdmController.getSurvivalFunctionValue(sampleCount, alpha, beta, distributionType);
//		for (double value : survivalFunctionValue) {
//			sbLog.append(value + ", " );
//		}
//		_logger.debug("Values[" + sbLog.toString() + "]");
//		_logger.debug("Survival function value length [" + survivalFunctionValue.length + "]");
//
//		
//		// #2. Get the model through training data 
//		double variancePercentage = 0.3;
//		Matrix matTrainData = FileUtil.loadCSVFile(trainDataPath);
//		Map<LimitType, Double> limitConfidenceLevelMap = new HashMap<LimitType, Double>();
//		limitConfidenceLevelMap.put(LimitType.CONTROL, controlConfidenceLevel);
//		CommonModel commonModel = pdmController.buildModel(variancePercentage, matTrainData.getData(), limitConfidenceLevelMap);
//		_logger.debug("CommonModel[" + commonModel.toString() + "]");
//		
//		// #2. Get the validation data through the model
//		int forecastLength = 30;
//		FittingType fittingType = FittingType.POLYNOMIAL;
//		Matrix matValidData = FileUtil.loadCSVFile(validDataPath);		
//		PredictModel predictModel = pdmController.predictHealth(commonModel, matValidData.getData(), forecastLength, fittingType);
//		_logger.debug("PredictModel[" + predictModel.toString() + "]");
//		
//		for(int index = 0; index < predictModel.getValue().length; index++){
//			System.out.println(String.format("%f", predictModel.getHealthFit()[index]));
//		}
	}
	

	
	@SuppressWarnings({ "deprecation", "static-access" })
	public static void main(String[] args) {
//		CommandLineParser parser = new DefaultParser();
//		CommandLine cmd = null;
//		PDMTest pdmTest = new PDMTest();
//		Options options = new Options();
//		options.addOption( OptionBuilder.withArgName("actionvalue")
//				.hasArg()
//				.withDescription("new=>createNewModel, exist=>runExistModel")
//				.create("action"));
//		
//		options.addOption( OptionBuilder.withArgName("distributionFilePath")
//				.hasArg()
//				.withDescription("This is file path of test data for distribution calculation.")
//				.create("dpath"));
//		
//		options.addOption( OptionBuilder.withArgName("trainingFilePath")
//				.hasArg()
//				.withDescription("This is file path of test data for training the model.")
//				.create("tpath"));
//		
//		options.addOption( OptionBuilder.withArgName("validationFilePath")
//				.hasArg()
//				.withDescription("This is file path of test data for validation of model.")
//				.create("vpath"));
//
//		
//		try {
//			String distribPath = null;
//			String trainingPath = null;
//			String validationPath = null;
//			
//			cmd = parser.parse( options, args);
//			if ( cmd.hasOption("action") ) {
//				
//				if ( cmd.hasOption("dpath") && cmd.hasOption("tpath") && cmd.hasOption("vpath") ) {
//					distribPath = cmd.getOptionValue("dpath");
//					trainingPath = cmd.getOptionValue("tpath");
//					validationPath = cmd.getOptionValue("vpath");
//				}
//				else {
//					pdmTest.printHelp(options);
//				}
//				
//				String action = cmd.getOptionValue("action");
//				if ( "new".equals(action) ) {
//					pdmTest.createNewModel(distribPath, trainingPath, validationPath); //new model
//				}
////				else if ("adjust".equals(action) ) {
////					pcaTest.testAdjustModel(paramPath); //control limit manual
////				}
////				else if ("exist".equals(action) ) {
////					pcaTest.runExistModel(modelPath, paramPath); // existing single processing model
////				}
////				else if ( "batch".equals(action) ) {
////					pcaTest.runBatchExistModel(modelPath, paramPath); // existing batch processing model
////				}
//				else {}
//			}
//			else {
//				pdmTest.printHelp(options);
//			}
//		}
//		catch ( Exception e ) {
//			e.printStackTrace();
//		}
	}


	
	
//	public void printHelp( Options options ) {
//		HelpFormatter formatter = new HelpFormatter();
//		formatter.printHelp( "FremontPCATest", options );
//		
//		System.exit(-1);
//	}
}
