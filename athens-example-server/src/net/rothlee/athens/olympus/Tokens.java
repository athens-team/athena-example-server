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
package net.rothlee.athens.olympus;

import java.util.Arrays;

import net.rothlee.athens.utils.token.JSONToken;
import net.rothlee.athens.utils.token.JSONTokenFactory;
import net.rothlee.athens.utils.token.TokenException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author roth2520@gmail.com
 */
public class Tokens {

	public static JSONToken createRegistrationToken(String email, String nickname, String sessionUuid, String tag)
			throws TokenException {
		JSONTokenFactory tokenFactory = new JSONTokenFactory();
		JSONToken accessToken = new JSONToken();
		JSONObject content = new JSONObject();

		try {
			content.put(TokenNames.EMAIL, email);
			content.put(TokenNames.NICKNAME, nickname);
			content.put(TokenNames.SESSION_UUID, sessionUuid);
			content.put(TokenNames.SESSION_TAG, tag);
		} catch (JSONException e) {
			throw new TokenException(e);
		}
		accessToken.setContent(content);
		accessToken.setExpiry(0);
		accessToken.setTimestamp(System.currentTimeMillis());

		// TODO: create signature w/ HMAC
		accessToken.setSignature(tokenFactory.toBytes(accessToken));
		
		return accessToken;
	}
	
	public static JSONToken createAccessToken(String email, String sessionUuid)
			throws TokenException {
		JSONTokenFactory tokenFactory = new JSONTokenFactory();
		JSONToken accessToken = new JSONToken();
		JSONObject content = new JSONObject();

		try {
			content.put(TokenNames.EMAIL, email);
			content.put(TokenNames.SESSION_UUID, sessionUuid);
		} catch (JSONException e) {
			throw new TokenException(e);
		}
		accessToken.setContent(content);
		accessToken.setExpiry(0);
		accessToken.setTimestamp(System.currentTimeMillis());

		// TODO: create signature w/ HMAC
		accessToken.setSignature(tokenFactory.toBytes(accessToken));
		
		return accessToken;
	}
	
	public static boolean verifyToken(JSONToken accessToken)
			throws TokenException {
		JSONTokenFactory tokenFactory = new JSONTokenFactory();
		byte[] signature = accessToken.getSignature();
		accessToken.setSignature(null);
		byte[] targetSignature = tokenFactory.toBytes(accessToken);
		boolean result = Arrays.equals(signature, targetSignature);
		accessToken.setSignature(signature);
		return result;
	}
}
