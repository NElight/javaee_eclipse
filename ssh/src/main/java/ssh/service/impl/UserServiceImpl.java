package ssh.service.impl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import ssh.Dao.UserDao;
import ssh.model.User;
import ssh.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Resource
	private UserDao userDao;

	public User getById(int id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

}
