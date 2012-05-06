package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum AttachInfoType {

	INFO("I"),
	PAGE("P");

	private static final AttachInfoType DEFAULT_VALUE = INFO;
	private static final Map<String, AttachInfoType> stringToEnum
		= new HashMap<String, AttachInfoType>();
	static {
		for(AttachInfoType value : values())
			stringToEnum.put(value.getValue(), value);
	}
	
	private String value = null;
	private AttachInfoType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static AttachInfoType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
