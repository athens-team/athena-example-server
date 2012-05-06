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

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

import net.rothlee.athens.handler.codec.http.HttpContentType;
import net.rothlee.athens.handler.service.simple.Bind;
import net.rothlee.athens.handler.service.simple.SimpleService;
import net.rothlee.athens.message.AthensRequest;
import net.rothlee.athens.message.AthensResponse;
import net.rothlee.athens.olympus.data.DataUtils;
import net.rothlee.athens.olympus.data.Post;
import net.rothlee.athens.olympus.db.OlympusMapper;
import net.rothlee.athens.olympus.mybatis.test.DBManager;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/delete", method = { "GET" })
public class PostDeleteService implements SimpleService {

	@Override
	public void doServe(AthensRequest request, AthensResponse response)
			throws Exception {
		final Integer postId = request.getParams()
				.getParam("id", Integer.class);
		final DBManager dbm = DBManager.getInstance();
		final SqlSession session = dbm.openSession();

		try {
			final OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			final int result = mapper.deletePost(Post.create(postId));
			session.commit();

			final String responseString = DataUtils
					.toResponseString(result > 0);
			response.setContentType(HttpContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}

}
