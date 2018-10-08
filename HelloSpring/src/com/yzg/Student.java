package com.yzg;

import javax.management.relation.RelationService;

public class Student {
	
	private Integer age;
	private String name;
	private Integer id;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getAge() {
		System.out.println("age : " + age.toString());
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getName() {
		System.out.println("name : " + name);
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void printThrowException() {
		System.out.println("raise exception");
		throw new IllegalArgumentException();
	}
	

}
