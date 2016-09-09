package thriftTest;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RankRequest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6075375358009758616L;
	private Double focus;
	private Integer toolId;
	private Integer moduleId;
	private String toolName = "";
	private String moduleName = "";
	private TimePeriod timePeriod;
	private List<UserEvent> userEvents = new ArrayList<UserEvent>();
	private List<SummaryParameters> summaryParameters = new ArrayList<SummaryParameters>();
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public String getToolName() {
		return toolName;
	}
	public void setToolName(String toolName) {
		this.toolName = toolName;
	}
	
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	
	/**
	 * 
	 * @return The focus
	 */
	public Double getFocus() {
		return focus;
	}

	/**
	 * 
	 * @param focus
	 *            The focus
	 */
	public void setFocus(Double focus) {
		this.focus = focus;
	}
	
	public Integer getToolId() {
	return toolId;
	}

	/**
	* 
	* @param toolId
	* The toolId
	*/
	public void setToolId(Integer toolId) {
	this.toolId = toolId;
	}

	/**
	* 
	* @return
	* The moduleId
	*/
	public Integer getModuleId() {
	return moduleId;
	}

	/**
	* 
	* @param moduleId
	* The moduleId
	*/
	public void setModuleId(Integer moduleId) {
	this.moduleId = moduleId;
	}

	/**
	 * 
	 * @return The timePeriod
	 */
	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	/**
	 * 
	 * @param timePeriod
	 *            The timePeriod
	 */
	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	/**
	 * 
	 * @return The userEvents
	 */
	public List<UserEvent> getUserEvents() {
		return userEvents;
	}

	/**
	 * 
	 * @param userEvents
	 *            The userEvents
	 */
	public void setUserEvents(List<UserEvent> userEvents) {
		this.userEvents = userEvents;
	}

	/**
	 * 
	 * @return The summaryParameters
	 */
	public List<SummaryParameters> getSummaryParameters() {
		return summaryParameters;
	}

	/**
	 * 
	 * @param summaryParameters
	 *            The summaryParameters
	 */
	public void setSummaryParameters(List<SummaryParameters> summaryParameters) {
		this.summaryParameters = summaryParameters;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}