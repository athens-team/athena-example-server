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
package com.eincs.athens.olympus.db;

import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author roth2520@gmail.com
 */
public class DBManager {
	
	private static DBManager instance;
	
	public static DBManager getInstance() throws IOException {
		if(instance == null) instance = new DBManager();
		return instance;
	}
	
	private SqlSessionFactory sessionFactory;
	
	private DBManager() throws IOException {
		
		final String resource = "mybatis-conf.xml";
		Reader reader = Resources.getResourceAsReader(resource);
		sessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}
	
	public SqlSession openSession() throws SQLException{
		return this.openSession(false);
	}
	
	public SqlSession openSession(boolean autoCommit){
		SqlSession session = null;
		session = sessionFactory.openSession(autoCommit);
		return session;
	}
}