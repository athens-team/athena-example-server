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
@Bind(path = "/confirm", method = { "GET" })
public class ConfirmService implements SimpleService {

	@Override
	public void doServe(AthensRequest request, AthensResponse response)
			throws Exception {

		final String tokenString = request.getParams().getParam("token",
				String.class);

		final DBManager dbm = DBManager.getInstance();
		final SqlSession session = dbm.openSession();
		final JSONTokenFactory tokenFactory = new JSONTokenFactory();
		try {
			if (tokenString == null) {
				String responseString = DataUtils
						.toResponseStringError("invalid access");
				response.setContentType(AthensContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}

			JSONToken registrationToken = tokenFactory.fromBytes(tokenString
					.getBytes());
			if (!Tokens.verifyToken(registrationToken)) {
				String responseString = DataUtils
						.toResponseStringError("invalid token");
				response.setContentType(AthensContentType.TEXT_PLAIN);
				response.setContents(ChannelBuffers.copiedBuffer(
						responseString, CharsetUtil.UTF_8));
				return;
			}

			String email = registrationToken.getContent().getString(
					TokenNames.EMAIL);
			String nickname = registrationToken.getContent().getString(
					TokenNames.NICKNAME);
			String sessionUuid = registrationToken.getContent().getString(
					TokenNames.SESSION_UUID);
			String sessionTag = registrationToken.getContent().getString(
					TokenNames.SESSION_TAG);

			final OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			User user = mapper.getUserByEmail(User.createByEmail(email));
			if(user==null) {
				mapper.insertUser(User.create(email, nickname));
				user = mapper.getUserByEmail(User.createByEmail(email));
			}
			mapper.insertSession(Session.create(user.getId(), sessionUuid,
					sessionTag));
			session.commit();
			
			String responseString = DataUtils.toResponseString(true);
			response.setContentType(AthensContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));
			return;

		} finally {
			session.close();
		}
	}
}
