package net.onpage.json;

import net.eincs.frame.exception.ReflectException;
import net.eincs.frame.factory.CachedObjectFactory;

public class JSONConvertorFactory
	extends CachedObjectFactory<Class<?>, JSONConvertor> {

	private static final int DEFAULT_CACHE_SIZE = 100;
	
	private static JSONConvertorFactory instance = null;
	
	private JSONConvertorFactory(int cacheSize) {
		super(cacheSize);
	}
	
	public static JSONConvertorFactory getInstance()
	{
		if(instance == null) {
			synchronized(JSONConvertorFactory.class) {
				if(instance == null) {
					instance = new JSONConvertorFactory(DEFAULT_CACHE_SIZE);
				}
			}
		}
		return instance;
	}

	@Override
	protected JSONConvertor createInstance(Class<?> clazz) {

		JSONConvertor convertor;
		try {
			convertor = new JSONConvertor(clazz);
		} catch (ReflectException e) {
			convertor = null;
		}
		return convertor;
	}

	
}
