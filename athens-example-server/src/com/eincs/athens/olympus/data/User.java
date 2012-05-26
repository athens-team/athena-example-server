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
public class User implements JConvertable {

	public static User createById(Integer id) {
		User result = new User();
		result.setId(id);
		return result;
	}
	
	public static User createByEmail(String email) {
		User result = new User();
		result.setEmailAddr(email);
		return result;
	}
	
	public static User create(String email, String nickname) {
		User result = new User();
		result.setEmailAddr(email);
		result.setNickname(nickname);
		return result;
	}
	
	private Integer id;
	
	private String profile;
	
	private String emailAddr;
	
	private String nickname;
	
	private Long createdTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
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
		result.put("profile", getProfile());
		result.put("email_addr", getEmailAddr());
		result.put("nickname", getNickname());
		result.put("created_time", getCreatedTime());
		return result;
	}
}
