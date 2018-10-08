package com.yzg.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.yzg.Student;

public class StudentJDBCTemplete implements StudentDAO {
	
	private DataSource dataSource;
	private JdbcTemplate templateObject;

	@Override
	public void setDataSource(DataSource ds) {
		// TODO Auto-generated method stub
		this.dataSource = ds;
		this.templateObject = new JdbcTemplate(ds);
		
	}

	@Override
	public void create(String name, Integer age) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Student getStudent(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Student> listStudents() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Integer id, Integer age) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
