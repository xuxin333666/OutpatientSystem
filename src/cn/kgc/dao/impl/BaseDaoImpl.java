package cn.kgc.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.kgc.utils.DBPoolConnection;
import cn.kgc.utils.DateUtils;

public class BaseDaoImpl {
	
	protected String queryMinEmptyId(String sql,String sql2,String columnName) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			result = psm.executeQuery();
			if(result.next()) {
				return result.getString(columnName);
				
			}
			return queryMaxId(cn,sql2,columnName);		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	protected String queryMaxId(Connection cn,String sql,String columnName) throws Exception {
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			psm = cn.prepareStatement(sql);
			result = psm.executeQuery();
			if(result.next()) {
				return result.getString(columnName);
			}
			return null;		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	protected void prepareStatementSetValue(PreparedStatement psm,Object obj,int attributeStrat,int attributeEnd) throws IllegalArgumentException, IllegalAccessException, SQLException {
		Field[] attributes = obj.getClass().getDeclaredFields();
		int index = 1;
		for (;attributeStrat<=attributeEnd;attributeStrat++) {
			attributes[attributeStrat].setAccessible(true);
			Object value = attributes[attributeStrat].get(obj);
			if(value == null) {
				psm.setObject(index++, null);
				continue;
			}
			Class<?> clazz = attributes[attributeStrat].getType();
			if(clazz.equals(String.class)) {
				psm.setString(index++, value.toString());
			} else if (clazz.equals(Double.class)) {
				psm.setDouble(index++,Double.valueOf(value.toString()));
			} else if(clazz.equals(java.util.Date.class)) {
				psm.setDate(index++, DateUtils.date2SqlDate((java.util.Date)value));
			}
		}
		
	}
	
	protected int updateById(StringBuilder sql,Object obj,String[] columnName,String id) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		for (int i=1;i<columnName.length;i++) {
			sql.append(" " + columnName[i] + " = ?");
			if(i != columnName.length-1) {
				sql.append(",");
			}
		}
		sql.append(" WHERE id = ?");	
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql.toString());
			prepareStatementSetValue(psm, obj, 1, columnName.length-1);
			psm.setString(columnName.length, id);
			return psm.executeUpdate();	
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	protected int deleteById(String sql,String id) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, id);
			return psm.executeUpdate();	
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
}
