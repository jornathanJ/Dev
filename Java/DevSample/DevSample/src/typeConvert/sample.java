package typeConvert;

public class sample {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		String value ="0";
		double dValue = Double.parseDouble(value);
		
		String sResult = String.format("%.7f", dValue);
		
		System.out.println(sResult);
		
		String doubleTest = String.format("%s%.2f", "test", 0.01);
		System.out.println(doubleTest);
		
		String strHeader = String.format("%s\t%s%.2f\t%s\t%s\t%s\t", "SR", "<", 0.01, "SampleEffect", "PDMIntelliMine", "CONT");
		
		System.out.println(strHeader);
	}

}
