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
package com.eincs.athens.olympus.db;

import java.util.List;

import com.eincs.athens.olympus.data.Post;
import com.eincs.athens.olympus.data.Range;
import com.eincs.athens.olympus.data.Session;
import com.eincs.athens.olympus.data.User;


/**
 * @author roth2520@gmail.com
 */
public interface OlympusMapper {

	public int insertUser(User user);
	
	public int deleteUser(User user);
	
	public int updateUser(User user);
	
	public User getUser(User user);
	
	public User getUserByEmail(User user);
	
	
	public int insertSession(Session session);
	
	public Session getSessionByUUID(Session session);
	
	
	public int insertPost(Post post);
	
	public int deletePost(Post post);
	
	public List<Post> getPosts(Range range);


}