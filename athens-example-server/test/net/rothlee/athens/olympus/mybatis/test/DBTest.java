package net.rothlee.athens.olympus.mybatis.test;

import java.io.IOException;

import net.rothlee.athens.olympus.data.Post;
import net.rothlee.athens.olympus.data.User;
import net.rothlee.athens.olympus.db.OlympusMapper;

import org.apache.ibatis.session.SqlSession;

public class DBTest {

	public static void main(String[] args) throws IOException{
		DBManager dbm = DBManager.getInstance();
		SqlSession session = dbm.openSession();
		
		try {
			OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			
			mapper.insertUser(User.create("roth2520@gmail.com", "자갈"));
			User user = mapper.getUser(User.createById(1));
			
			mapper.insertPost(Post.create(1, "ㅋㅋㅋㅋ1"));
			mapper.insertPost(Post.create(1, "ㅋㅋㅋㅋ1ㅎ"));
			mapper.insertPost(Post.create(1, "ㅋㅋㅋㅋ1ㅎㅎ"));
			mapper.insertPost(Post.create(1, "ㅋㅋㅋ1"));
			mapper.insertPost(Post.create(1, "ㅋㅋ1"));
			mapper.insertPost(Post.create(1, "ㅋ1"));
			mapper.insertPost(Post.create(1, "ㅋㅋㅋㅋ111"));
			mapper.insertPost(Post.create(1, "ㅋㅋㅋㅋ1222"));
			session.commit();
			
			if(user!=null) {
				System.out.println(user.getId() + ", " + user.getCreatedTime());
			}
			
		} finally {
			session.close();
		}
	}
}
