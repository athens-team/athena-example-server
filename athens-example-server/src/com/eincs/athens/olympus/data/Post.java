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


import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import com.eincs.athens.olympus.data.DataUtils.JConvertable;

/**
 * @author roth2520@gmail.com
 */
public class Post implements JConvertable, Serializable {

	private static final long serialVersionUID = -6795933301646666997L;

	public static Post create(Integer postId) {
		Post result = new Post();
		result.setId(postId);
		return result;
	}
	
	public static Post create(Integer userId, String content) {
		Post result = new Post();
		result.setUserId(userId);
		result.setContent(content);
		return result;
	}
	
	private Integer id;
	
	private Integer userId;
	
	private User user;
	
	private String content;
	
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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
		result.put("user_id", getUserId());
		result.put("user", getUser().toJSON());
		result.put("content", getContent());
		result.put("created_time", getCreatedTime());
		return result;
	}

}
