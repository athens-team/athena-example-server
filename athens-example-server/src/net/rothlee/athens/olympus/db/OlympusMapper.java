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
package net.rothlee.athens.olympus.db;

import java.util.List;

import net.rothlee.athens.olympus.data.Comment;
import net.rothlee.athens.olympus.data.Device;
import net.rothlee.athens.olympus.data.Post;
import net.rothlee.athens.olympus.data.User;

/**
 * @author roth2520@gmail.com
 */
public interface OlympusMapper {

	public int insertUser(User user);
	
	public int deleteUser(String userId);
	
	public int updateUser(String userId, User user);
	
	public User getUser(String userId);
	
	public User getUserByEmail(String emailAddr);
	
	
	public int insertPost(Post post);
	
	public int deletePost(String postId);
	
	public Post getPost(String postId);
	
	public List<Post> getPost(String afterId, int limit);
	
	
	public int insertComment(Comment comment);
	
	public int deleteComment(String commentId);
	
	public Comment getComment(String commentId);
	
	public List<Comment> getComment(String postId, String afterId, int limit);
	
	
	public int insertDevice(Device device);
	
	public int deleteDevice(String deviceId);
	
	public List<Device> getDeviceByUser(String userId);

}
