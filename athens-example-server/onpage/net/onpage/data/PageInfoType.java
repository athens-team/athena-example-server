package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum PageInfoType {
	PENDING("P"),
	RELATION("R"),
	REFUESD("F"),
	ETC("0");

	private static final PageInfoType DEFAULT_VALUE = ETC;
	private static final Map<String, PageInfoType> stringToEnum
		= new HashMap<String, PageInfoType>();
	static {
		for(PageInfoType infoType : values())
			stringToEnum.put(infoType.getValue(), infoType);
	}
	
	private String value = null;
	private PageInfoType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static PageInfoType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
