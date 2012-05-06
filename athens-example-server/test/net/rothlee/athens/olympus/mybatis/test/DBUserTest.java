package net.rothlee.athens.olympus.mybatis.test;

import java.io.IOException;

import net.rothlee.athens.olympus.data.User;
import net.rothlee.athens.olympus.db.OlympusMapper;

import org.apache.ibatis.session.SqlSession;

public class DBUserTest {

	public static void main(String[] args) throws IOException{
		DBManager dbm = DBManager.getInstance();
		SqlSession session = dbm.openSession();
		
		try {
			OlympusMapper mapper = session.getMapper(OlympusMapper.class);
			
			User user = mapper.getUser(User.createById(5));
			
			session.commit();
			
			if(user!=null) {
				System.out.println(user.getId() + ", " + user.getCreatedTime());
			}
			
		} finally {
			session.close();
		}
	}
}
