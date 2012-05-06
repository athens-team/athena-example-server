package net.rothlee.athens.olympus.mybatis.test;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class DBManager {
	private static DBManager instance;
	
	public static DBManager getInstance() throws IOException {
		if(instance == null) instance = new DBManager();
		return instance;
	} // getInstance()
	
	private SqlSessionFactory sessionFactory;
	
	private DBManager() throws IOException {
		
		final String resource = "mybatis-conf.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}
	
	public SqlSession openSession(){
		return this.openSession(false);
	}
	
	public SqlSession openSession(boolean autoCommit){
		SqlSession session = null;
		session = sessionFactory.openSession(autoCommit);
		return session;
	}
	
}