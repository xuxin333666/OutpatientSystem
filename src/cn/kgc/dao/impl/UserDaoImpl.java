package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import cn.kgc.dao.intf.UserDao;
import cn.kgc.model.User;
import cn.kgc.utils.DBPoolConnection;

public class UserDaoImpl extends BaseDaoImpl implements UserDao {

	public UserDaoImpl() {
		super("UserDao");
	}


	@Override
	public User query(User user) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = sqlMap.get("QUERY_SQL");
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, user.getName());
			psm.setString(2, user.getPwd());
			result = psm.executeQuery();
			return result2User(result);		
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
	}
	
	
	private User result2User(ResultSet result) throws SQLException {
		List<User> users = result2List(result);
		if(users.size() != 0) {
			return users.get(0);
		}
		return null;
	}

	private List<User> result2List(ResultSet result) throws SQLException {
		List<User> users = new ArrayList<>();
		while(result.next()) {
			String id = result.getString("id");
			String name = result.getString("name");
			String pwd = result.getString("pwd");
			String status = result.getString("status");
			User queryUser = new User(id, name, pwd, status);
			users.add(queryUser);
		}
		return users;
	}

	@Override
	public int updatePwd(String id, String newPwd) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = sqlMap.get("UPDATE_SQL");
		Connection cn = null;
		PreparedStatement psm = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, newPwd);
			psm.setString(2, "00");
			psm.setString(3, id);
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
