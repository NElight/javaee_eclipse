package com.yzg.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Before;
import org.junit.Test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class Mybatis_first {
	private SqlSessionFactory sqlSessionFactory;
	
	@Before
	public void createSqlSessionFactory() throws IOException {
		String resource = "SqlMapConfig.xml";
		InputStream inputStream = Resources.getResourceAsStream(resource);
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
	}
	
	@Test
	public void testFindUserById() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			User user = sqlSession.selectOne("test.findUserById", 10);
			System.out.println(user);
		}catch (Exception exception) {
			exception.printStackTrace();
		}finally {
			if(sqlSession!= null) {
				sqlSession.close();
			}
		}
	}
	
	@Test
	public void testInsertUser() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			User user = new User();
			user.setId(21);
			user.setName("abcdefg");
			user.setPassword("kingstar");
			sqlSession.insert("test.insertUser", user);
			sqlSession.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sqlSession != null) {
				sqlSession.close();
			}
		}
		
	}
	
	@Test
	public void testDelete() {
		SqlSession sqlSession = null;
		try {
			sqlSession = sqlSessionFactory.openSession();
			sqlSession.delete("test.deleteUser", 21);
			sqlSession.commit();
		}catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(sqlSession!=null) {
				sqlSession.close();
			}
		}
	}
	
}
