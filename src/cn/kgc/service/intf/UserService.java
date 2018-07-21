package cn.kgc.service.intf;

import cn.kgc.model.User;

public interface UserService {
	User login(String username,String password) throws Exception;

	User modifyPwd(String id, String username, String pwdMD5) throws Exception;
}
