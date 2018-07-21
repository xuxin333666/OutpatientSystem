package cn.kgc.dao.intf;

import cn.kgc.model.User;

public interface UserDao {
	User query(User user) throws Exception;

	int updatePwd(String id, String newPwd) throws Exception;
}
