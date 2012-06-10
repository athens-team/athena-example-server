/*
 * Copyright 2012 Athens Team
 * 
 * This file to you under the Apache License, version 2.0 (the "License"); you
 * may not use this file except in compliance with the License. You may obtain a
 * copy of the License at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.eincs.athens.olympus.conf;

import com.eincs.pantheon.utils.config.AbstractXStreamConfig;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("olympusConf")
public class OlympusConf extends AbstractXStreamConfig {

	private static final String CONF_NAME = "./conf/athens-olympus.conf";
	
	public static final OlympusConf INSTANCE = new OlympusConf();
	
	public OlympusConf() {
		super(CONF_NAME);
		load();
	}
	
	private int listenPort = 8080;
	private boolean useAnalyzer = false;
	
	public int getListenPort() {
		return listenPort;
	}

	public void setListenPort(int listenPort) {
		this.listenPort = listenPort;
	}
	
	
	public boolean isUseAnalyzer() {
		return useAnalyzer;
	}

	public void setUseAnalyzer(boolean useAnalyzer) {
		this.useAnalyzer = useAnalyzer;
	}

	public static void main(String[] args) {
		OlympusConf conf = new OlympusConf();
		conf.setListenPort(8080);
		conf.setUseAnalyzer(false);
		conf.save();
	}

}
