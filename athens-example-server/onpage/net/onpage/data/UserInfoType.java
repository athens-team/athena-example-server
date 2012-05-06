package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum UserInfoType {
	
	EMAIL ("E"),
	GENDER ("G"),
	BIRTHDAY ("B"),
	ETC ("0");

	private static final UserInfoType DEFAULT_VALUE = ETC;
	private static final Map<String, UserInfoType> stringToEnum
		= new HashMap<String, UserInfoType>();
	static {
		for(UserInfoType infoType : values())
			stringToEnum.put(infoType.getValue(), infoType);
	}
	
	private String value = null;
	private UserInfoType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static UserInfoType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
