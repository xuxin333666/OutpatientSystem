package cn.kgc.dao.impl;

import java.io.File;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import cn.kgc.utils.DBPoolConnection;
import cn.kgc.utils.DateUtils;
import cn.kgc.utils.FrameUtils;
import cn.kgc.utils.StringUtils;

public class BaseDaoImpl {	
	protected final String SQL_ERORR = "后台数据错误，";
	protected final Map<String, String> sqlMap = new HashMap<>();
	
	/**
	 * 构造方法，读取XML文件。生成子dao对应的sql语句集合
	 */
	@SuppressWarnings("unchecked")
	public BaseDaoImpl(String daoName) {
		SAXReader reader = new SAXReader();
		try {
			Document doc = reader.read(new File("src/sql.xml"));
			Element root = doc.getRootElement();
			Element dao = root.element(daoName);
			Iterator<Element> iter = dao.elementIterator();
			while(iter.hasNext()) {
				Element e = iter.next();
				sqlMap.put(e.getName(),e.getText());
			}
		} catch (DocumentException e) {
			FrameUtils.DialogErorr("配置文件读取错误，" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * 根据sql语句查询数据，将数据生成对象，放入list返回
	 * @param sql sql语句
	 * @param clazz 要生成的对象的class类
	 * @param subClazz 要生成的对象的子对象的class类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @return 返回List<Object>
	 * @throws Exception
	 */
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

	/**
	 *  根据sql语句查询数据（语句有且仅有一个参数），将数据生成对象，放入list返回
	 * @param sql sql语句
	 * @param clazz 要生成的对象的class类
	 * @param subClazz 要生成的对象的子对象的class类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @param id 查询关键字id
	 * @return 返回List<Object>
	 * @throws Exception
	 */
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
	
	/**
	 * 遍历结果集，生成对象，放入list
	 * @param result 查询的结果集
	 * @param list 要放入的容器
	 * @param clazz 要生成的对象的class类
	 * @param subClazz 要生成的对象的子对象的class类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @throws Exception
	 */
	protected void result2List(ResultSet result, List<Object> list,Class<?> clazz,Class<?> subClazz,String[] columnName) throws Exception {
		while(result.next()) {
			Object obj = setValue(result,clazz,subClazz,columnName,0);
			list.add(obj);
		}
	}
	
	/**
	 * 将对象的属性与列名一一对应，从结果集拿出数据赋值到对象对应的属性中
	 * @param result 查询的结果集
	 * @param clazz 要生成的对象的class类
	 * @param subClazz 要生成的对象的子对象的class类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @param index 已经遍历到第几个列名了
	 * @return
	 * @throws Exception
	 */
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

	
	/**
	 * 
	 * @param sql sql语句
	 * @param dto 条件查询的数据传输对象
	 * @param clazz 要生成的对象的class类
	 * @param subClazz 要生成的对象的子对象的class类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @param attributeStrat 生成对象的属性从哪里开始与列名一一对应
	 * @param attributeEnd 生成对象的属性从哪里结束对应
	 * @return 返回List<Object> 
	 * @throws Exception
	 */
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
	
	/**
	 * 查询未使用的最小id
	 * @param sql 查询未使用的最小id语句
	 * @param sql2 当已有的id连续时，查询最大的id数
	 * @param columnName 根据该数组一一对应对象的属性
	 * @return 返回查询到的符合要求的id字符串
	 * @throws Exception
	 */
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
	
	/**
	 * 当已有的id连续时，查询最大的id数
	 * @param cn 查询未使用的最小id启用的数据库连接
	 * @param sql 当已有的id连续时，查询最大的id数sql语句
	 * @param columnName 根据该数组一一对应对象的属性
	 * @return 返回查询到的符合要求的id字符串
	 * @throws Exception
	 */
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
	
	/**
	 * 将给定的对象，添加到数据库
	 * @param sql 添加的sql语句
	 * @param obj 对象的基类
	 * @param attributeStrat 对象的属性从哪里开始与列名一一对应
	 * @param attributeEnd 对象的属性从哪里结束对应
	 * @return 返回影响行数
	 * @throws Exception
	 */
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd) throws Exception {
		return insert(sql, obj, attributeStrat, attributeEnd, new String[]{null});
	}
	
	/**
	 * 将给定的对象（有一个外键id），添加到数据库
	 * @param sql 添加的sql语句
	 * @param obj 对象的基类
	 * @param attributeStrat 对象的属性从哪里开始与列名一一对应
	 * @param attributeEnd 对象的属性从哪里结束对应
	 * @param fkId 外键id的值
	 * @return 返回影响行数
	 * @throws Exception
	 */
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd, String fkId) throws Exception {
		return insert(sql, obj, attributeStrat, attributeEnd, new String[]{fkId});
	}
	
	/**
	 *  将给定的对象（有多个外键id），添加到数据库
	 * @param sql 添加的sql语句
	 * @param obj 对象的基类
	 * @param attributeStrat 对象的属性从哪里开始与列名一一对应
	 * @param attributeEnd 对象的属性从哪里结束对应
	 * @param fkIds 外键id的值的数组
	 * @return 返回影响行数
	 * @throws Exception
	 */
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd, String[] fkIds) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			prepareStatementSetValue(psm, obj, attributeStrat, attributeEnd);
			for (int i = 0; i < fkIds.length; i++) {
				if(fkIds[i] != null) {
					psm.setString(attributeEnd+2+i, fkIds[i]);				
				}				
			}
			return psm.executeUpdate();	
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}

	/**
	 * 将对象的属性一一对应的设置到PreparedStatement的参数中，要求两者顺序一致
	 * @param psm PreparedStatement对象
	 * @param obj 对象的基类
	 * @param attributeStrat 对象的属性从哪里开始与列名一一对应
	 * @param attributeEnd 对象的属性从哪里结束对应
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SQLException
	 */
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
	
	/**
	 * 根据给定的id，修改数据。
	 * @param sql 修改的sql语句
	 * @param obj 对象的基类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @param id 修改所依赖的id
	 * @return 返回影响行数
	 * @throws Exception
	 */
	protected int updateById(String sql,Object obj,String[] columnName,String id) throws Exception {
		return updateById(sql, obj, columnName, id, null);
	}
	
	/**
	 * 根据给定的id（该对象含有一个外键id），修改数据。
	@param sql 修改的sql语句
	 * @param obj 对象的基类
	 * @param columnName 根据该数组一一对应对象的属性
	 * @param id 修改所依赖的id
	 * @param fkId 该对象的外键id
	 * @return 返回影响行数
	 * @throws Exception
	 */
	protected int updateById(String sql,Object obj,String[] columnName,String id,String fkId) throws Exception {
		StringBuilder sb = new StringBuilder(sql);
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		Connection cn = null;
		PreparedStatement psm = null;
		for (int i=1;i<columnName.length;i++) {
			sb.append(" " + columnName[i] + " = ?");
			if(i != columnName.length-1) {
				sb.append(",");
			}
		}
		sb.append(" WHERE id = ?");	
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sb.toString());
			if(fkId == null) {
				prepareStatementSetValue(psm, obj, 1, columnName.length-1);
			} else {
				prepareStatementSetValue(psm, obj, 1, columnName.length-2);
				psm.setString(columnName.length-1,fkId);			
			}
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
	
	/**
	 * 根据给定的id删除数据
	 * @param sql 删除的sql语句
	 * @param id 删除的数据的id
	 * @return 返回影响行数
	 * @throws Exception
	 */
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
