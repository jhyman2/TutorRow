package com.example.tutorrow;
/*
 * SchoolClass is a class that will hold data associated to one course
 * that a user can sign up for.
 */
public class SchoolClass {
	
	private String subject;
	private String num;
	private String description;
	
	public SchoolClass(String sub, String num, String descrip){
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
