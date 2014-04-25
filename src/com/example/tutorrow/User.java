package com.example.tutorrow;

import java.io.Serializable;
import java.util.ArrayList;

import android.app.Application;

public class User implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3027428612524610162L;
	private String id;
	private String fullName;
	private String email;
	private String phone;
	private String username;
	private ArrayList<SchoolClass> classesTutor = new ArrayList<SchoolClass>();
	private ArrayList<SchoolClass> classesStudent = new ArrayList<SchoolClass>();
	
	public User(String id, String fullName, String email, String phone, String username){
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.phone = phone;
		this.username = username;
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
	
	public String getPhone(){
		return phone;
	}
	
	public String getUsername(){
		return username;
	}
	
	public ArrayList<SchoolClass> getClassesTutor(){
		return classesTutor;
	}
	
	public ArrayList<SchoolClass> getClassesStudent(){
		return classesStudent;
	}
	
	public String[] getClassesStudentFormat(){
		String[] classes = new String[classesStudent.size()];
		int count = 0;
		for (SchoolClass s: classesStudent){
			classes[count] = s.getSubject() + " " + s.getNumber() + ": " + s.getDescription();
			count++;
		}
		
		return classes;
	}
	
	public String[] getClassesTutorFormat(){
		String[] classes = new String[classesTutor.size()];
		int count = 0;
		for (SchoolClass s: classesTutor){
			classes[count] = s.getSubject() + " " + s.getNumber() + ": " + s.getDescription();
			count++;
		}
		
		return classes;
	}
}