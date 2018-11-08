package com.yzg.hibernate;

import java.util.Iterator;
import java.util.List;

import javax.jws.soap.SOAPBinding.Use;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDaoImp {
	
	private static SessionFactory factory;
	
	public static void main(String args[]) {
		try {
			factory = new Configuration().configure().buildSessionFactory();
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("failed to create sessionfactory object." + e);
			throw new ExceptionInInitializerError(e);
		}
		
		UserDaoImp me = new UserDaoImp();
		
//		Integer r1 = me.addUser(1, "yzg", "123456");
//		Integer r2 = me.addUser(2, "aljdf", "djfalfja");
//		
//		System.out.println(r1);	
//		System.out.println(r2);
		
//		me.updateUser(1, "abcd", "efgh");
		me.deleteUser(21);
		me.deleteUser(20);
		
		me.listUser();
		factory.close();
	}
	
	
	public Integer addUser(int id, String name, String password) {
		
		Session session = factory.openSession();
		Transaction tx = null;
		Integer relid = null;
		try {
			tx = session.beginTransaction();
			User user = new User(id, name, password);
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
	
	public void listUser() {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			List users = session.createQuery("from User").list();
			for (Iterator iterator = users.iterator(); iterator.hasNext();) {
				User user = (User)iterator.next();
				System.out.println("id" + user.getId() + "\n" + "name"+ user.getName() + "\n" + "pwd" + user.getPassword() + "\n");
				
			}
			tx.commit();
		}catch(HibernateException e) {
			if(tx!=null) {
				tx.rollback();
			}
			e.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	public void updateUser(Integer id, String name, String password) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User)session.get(User.class, id);
			user.setName(name);
			user.setPassword(password);
			session.update(user);
			tx.commit();
		}catch (HibernateException exception) {
			if (tx != null) {
				tx.rollback();
			}
			exception.printStackTrace();
		}finally {
			session.close();
		}
	}
	
	public void deleteUser(Integer id) {
		Session session = factory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			User user = (User) session.get(User.class, id);
			session.delete(user);
			tx.commit();
		}catch(HibernateException exception) {
			if(tx != null) {
				tx.rollback();
			}
			exception.printStackTrace();
		}finally {
			session.close();
		}
	}
	

}
