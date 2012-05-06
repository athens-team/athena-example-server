package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum PrivacyType {
	
	PUBLIC("0"),
	PRIVATE("1"),
	FRIEND_ONLY("2");

	private static final PrivacyType DEFAULT_VALUE = PRIVATE;
	private static final Map<String, PrivacyType> stringToEnum
		= new HashMap<String, PrivacyType>();
	static {
		for(PrivacyType privacyType : values())
			stringToEnum.put(privacyType.getValue(), privacyType);
	}
	
	private String value = null;
	PrivacyType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static PrivacyType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
