package net.onpage.data;

import net.eincs.frame.util.ClassUtil;
import net.onpage.json.annotation.JSONable;
import net.onpage.json.annotation.JSONField;
import net.onpage.json.enumeration.JSONType;

@JSONable
public class GeoInfo {

	@JSONField(Name="latitude", JsonType=JSONType.Object, JavaType=Float.class)
	private Float latitude = null;
	
	@JSONField(Name="longitude", JsonType=JSONType.Object, JavaType=Float.class)
	private Float longitude = null;

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	
	@Override
	public String toString()
	{
		return ClassUtil.getFieldValueWithClass(GeoInfo.class, this);
	}
}
