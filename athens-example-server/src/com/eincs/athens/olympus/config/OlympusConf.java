package com.eincs.athens.olympus.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.extended.ISO8601DateConverter;
import com.thoughtworks.xstream.io.xml.DomDriver;

public abstract class OlympusConf {
	
	private static OlympusConf CONF = null;
	
	private TokenSecretConf tokenSecret;
	
	static {
		reload();
	}
	
	public static OlympusConf getInstance(){
		return CONF;
	}
	
	public static void reload(){
		XStream stream = new XStream(new DomDriver("UTF-8"));
		stream.alias("configuration", OlympusConf.class);
		stream.alias("tokenSecret", TokenSecretConf.class);
		
		stream.registerConverter(new ISO8601DateConverter());
		try {
			CONF = (OlympusConf)stream.fromXML(new FileInputStream("./conf/pigeon.xml"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public TokenSecretConf getTokenSecret() {
		return tokenSecret;
	}

	public void setTokenSecret(TokenSecretConf tokenSecret) {
		this.tokenSecret = tokenSecret;
	}

}
