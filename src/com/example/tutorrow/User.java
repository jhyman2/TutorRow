package com.example.tutorrow;

import java.util.ArrayList;

public class User {
	
	private String id;
	private String fullName;
	private String email;
	private ArrayList<SchoolClass> classesTutor = new ArrayList<SchoolClass>();
	private ArrayList<SchoolClass> classesStudent = new ArrayList<SchoolClass>();
	
	public User(String id, String fullName, String email){
		this.id = id;
		this.fullName = fullName;
		this.email = email;
	}
	
	public void addClassTutor(String department, String number, String description){
		classesTutor.add(new SchoolClass(department, number, description));
	}
	
	public void addClassStudent(String department, String number, String description){
		classesStudent.add(new SchoolClass(department, number, description));
	}
	
	public String getID(){
		return id;
	}
	
	public String getName(){
		return fullName;
	}
	
	public String getEmail(){
		return email;
	}
	
	public ArrayList<SchoolClass> getClassesTutor(){
		return classesTutor;
	}
	
	public ArrayList<SchoolClass> getClassesStudent(){
		return classesStudent;
	}
}