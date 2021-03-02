package com.studentapp.business;

import java.util.ArrayList;
import java.util.List;

import com.studentapp.models.Student;
import com.studentapp.service.StudentService;

public class StudentBusinessImpl {
	
	private StudentService studentService;
	
	public StudentBusinessImpl(StudentService studentService) {
		this.studentService=studentService;
	}
	
	public List<Student> getScienceStudents(){
		List<Student> students = studentService.getStudents();
		List<Student> scienceStudents = new ArrayList<Student>();
		
		for(Student student:students) {
			if(student.getSubject()=="science") {
				scienceStudents.add(student);
			}
		}
		return scienceStudents;
		
	}
	
	public List<Student> getArtsStudents(){
		
		List<Student> students = studentService.getStudents();
		List<Student> artsStudents = new ArrayList<Student>();
		
		for(Student student:students) {
			if(student.getSubject()=="arts") {
				artsStudents.add(student);
			}
		}
		return artsStudents;
	}	
	
	public final Runnable getStudentIds = new Runnable() {
	    public void run() {
	        try {
	            Thread.sleep(1000);
	            studentService.getIds();
	        } catch (InterruptedException e) {
	        	e.printStackTrace();
	        }
	    }

	};
	
	public String getSubject(String teacherName) {
		String subject = studentService.getTeacherSubject(teacherName);
		return subject;
		
	}

	public static void main(String[] args) {
		
	}

}
