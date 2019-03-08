package ssh.DaoImp;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import ssh.Dao.UserDao;
import ssh.model.User;

@Repository("UserDao")
public class UserDaoImpl implements UserDao {
	@Resource
	private SessionFactory sessionFactory;

	public User getById(int id) {
		// TODO Auto-generated method stub
		return (User)sessionFactory.getCurrentSession().get(User.class, id);
	}
	

}
