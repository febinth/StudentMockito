package com.studentapp.service;

import java.util.List;

import com.studentapp.models.Student;

public interface StudentService {
	public List<Student> getStudents();
	public List<String> getIds();	
	public String getTeacherSubject(String teacherName);
}
