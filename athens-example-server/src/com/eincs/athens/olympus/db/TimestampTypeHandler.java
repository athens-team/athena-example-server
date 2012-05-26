package com.eincs.athens.olympus.db;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class TimestampTypeHandler implements TypeHandler {

	@Override
	public void setParameter(PreparedStatement ps, int i, Object parameter,
			JdbcType jdbcType) throws SQLException {
		Timestamp ts = new Timestamp((Long)parameter);
		ps.setTimestamp(i, ts);
	}
	
	@Override
	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		return ts.getTime();

	}

	@Override
	public Object getResult(CallableStatement rs, int columnName)
			throws SQLException {
		Timestamp ts = rs.getTimestamp(columnName);
		return ts.getTime();
	}	

}
