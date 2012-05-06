package net.onpage.db.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.onpage.data.StatusType;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class StatusTypeHandler implements TypeHandler {
	
	public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType)
			throws SQLException {
		StatusType statusType = (StatusType) parameter;
		ps.setString(i, statusType.getValue());
	}

	public Object getResult(ResultSet rs, String columnName)
			throws SQLException {
		String value = rs.getString(columnName);
		if(value==null) return null;
		return StatusType.getValueOf(value);
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		String value = cs.getString(columnIndex);
		if(value==null) return null;
		return StatusType.getValueOf(value);
	}
}