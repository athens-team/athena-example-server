package net.onpage.data;

import java.util.HashMap;
import java.util.Map;

public enum PageType {
	
	PAGE("0"),
	PLACE("P"), // 장소
	GROUP("G"), // 모임
	CONCEPT("C"), // 개념
	INFORMATION("I"); // 정보
	
	private static final PageType DEFAULT_VALUE = PAGE;
	private static final Map<String, PageType>  stringToEnum
		= new HashMap<String, PageType>();
	static {
		for(PageType pageType : values())
			 stringToEnum.put(pageType.getValue(), pageType);
	}
	
	private String value = null;
	private PageType(String value) { this.value = value; }
	public String getValue() { return value; }
	public static PageType getValueOf(String value)
	{
		if(stringToEnum.containsKey(value))
			return stringToEnum.get(value);
		
		return DEFAULT_VALUE;
	}
}
