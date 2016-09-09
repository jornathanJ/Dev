package com.portal.domain.pdm;

import java.util.HashMap;
import java.util.Map;

public class Event {

	private String name;
	private TimePeriod timePeriod;
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public Event() {
	}

	public Event(String name, TimePeriod timePeriod) {
		this.name = name;
		this.timePeriod = timePeriod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public TimePeriod getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(TimePeriod timePeriod) {
		this.timePeriod = timePeriod;
	}

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
