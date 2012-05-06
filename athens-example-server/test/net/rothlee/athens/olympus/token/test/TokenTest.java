package net.rothlee.athens.olympus.token.test;

import java.util.UUID;

import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.util.CharsetUtil;

import net.rothlee.athens.olympus.Tokens;
import net.rothlee.athens.utils.token.JSONToken;
import net.rothlee.athens.utils.token.JSONTokenFactory;
import net.rothlee.athens.utils.token.TokenException;

public class TokenTest {

	public static void main(String[] args) throws TokenException {
		UUID ssionUuid = UUID.randomUUID();
		JSONToken accessToken = Tokens.createAccessToken("roth2520@gmail.com",
				ssionUuid.toString());
		JSONTokenFactory tokenFactory = new JSONTokenFactory();
		String tokenString = ChannelBuffers.copiedBuffer(
				tokenFactory.toBytes(accessToken)).toString(CharsetUtil.UTF_8);
		System.out.println(tokenString);
		
		JSONToken token = tokenFactory.fromBytes(tokenString.getBytes());
		System.out.println(Tokens.verifyToken(token));
	}
}
