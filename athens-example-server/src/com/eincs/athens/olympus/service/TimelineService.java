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
package com.eincs.athens.olympus.service;

import java.util.List;

import net.rothlee.athens.olympus.mybatis.test.DBManager;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eincs.athens.olympus.data.DataUtils;
import com.eincs.athens.olympus.data.Post;
import com.eincs.athens.olympus.data.Range;
import com.eincs.athens.olympus.data.User;
import com.eincs.athens.olympus.db.OlympusMapper;
import com.eincs.pantheon.handler.service.simple.Bind;
import com.eincs.pantheon.handler.service.simple.SimpleService;
import com.eincs.pantheon.message.PanteonContentType;
import com.eincs.pantheon.message.PanteonRequest;
import com.eincs.pantheon.message.PanteonResponse;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/timeline", method = { "GET" })
public class TimelineService implements SimpleService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(TimelineService.class);

	@Override
	public void doServe(PanteonRequest request, PanteonResponse response)
			throws Exception {

		final Integer after = request.getParams().getParam("after",
				Integer.class);
		final Integer before = request.getParams().getParam("before",
				Integer.class);
		final Range range = Range.createRange(after, before, 64);

		final DBManager dbm = DBManager.getInstance();
		final SqlSession session = dbm.openSession();

		try {
			
			final OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			final List<Post> result = mapper.getPosts(range);
			for(Post post : result) {
				Integer userId = post.getUserId();
				if (post.getUser() == null) {
					logger.info("new post!");
					post.setUser(mapper.getUser(User.createById(userId)));
				}
			}
			final String responseString = DataUtils.toResponseString(result);
			response.setContentType(PanteonContentType.TEXT_HTML);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}
}
