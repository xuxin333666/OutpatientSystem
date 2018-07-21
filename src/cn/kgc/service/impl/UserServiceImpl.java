package cn.kgc.service.impl;

import cn.kgc.dao.impl.UserDaoImpl;
import cn.kgc.dao.intf.UserDao;
import cn.kgc.model.User;
import cn.kgc.service.intf.UserService;

public class UserServiceImpl implements UserService {
	private final String NAME_OR_PWD_ERORR = "账号不存在或密码错误";
	private final String MODIFY_ERORR = "暂无法修改，请稍后重试";
	
	
	@Override
	public User login(String username, String password) throws Exception {
		UserDao userDao = new UserDaoImpl();
		User user = new User(username, password);	
		user = userDao.query(user);
		if(user == null) {
			throw new Exception(NAME_OR_PWD_ERORR);
		}
		return user;
	}


	@Override
	public User modifyPwd(String id, String username, String pwdMD5) throws Exception {
		UserDao userDao = new UserDaoImpl();	
		int status = userDao.updatePwd(id,pwdMD5);
		if(status == 0) {
			throw new Exception(MODIFY_ERORR);
		}
		return login(username,pwdMD5);
	}

}
