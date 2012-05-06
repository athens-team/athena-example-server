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
package net.rothlee.athens.olympus.service;

import java.util.List;

import net.rothlee.athens.handler.codec.http.HttpContentType;
import net.rothlee.athens.handler.service.simple.Bind;
import net.rothlee.athens.handler.service.simple.SimpleService;
import net.rothlee.athens.message.AthensRequest;
import net.rothlee.athens.message.AthensResponse;
import net.rothlee.athens.olympus.data.DataUtils;
import net.rothlee.athens.olympus.data.Post;
import net.rothlee.athens.olympus.data.Range;
import net.rothlee.athens.olympus.data.User;
import net.rothlee.athens.olympus.db.OlympusMapper;
import net.rothlee.athens.olympus.mybatis.test.DBManager;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/timeline", method = { "GET" })
public class TimelineService implements SimpleService {

	@Override
	public void doServe(AthensRequest request, AthensResponse response)
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
				post.setUser(mapper.getUser(User.createById(userId)));
			}
			final String responseString = DataUtils.toResponseString(result);
			response.setContentType(HttpContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}
}
