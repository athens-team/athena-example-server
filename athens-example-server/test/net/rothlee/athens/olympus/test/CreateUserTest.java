package net.rothlee.athens.olympus.test;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.math.RandomUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;

import com.eincs.athens.HttpPostTest.StringResponseHandler;
import com.eincs.athens.olympus.HeaderNames;
import com.google.common.collect.Lists;

public class CreateUserTest {

	public static void main(String[] args) throws ClientProtocolException,
			IOException, JSONException {

		for (int i = 0; i < 100; i++) {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(
					"http://athens.rothlee.net:8080/write");
			httpPost.addHeader(
					HeaderNames.ACCESS_TOKEN,
					"{\"content\":{\"session_uuid\":\"2640928e-1dd0-4c1a-be8b-f9b162f29e94\",\"email\":\"roth2520+test@gmail.com\"},\"timestamp\":1338652053426,\"signature\":\"eyJjb250ZW50Ijp7InNlc3Npb25fdXVpZCI6IjI2NDA5MjhlLTFkZDAtNGMxYS1iZThiLWY5YjE2\\nMmYyOWU5NCIsImVtYWlsIjoicm90aDI1MjArdGVzdEBnbWFpbC5jb20ifSwidGltZXN0YW1wIjox\\nMzM4NjUyMDUzNDI2fQ==\"}");

			List<NameValuePair> parameters = Lists.newArrayList();;
			parameters
					.add(new BasicNameValuePair("content", getCoplexString()));
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters,
					"UTF-8");
			httpPost.setEntity(entity);

			String response = httpClient.execute(httpPost,
					new StringResponseHandler());
			System.out.println(response);
		}
	}

	private static String getCoplexString() {
		Random rand = new Random();
		int length = rand.nextInt(2000);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			sb.append((char)(rand.nextInt('z' - 'a') + 'a'));
		}
		return sb.toString();
	}
}
