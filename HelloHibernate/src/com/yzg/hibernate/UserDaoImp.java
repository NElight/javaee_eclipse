package com.yzg.hibernate;

import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class UserDaoImp {
	
	private static SessionFactory factory;
	
	public static void main(String args[]) throws IllegalStateException, SecurityException, SystemException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("failed to create sessionfactory object." + e);
			throw new ExceptionInInitializerError(e);
		}
		
		UserDaoImp me = new UserDaoImp();
		Integer r1 = me.addUser(1, "yzg", "123456");
		
		System.out.println(r1);	
	}
	
	
	public Integer addUser(int id, String name, String password) throws IllegalStateException, SystemException, SecurityException, RollbackException, HeuristicMixedException, HeuristicRollbackException {
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer relid = null;
		try {
			tx = (Transaction) session.beginTransaction();
			User user = new User(1, "yzg", "123456");
			relid = (Integer) session.save(user);
			tx.commit();
		}catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
			e.printStackTrace();
		}finally {
			session.close();
		}
		
		return relid;
		
		
		
	}
	

}
