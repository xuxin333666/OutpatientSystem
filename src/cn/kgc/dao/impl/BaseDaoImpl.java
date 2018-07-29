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
	protected final String SQL_ERORR = "��̨���ݴ���";
	protected final Map<String, String> sqlMap = new HashMap<>();
	
	/**
	 * ���췽������ȡXML�ļ���������dao��Ӧ��sql��伯��
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
			FrameUtils.DialogErorr("�����ļ���ȡ����" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * ����sql����ѯ���ݣ����������ɶ��󣬷���list����
	 * @param sql sql���
	 * @param clazz Ҫ���ɵĶ����class��
	 * @param subClazz Ҫ���ɵĶ�����Ӷ����class��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @return ����List<Object>
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
	 *  ����sql����ѯ���ݣ�������ҽ���һ�������������������ɶ��󣬷���list����
	 * @param sql sql���
	 * @param clazz Ҫ���ɵĶ����class��
	 * @param subClazz Ҫ���ɵĶ�����Ӷ����class��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @param id ��ѯ�ؼ���id
	 * @return ����List<Object>
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
	 * ��������������ɶ��󣬷���list
	 * @param result ��ѯ�Ľ����
	 * @param list Ҫ���������
	 * @param clazz Ҫ���ɵĶ����class��
	 * @param subClazz Ҫ���ɵĶ�����Ӷ����class��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @throws Exception
	 */
	protected void result2List(ResultSet result, List<Object> list,Class<?> clazz,Class<?> subClazz,String[] columnName) throws Exception {
		while(result.next()) {
			Object obj = setValue(result,clazz,subClazz,columnName,0);
			list.add(obj);
		}
	}
	
	/**
	 * �����������������һһ��Ӧ���ӽ�����ó����ݸ�ֵ�������Ӧ��������
	 * @param result ��ѯ�Ľ����
	 * @param clazz Ҫ���ɵĶ����class��
	 * @param subClazz Ҫ���ɵĶ�����Ӷ����class��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @param index �Ѿ��������ڼ���������
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
	 * @param sql sql���
	 * @param dto ������ѯ�����ݴ������
	 * @param clazz Ҫ���ɵĶ����class��
	 * @param subClazz Ҫ���ɵĶ�����Ӷ����class��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @param attributeStrat ���ɶ�������Դ����￪ʼ������һһ��Ӧ
	 * @param attributeEnd ���ɶ�������Դ����������Ӧ
	 * @return ����List<Object> 
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
	 * ��ѯδʹ�õ���Сid
	 * @param sql ��ѯδʹ�õ���Сid���
	 * @param sql2 �����е�id����ʱ����ѯ����id��
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @return ���ز�ѯ���ķ���Ҫ���id�ַ���
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
	 * �����е�id����ʱ����ѯ����id��
	 * @param cn ��ѯδʹ�õ���Сid���õ����ݿ�����
	 * @param sql �����е�id����ʱ����ѯ����id��sql���
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @return ���ز�ѯ���ķ���Ҫ���id�ַ���
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
	 * �������Ķ�����ӵ����ݿ�
	 * @param sql ��ӵ�sql���
	 * @param obj ����Ļ���
	 * @param attributeStrat ��������Դ����￪ʼ������һһ��Ӧ
	 * @param attributeEnd ��������Դ����������Ӧ
	 * @return ����Ӱ������
	 * @throws Exception
	 */
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd) throws Exception {
		return insert(sql, obj, attributeStrat, attributeEnd, new String[]{null});
	}
	
	/**
	 * �������Ķ�����һ�����id������ӵ����ݿ�
	 * @param sql ��ӵ�sql���
	 * @param obj ����Ļ���
	 * @param attributeStrat ��������Դ����￪ʼ������һһ��Ӧ
	 * @param attributeEnd ��������Դ����������Ӧ
	 * @param fkId ���id��ֵ
	 * @return ����Ӱ������
	 * @throws Exception
	 */
	protected int insert(String sql,Object obj,int attributeStrat,int attributeEnd, String fkId) throws Exception {
		return insert(sql, obj, attributeStrat, attributeEnd, new String[]{fkId});
	}
	
	/**
	 *  �������Ķ����ж�����id������ӵ����ݿ�
	 * @param sql ��ӵ�sql���
	 * @param obj ����Ļ���
	 * @param attributeStrat ��������Դ����￪ʼ������һһ��Ӧ
	 * @param attributeEnd ��������Դ����������Ӧ
	 * @param fkIds ���id��ֵ������
	 * @return ����Ӱ������
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
	 * �����������һһ��Ӧ�����õ�PreparedStatement�Ĳ����У�Ҫ������˳��һ��
	 * @param psm PreparedStatement����
	 * @param obj ����Ļ���
	 * @param attributeStrat ��������Դ����￪ʼ������һһ��Ӧ
	 * @param attributeEnd ��������Դ����������Ӧ
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
	 * ���ݸ�����id���޸����ݡ�
	 * @param sql �޸ĵ�sql���
	 * @param obj ����Ļ���
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @param id �޸���������id
	 * @return ����Ӱ������
	 * @throws Exception
	 */
	protected int updateById(String sql,Object obj,String[] columnName,String id) throws Exception {
		return updateById(sql, obj, columnName, id, null);
	}
	
	/**
	 * ���ݸ�����id���ö�����һ�����id�����޸����ݡ�
	@param sql �޸ĵ�sql���
	 * @param obj ����Ļ���
	 * @param columnName ���ݸ�����һһ��Ӧ���������
	 * @param id �޸���������id
	 * @param fkId �ö�������id
	 * @return ����Ӱ������
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
	 * ���ݸ�����idɾ������
	 * @param sql ɾ����sql���
	 * @param id ɾ�������ݵ�id
	 * @return ����Ӱ������
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
