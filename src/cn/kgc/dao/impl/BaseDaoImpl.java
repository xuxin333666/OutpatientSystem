package cn.kgc.dao.impl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.kgc.utils.DBPoolConnection;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.StringUtils;

public class BaseDaoImpl {	
	protected final String SQL_ERORR = "后台数据错误，";
	
	protected List<Object> query(String sql,Class<?> clazz,Class<?> subClazz,String[] columnName) throws Exception {
		List<Object> list =  new ArrayList<>();
		DBPoolConnection dbp = new DBPoolConnection();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dbp.getConnection();
			psm = cn.prepareStatement(sql); 
			result = psm.executeQuery();
			result2List(result,list,clazz,subClazz,columnName);
		} catch (SQLException e) {
			throw new Exception(SQL_ERORR + e.getMessage());
		} finally {
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	
	protected List<Object> queryById(String sql,Class<?> clazz,Class<?> subClazz,String[] columnName,String id) throws Exception {
		List<Object> list =  new ArrayList<>();
		DBPoolConnection dbp = new DBPoolConnection();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dbp.getConnection();
			psm = cn.prepareStatement(sql); 
			if(StringUtils.isNotEmpty(id)) {
				psm.setString(1, id);			
			}
			result = psm.executeQuery();
			result2List(result,list,clazz,subClazz,columnName);
		} catch (SQLException e) {
			throw new Exception(SQL_ERORR + e.getMessage());
		} finally {
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	protected void result2List(ResultSet result, List<Object> list,Class<?> clazz,Class<?> subClazz,String[] columnName) throws Exception {
		while(result.next()) {
			Object obj = setValue(result,clazz,subClazz,columnName,0);
			list.add(obj);
		}
	}
	
	public List<Object> queryBySearch(String sql,Object dto,Class<?> clazz,Class<?> subClazz,String[] columnName,int attributeStrat,int attributeEnd) throws Exception {
		DBPoolConnection dbp = new DBPoolConnection();
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dbp.getConnection();
			psm = cn.prepareStatement(sql); 
			prepareStatementSetValue(psm, dto, attributeStrat, attributeEnd);
			result = psm.executeQuery();
			List<Object> objs = new ArrayList<>();
			result2List(result, objs, clazz, subClazz, columnName);
			return objs;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(SQL_ERORR + e.getMessage());
		} finally {
			if(cn != null) {
				try {
					cn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private Object setValue(ResultSet result,Class<?> clazz,Class<?> subClazz,String[] columnName,int index) throws Exception  {
		Object obj = clazz.newInstance();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if(columnName.length == index) {
				return obj;
			}
			field.setAccessible(true);
			Object type = field.getType();
			if(type.equals(String.class)) {
				String str = result.getString(columnName[index++]);
				field.set(obj, str);
			} else if(type.equals(Double.class)) {
				Double db = result.getDouble(columnName[index++]);
				field.set(obj, db);
			} else if(type.equals(java.util.Date.class)) {
				Date db = result.getDate(columnName[index++]);
				field.set(obj, db);
			} else if(type.equals(subClazz)) {
				Object obj2 = setValue(result,subClazz,null,columnName,index);
				field.set(obj, obj2);
			}
		}
		return obj;
	}

	
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
	
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			prepareStatementSetValue(psm, obj, attributeStrat, attributeEnd);
			return psm.executeUpdate();	
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd, String fkId) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			prepareStatementSetValue(psm, obj, attributeStrat, attributeEnd);
			psm.setString(attributeEnd+2, fkId);
			return psm.executeUpdate();	
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
