package net.rothlee.athens.olympus.mybatis.test;

import java.io.IOException;


import org.apache.ibatis.session.SqlSession;

import com.eincs.athens.olympus.data.User;
import com.eincs.athens.olympus.db.OlympusMapper;

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
