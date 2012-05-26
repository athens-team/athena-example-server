/*
 * Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.eincs.athens.olympus.data;


import org.json.JSONException;
import org.json.JSONObject;

import com.eincs.athens.olympus.data.DataUtils.JConvertable;

/**
 * @author roth2520@gmail.com
 */
public class Session implements JConvertable {

	public static Session create(String uuid) {
		Session result = new Session();
		result.setUuid(uuid);
		return result;
	}
	
	public static Session create(Integer userId, String uuid,
			String tag) {
		Session result = new Session();
		result.setUserId(userId);
		result.setUuid(uuid);
		result.setTag(tag);
		return result;
	}
	
	private Integer id;
	
	private Integer userId;
	
	private String uuid;
	
	private String tag;
	
	private Long createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}
	
	public JSONObject toJSON() throws JSONException {
		JSONObject result = new JSONObject();
		result.put("id", getId());
		result.put("user_id", userId);
		result.put("uuid", getUuid());
		result.put("tag", getTag());
		result.put("created_time", getCreatedTime());
		return result;
	}


}
