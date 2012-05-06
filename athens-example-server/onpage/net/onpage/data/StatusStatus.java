package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum StatusStatus {
	
	VALID("V"),
	OVERDATED("O");
	
	private static final StatusStatus DEFAULT_VALUE = VALID;
	private static final Map<String, StatusStatus>  stringToEnum
		= new HashMap<String, StatusStatus>();
	static {
		for(StatusStatus statusStatus: values())
			 stringToEnum.put(statusStatus.getValue(), statusStatus);
	}
	
	private String value = null;
	private StatusStatus(String value) { this.value = value; }
	public String getValue() { return value; }
	public static StatusStatus getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
