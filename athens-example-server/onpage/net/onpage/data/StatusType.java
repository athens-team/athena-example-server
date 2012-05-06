package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum StatusType {
	
	USER_STATUS("U"),
	PAGE_STATUS("P"),
	INFO_STATUS("I");
	
	private static final StatusType DEFAULT_VALUE = USER_STATUS;
	private static final Map<String, StatusType>  stringToEnum
		= new HashMap<String, StatusType>();
	static {
		for(StatusType statusType : values())
			 stringToEnum.put(statusType.getValue(), statusType);
	}
	
	private String value = null;
	private StatusType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static StatusType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
