package com.example.tutorrow;

import java.io.Serializable;

/*
 * SchoolClass is a class that will hold data associated to one course
 * that a user can sign up for.
 */
public class SchoolClass implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7157214988916393789L;
	private String subject;
	private String num;
	private String description;
	
	public SchoolClass(String descrip, String num, String sub){
		this.subject = sub;
		this.num = num;
		this.description = descrip;
	}

	public String getSubject(){
		return subject;
	}
	
	public String getNumber(){
		return num;
	}
	
	public String getDescription(){
		return description;
	}
}
