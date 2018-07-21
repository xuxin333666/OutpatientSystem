package cn.kgc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.kgc.dao.intf.UserDao;
import cn.kgc.model.User;
import cn.kgc.utils.DBPoolConnection;

public class UserDaoImpl implements UserDao {

	@Override
	public User query(User user) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = "SELECT * FROM t_user WHERE name = ? AND pwd = ?";
		Connection cn = null;
		PreparedStatement psm = null;
		ResultSet result = null;
		try {
			cn = dBP.getConnection();
			psm = cn.prepareStatement(sql);
			psm.setString(1, user.getName());
			psm.setString(2, user.getPwd());
			result = psm.executeQuery();
			if(result.next()) {
				String id = result.getString("id");
				String name = result.getString("name");
				String pwd = result.getString("pwd");
				String status = result.getString("status");
				User queryUser = new User(id, name, pwd, status);
				return queryUser;
			}
		} catch (SQLException e) {
			throw new Exception(e.getMessage());
		} finally {
			if(cn != null) {
				cn.close();
			}	
		}
		return null;
	}

	@Override
	public int updatePwd(String id, String newPwd) throws Exception {
		DBPoolConnection dBP = DBPoolConnection.getInstance();
		String sql = "UPDATE t_user SET pwd = ?,status = ? WHERE id = ?";
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
