package com.algo.pdm.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class FileUtil {

//	public static Matrix loadCSVFile(String filePathName) {
//		String line = null;
//		Matrix matrix = null;
//		int colSize = -1;
//		List<double[]> dList = new ArrayList<double[]>();
//		BufferedReader br = null;
//		int i = 0;
//		int lineNo = 0;
//
//		try {
//			br = new BufferedReader (new  InputStreamReader (new FileInputStream(filePathName)));
//
//			while ( (line = br.readLine()) !=null ) {
//				if ( (lineNo++) == 0 ) continue;
//
//				String[] values = line.split(",");
//				double[] dValues = new double[values.length];
//				i = 0;
//				for ( String val : values) {
//					dValues[i++] = Double.parseDouble(val);
//				}
//				dList.add(dValues);
//				colSize = values.length;
//			}
//			double[][] matDoubles = new double[dList.size()][colSize];
//
//			i = 0;
//			for ( double[] dTemp : dList ) {
//				matDoubles[i++] = dTemp;
//			}
//			matrix = new Matrix( matDoubles );
//
//			br.close();
//		}
//		catch ( Exception e ) {
//			e.printStackTrace();
//		}
//		return matrix;
//	}

	
	
	public static String loadXMLFile(String filePathName) {
		BufferedReader br = null;
		String line = "";
		StringBuilder sb = new StringBuilder();
		
		try {
			br = new BufferedReader (new  InputStreamReader (new FileInputStream(filePathName)));

			while ( (line = br.readLine()) !=null ) {
				sb.append(line);
			}
		}
		catch ( Exception e ) {
			e.printStackTrace();
		}
		finally {
			if ( br != null ) try { br.close(); } catch ( Exception e ) {}			
		}
		return sb.toString();
	}

}
