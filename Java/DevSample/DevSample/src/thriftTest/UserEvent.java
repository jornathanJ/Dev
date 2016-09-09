package thriftTest;


import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

public class UserEvent {

private String name;
private String startDtts;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

/**
* No args constructor for use in serialization
* 
*/
public UserEvent() {
}

/**
* 
* @param name
* @param startDtts
*/
public UserEvent(String name, String startDtts) {
this.name = name;
this.startDtts = startDtts;
}

/**
* 
* @return
* The name
*/
public String getName() {
return name;
}

/**
* 
* @param name
* The name
*/
public void setName(String name) {
this.name = name;
}

/**
* 
* @return
* The startDtts
*/
public String getStartDtts() {
return startDtts;
}

public void setStartDtts(String startDtts) {
this.startDtts = startDtts;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
