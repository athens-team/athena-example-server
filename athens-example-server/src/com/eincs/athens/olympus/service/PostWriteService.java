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

import net.rothlee.athens.olympus.mybatis.test.DBManager;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

import com.eincs.athens.olympus.HeaderNames;
import com.eincs.athens.olympus.TokenNames;
import com.eincs.athens.olympus.Tokens;
import com.eincs.athens.olympus.data.DataUtils;
import com.eincs.athens.olympus.data.Post;
import com.eincs.athens.olympus.data.Session;
import com.eincs.athens.olympus.data.User;
import com.eincs.athens.olympus.db.OlympusMapper;
import com.eincs.pantheon.handler.service.simple.Bind;
import com.eincs.pantheon.handler.service.simple.SimpleService;
import com.eincs.pantheon.message.PanteonContentType;
import com.eincs.pantheon.message.PanteonRequest;
import com.eincs.pantheon.message.PanteonResponse;
import com.eincs.pantheon.utils.token.JSONToken;
import com.eincs.pantheon.utils.token.JSONTokenFactory;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/write", method = { "POST" })
public class PostWriteService implements SimpleService {

	@Override
	public void doServe(PanteonRequest request, PanteonResponse response)
			throws Exception {
		final String content = request.getParams().getParam("content",
				String.class);
		final String accessTokenString = request.getHeaders().get(
				HeaderNames.ACCESS_TOKEN);

		final DBManager dbm = DBManager.getInstance();
		final SqlSession session = dbm.openSession();
		final JSONTokenFactory tokenFactory = new JSONTokenFactory();

		try {
			final OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			final JSONToken accessToken = tokenFactory
					.fromBytes(accessTokenString.getBytes());

			if (!Tokens.verifyToken(accessToken)) {
				String responseString = DataUtils
						.toResponseStringError("permission denied. invalid token.");
				response.setContentType(PanteonContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}

			String email = accessToken.getContent().getString(TokenNames.EMAIL);
			String sessionUuid = accessToken.getContent().getString(
					TokenNames.SESSION_UUID);

			User user = mapper.getUserByEmail(User.createByEmail(email));
			if (user == null) {
				String responseString = DataUtils
						.toResponseStringError("permission denied. no such user.");
				response.setContentType(PanteonContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}
			Session userSession = mapper.getSessionByUUID(Session
					.create(sessionUuid));
			if (userSession == null) {
				String responseString = DataUtils
						.toResponseStringError("permission denied. no such session.");
				response.setContentType(PanteonContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}

			final int result = mapper.insertPost(Post.create(user.getId(), content));
			session.commit();

			final String responseString = DataUtils
					.toResponseString(result > 0);
			response.setContentType(PanteonContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}
}
