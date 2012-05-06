package net.onpage.json;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import net.eincs.frame.exception.JsonException;
import net.eincs.frame.exception.ReflectException;
import net.eincs.frame.util.ClassUtil;
import net.onpage.json.annotation.JSONField;
import net.onpage.json.annotation.JSONOptional;
import net.onpage.json.enumeration.JSONType;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONConvertor {

	public Class<?> targetClass = null;
	
	public List<JsonFieldTaker> takers = null;
	
	public JSONConvertor(Class<?> clazz) throws ReflectException
	{
		targetClass = clazz;
		List<JsonFieldTaker> createdTaker = new LinkedList<JsonFieldTaker>();
		for(Field f : targetClass.getDeclaredFields())
		{
			if(f.isAnnotationPresent(JSONField.class))
			{
				JSONField anno = f.getAnnotation(JSONField.class);
				String fieldName = f.getName();
				String getterName = ClassUtil.getGetterNameFromFieldname(fieldName);
				boolean isOptional = f.getAnnotation(JSONOptional.class)!=null;
				
				try {
					Method m = targetClass.getMethod(getterName, (Class<?>[])null);
					createdTaker.add(new JsonFieldTaker(anno, m, isOptional));
				
				} catch (SecurityException e) {
					throw new ReflectException("SecurityException occurred when generating taker.", e);
				} catch (NoSuchMethodException e) {
					throw new ReflectException("NoSuchMethodException when generating taker.", e);
				}
			}
		}
		takers = Collections.unmodifiableList(createdTaker);
	}
	
	public JSONObject getJSONObject(Object obj) throws JsonException
	{
		Object targetObject = obj;
		
		JSONObject res = new JSONObject();
		for(JsonFieldTaker taker : takers)
		{
			String jsonFieldName = taker.getFieldName();
			Object value = taker.invoke(targetObject);
			boolean optional = taker.getOptional();
			JSONType jsonType = taker.getJsonType();
			Class<?> javaType = taker.getJavaType();
			
			if(value != null) {
				value = jsonType.convert(value, javaType);
			} else if(optional) {
				// ignore
			} else {
				throw new JsonException("\"" + jsonFieldName  + "\" in \""+targetClass.getName()+"\" is mandatory.");
			}
			
			try {
				if(value != null)
					res.put(jsonFieldName, value);
				
			} catch (JSONException e) {
				throw new JsonException("JSONException occurred when convert to JSONObject.", e);
			}
			
		}
		return res;
	}
	
	private class JsonFieldTaker implements Comparable<JsonFieldTaker>
	{
		private int index;
		
		private Method getter;
		
		private String fieldName;
		
		private boolean optional;
		
		private JSONType jsonType;
		
		private Class<?> javaType;
		
		public JsonFieldTaker(JSONField fieldInfo, Method getter, boolean optional)
		{
			this.index = fieldInfo.Index();
			this.getter = getter;
			this.fieldName = fieldInfo.Name();
			this.optional = optional;
			this.jsonType = fieldInfo.JsonType();
			this.javaType = fieldInfo.JavaType();
		}
		
		public int getIndex() {
			return index;
		}

		public String getFieldName() {
			return fieldName;
		}

		
		public boolean getOptional() {
			return optional;
		}
		
		public Class<?> getJavaType() {
			return javaType;
		}
		
		public JSONType getJsonType() {
			return jsonType;
		}
		
		public Object invoke(Object obj)
		{
			try {
				return getter.invoke(obj, (Object [])null);
			} catch (IllegalArgumentException e) {
				return null;
			} catch (IllegalAccessException e) {
				return null;
			} catch (InvocationTargetException e) {
				return null;
			}
		}

		@Override
		public String toString()
		{
			return this.fieldName;
		}
		
		@Override
		public int compareTo(JsonFieldTaker o) {
			return index-o.getIndex();
		}
			
	}
}
