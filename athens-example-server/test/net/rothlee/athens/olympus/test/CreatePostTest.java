package net.rothlee.athens.olympus.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.eincs.athens.HttpPostTest.StringResponseHandler;
import com.google.common.collect.Lists;

public class CreatePostTest {

	public static void main(String[] args) throws ClientProtocolException,
			IOException, JSONException {

//		final String email = request.getParams()
//				.getParam("email", String.class);
//		final String nickname = request.getParams().getParam("nickname",
//				String.class);
//		final String tag = request.getParams().getParam("tag",
//				String.class);
		
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(
				"http://athens.rothlee.net:8080/getAccessToken");

		List<NameValuePair> parameters = Lists.newArrayList();;
		parameters.add(new BasicNameValuePair("email",
				"roth2520+test@gmail.com"));
		parameters.add(new BasicNameValuePair("nickname", "eincs"));
		parameters.add(new BasicNameValuePair("tag", "Android Nexus S"));
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
				"UTF-8");
		httpPost.setEntity(entity);

		String response = httpClient.execute(httpPost,
				new StringResponseHandler());
		System.out.println(response);
		
		JSONObject result = new JSONObject(response);
		System.out.println(result.getString("result"));
		
//		{"content":{"session_uuid":"2640928e-1dd0-4c1a-be8b-f9b162f29e94","email":"roth2520+test@gmail.com"},"timestamp":1338652053426,"signature":"eyJjb250ZW50Ijp7InNlc3Npb25fdXVpZCI6IjI2NDA5MjhlLTFkZDAtNGMxYS1iZThiLWY5YjE2\nMmYyOWU5NCIsImVtYWlsIjoicm90aDI1MjArdGVzdEBnbWFpbC5jb20ifSwidGltZXN0YW1wIjox\nMzM4NjUyMDUzNDI2fQ=="}

	}
}
