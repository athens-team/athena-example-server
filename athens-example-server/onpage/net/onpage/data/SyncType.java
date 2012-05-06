package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum SyncType {
	
	FACEBOOK ("FB"),
	TWITTER ("TW"),
	KOREAPAS ("KP"),
	UNKNOWN ("UK");
	
	private static final SyncType DEFAULT_VALUE = UNKNOWN;
	private static final Map<String, SyncType> stringToEnum
		= new HashMap<String, SyncType>();
	static {
		for(SyncType infoType : values())
			stringToEnum.put(infoType.getValue(), infoType);
	}
	
	private String value = null;
	private SyncType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static SyncType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
