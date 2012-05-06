package net.onpage.json.enumeration;

import java.util.List;

import net.eincs.frame.exception.JsonException;
import net.onpage.json.JSONConvertor;
import net.onpage.json.JSONConvertorFactory;
import net.onpage.json.annotation.JSONable;

import org.json.JSONArray;

public enum JSONType {
	
	Object{
		@Override
		public Object convert(Object obj, Class<?> javaType) throws JsonException {
			Object res = obj;
			boolean needsConvert = javaType.isAnnotationPresent(JSONable.class);
			if(needsConvert) {
				JSONConvertor convertor = JSONConvertorFactory.getInstance().getInstance(javaType);
				res = convertor.getJSONObject(obj);
			}
			return res;
		}
		
	},
	Array{
		@Override
		public Object convert(Object obj, Class<?> javaType) throws JsonException {
			JSONArray jsonArr = new JSONArray();
			if(obj instanceof List<?>)  {
				List<?> list = (List<?>) obj;
				for(Object item : list) {
					Object value = item;
					boolean needsConvert = javaType.isAnnotationPresent(JSONable.class);
					if(needsConvert) {
						JSONConvertor convertor = JSONConvertorFactory.getInstance().getInstance(javaType);
						item = convertor.getJSONObject(obj);
					}
					jsonArr.put(value);
				}
			}
			return jsonArr;
		}
		
	};
	
	public abstract Object convert(Object obj, Class<?> target) throws JsonException;
}
