package thriftTest;

import java.util.HashMap;
import java.util.Map;




public class SummaryParameters {

	private String parameter;
	private String summaryCategory;
	private String summaryType;
	
	public SummaryParameters() {
	}
	
	public SummaryParameters(String _parameter, String _summaryCategory, String _summaryType ) {
		parameter = _parameter;
		summaryCategory = _summaryCategory;
		summaryType = _summaryType;
	}
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The parameter
	 */
	public String getParameter() {
		return parameter;
	}

	/**
	 * 
	 * @param parameter
	 *            The parameter
	 */
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	/**
	 * 
	 * @return The summaryCategory
	 */
	public String getSummaryCategory() {
		return summaryCategory;
	}

	/**
	 * 
	 * @param summaryCategory
	 *            The summaryCategory
	 */
	public void setSummaryCategory(String summaryCategory) {
		this.summaryCategory = summaryCategory;
	}

	/**
	 * 
	 * @return The summaryType
	 */
	public String getSummaryType() {
		return summaryType;
	}

	/**
	 * 
	 * @param summaryType
	 *            The summaryType
	 */
	public void setSummaryType(String summaryType) {
		this.summaryType = summaryType;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
