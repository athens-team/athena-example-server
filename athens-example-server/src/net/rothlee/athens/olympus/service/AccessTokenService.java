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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import net.rothlee.athens.handler.service.simple.Bind;
import net.rothlee.athens.handler.service.simple.SimpleService;
import net.rothlee.athens.message.AthensContentType;
import net.rothlee.athens.message.AthensRequest;
import net.rothlee.athens.message.AthensResponse;
import net.rothlee.athens.olympus.GmailMailSender;
import net.rothlee.athens.olympus.Tokens;
import net.rothlee.athens.olympus.data.DataUtils;
import net.rothlee.athens.olympus.mybatis.test.DBManager;
import net.rothlee.athens.utils.token.JSONToken;
import net.rothlee.athens.utils.token.JSONTokenFactory;

import org.apache.ibatis.session.SqlSession;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

/**
 * @author roth2520@gmail.com
 */
@Bind(path = "/getAccessToken", method = { "POST" })
public class AccessTokenService implements SimpleService {

	@Override
	public void doServe(AthensRequest request, AthensResponse response)
			throws Exception {

		final String email = request.getParams()
				.getParam("email", String.class);
		final String nickname = request.getParams().getParam("nickname",
				String.class);
		final String tag = request.getParams().getParam("tag",
				String.class);
		final String sessionUuid = UUID.randomUUID().toString();

		final DBManager dbm = DBManager.getInstance();
		final SqlSession session = dbm.openSession();
		final JSONTokenFactory tokenFactory = new JSONTokenFactory();
		try {

			JSONToken accessToken = Tokens
					.createAccessToken(email, sessionUuid);
			JSONToken registrationToken = Tokens.createRegistrationToken(email,
					nickname, sessionUuid, tag);

			GmailMailSender mailSender = GmailMailSender.getInstance();
			mailSender.sendMail(
					"athens.team.2011@gmail.com",
					email,
					"Please Confirm Registration to Olympus",
					createMailContent(ChannelBuffers.copiedBuffer(
							tokenFactory.toBytes(registrationToken)).toString(
							CharsetUtil.UTF_8)));

			String responseString = DataUtils.toResponseString(ChannelBuffers
					.copiedBuffer(tokenFactory.toBytes(accessToken)).toString(
							CharsetUtil.UTF_8));
			response.setContentType(AthensContentType.TEXT_PLAIN);
			response.setContents(ChannelBuffers.copiedBuffer(responseString,
					CharsetUtil.UTF_8));
			return;

		} finally {
			session.close();
		}

	}

	public String createMailContent(String token)
			throws UnsupportedEncodingException {
		StringBuffer sb = new StringBuffer();
		sb.append("<h1>Please confirm this mail.</h1>");
		sb.append("<p>Click following link, and go back to the app.");
		sb.append("Then you can use olympus right a way!</p><br/>");
		sb.append(String
				.format("<a href=\"https://rothlee.net/athens/confirm?token=%s\">Click This Link!</a><br/><br/>",
						URLEncoder.encode(token, CharsetUtil.UTF_8.toString())));
		sb.append("Thank You!");
		return sb.toString();
	}

}
