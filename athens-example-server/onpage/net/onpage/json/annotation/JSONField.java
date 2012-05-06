package net.onpage.json.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import net.onpage.json.enumeration.JSONType;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface JSONField {
	String Name();
	JSONType JsonType();
	Class<?> JavaType();
	int Index() default 0;
}
