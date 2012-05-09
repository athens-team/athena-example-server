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

import net.rothlee.athens.handler.codec.http.HttpContentType;
import net.rothlee.athens.handler.service.simple.Bind;
import net.rothlee.athens.handler.service.simple.SimpleService;
import net.rothlee.athens.message.AthensRequest;
import net.rothlee.athens.message.AthensResponse;
import net.rothlee.athens.olympus.HeaderNames;
import net.rothlee.athens.olympus.TokenNames;
import net.rothlee.athens.olympus.Tokens;
import net.rothlee.athens.olympus.data.DataUtils;
import net.rothlee.athens.olympus.data.Session;
import net.rothlee.athens.olympus.data.User;
import net.rothlee.athens.olympus.db.OlympusMapper;
import net.rothlee.athens.olympus.mybatis.test.DBManager;
import net.rothlee.athens.utils.token.JSONToken;
import net.rothlee.athens.utils.token.JSONTokenFactory;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

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
				response.setContentType(HttpContentType.TEXT_PLAIN);
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
				response.setContentType(HttpContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}
			
			Session userSession = mapper.getSessionByUUID(Session
					.create(sessionUuid));
			if (userSession == null) {
				String responseString = DataUtils.toResponseString(false);
				response.setContentType(HttpContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}
			session.commit();

			String responseString = DataUtils.toResponseString(true);
			response.setContentType(HttpContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));

		} finally {
			session.close();
		}
	}
}
