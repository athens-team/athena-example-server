package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum FriendState {

	ALL("9"),
	PENDING("P"),
	RELATION("R"),
	REFUESD("F"),
	ETC("0");

	private static final FriendState DEFAULT_VALUE = ETC;
	private static final Map<String, FriendState> stringToEnum
		= new HashMap<String, FriendState>();
	static {
		for(FriendState friendState : values())
			stringToEnum.put(friendState.getValue(), friendState);
	}
	
	private String value = null;
	private FriendState(String value) { this.value = value; }
	public String getValue() { return value; }
	public static FriendState getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
