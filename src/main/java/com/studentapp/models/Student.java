package com.studentapp.models;


public class Student {
	private int studentId;
	private String subject;
	
	public Student() {
	}

	public Student(int studentId, String subject) {
		super();
		this.studentId = studentId;
		this.subject = subject;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", subject=" + subject + "]";
	}
	
}
