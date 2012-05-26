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

import com.eincs.athens.handler.service.simple.Bind;
import com.eincs.athens.handler.service.simple.SimpleService;
import com.eincs.athens.message.AthensContentType;
import com.eincs.athens.message.AthensRequest;
import com.eincs.athens.message.AthensResponse;
import com.eincs.athens.olympus.HeaderNames;
import com.eincs.athens.olympus.TokenNames;
import com.eincs.athens.olympus.Tokens;
import com.eincs.athens.olympus.data.DataUtils;
import com.eincs.athens.olympus.data.Session;
import com.eincs.athens.olympus.data.User;
import com.eincs.athens.olympus.db.OlympusMapper;
import com.eincs.athens.utils.token.JSONToken;
import com.eincs.athens.utils.token.JSONTokenFactory;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/check", method = { "POST" })
public class CheckService implements SimpleService {

	@Override
	public void doServe(AthensRequest request, AthensResponse response)
			throws Exception {
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
				String responseString = DataUtils.toResponseString(false);
				response.setContentType(AthensContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}

			String email = accessToken.getContent().getString(TokenNames.EMAIL);
			String sessionUuid = accessToken.getContent().getString(
					TokenNames.SESSION_UUID);

			User user = mapper.getUserByEmail(User.createByEmail(email));
			if (user == null) {
				String responseString = DataUtils.toResponseString(false);
				response.setContentType(AthensContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}
			
			Session userSession = mapper.getSessionByUUID(Session
					.create(sessionUuid));
			if (userSession == null) {
				String responseString = DataUtils.toResponseString(false);
				response.setContentType(AthensContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}
			session.commit();

			String responseString = DataUtils.toResponseString(true);
			response.setContentType(AthensContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}
}
