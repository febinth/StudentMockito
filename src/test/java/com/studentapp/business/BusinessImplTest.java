package com.studentapp.business;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.studentapp.models.Student;
import com.studentapp.service.StudentService;

@RunWith(MockitoJUnitRunner.class)
public class BusinessImplTest {
	private static List<Student> students;
	
	@Mock
	StudentService studentService;
	
	@Spy
	List<String> listSpy = new ArrayList<String>();
	
	@BeforeClass
	public static void setup() {
		students = new ArrayList<Student>();
		students.add(new Student(1,"science"));
		students.add(new Student(2,"arts"));
		students.add(new Student(3,"science"));	
	}

	@Test
	public void testWhenAndVerify() {
		StudentBusinessImpl studentBusinessImpl = new StudentBusinessImpl(studentService);
		when(studentService.getStudents()).thenReturn(students);
		List<Student> scienceStudents = studentBusinessImpl.getScienceStudents();
		
		//Verify
		verify(studentService).getStudents();
		verify(studentService,atLeastOnce()).getStudents();
		verify(studentService,atLeast(1)).getStudents();
		verify(studentService,atMost(2)).getStudents();
		assertEquals(2,scienceStudents.size());
	}
	
	@Test
	public void testTimeout() {
		StudentBusinessImpl studentBusinessImpl = new StudentBusinessImpl(studentService);
		when(studentService.getIds()).thenAnswer(new Answer<List<Integer>>() {

			public List<Integer> answer(InvocationOnMock invocation) throws Throwable {
				List<Integer> studentIds = new ArrayList<Integer>();
				for(Student student:students) {
					studentIds.add(student.getStudentId());
				}
				return studentIds;
			}			
		});
		new Thread(studentBusinessImpl.getStudentIds).start();
		verify(studentService,timeout(2000)).getIds();
		assertEquals(3, studentBusinessImpl.studentIds.size());
	}
	
	@Test
	public void testAnswerAndOrder() {
		StudentBusinessImpl studentBusinessImpl = new StudentBusinessImpl(studentService);
		InOrder orderVerifier = Mockito.inOrder(studentService);
		
		when(studentService.getStudents()).thenReturn(students);
		when(studentService.getTeacherSubject(anyString())).thenAnswer(new Answer<String>() {

			public String answer(InvocationOnMock invocation) throws Throwable {
				
				String teacher = invocation.getArgumentAt(0, String.class);
				if(teacher.equals("Shyam")) {
					return "Science Teacher";
				}
				else {
					return "Arts Teacher";
				}
			}
			
		});
		String teacherSubject = studentBusinessImpl.getSubject("Shyam");
		assertEquals("Science Teacher", teacherSubject);
		
		List<Student> scienceStudents = studentBusinessImpl.getScienceStudents();
		
		//Testing order
		orderVerifier.verify(studentService).getTeacherSubject(anyString());
		orderVerifier.verify(studentService).getStudents();
		
	}
	
	@Test(expected = NullPointerException.class)
	public void testException() {
		
		doThrow(new NullPointerException()).when(studentService).getTeacherSubject(null);
		studentService.getTeacherSubject(null);
		
	}
	
//	@Mock List<String> listMock;
	@Spy Student studentSpy;
	@Mock Student studentMock;
	@Test()
	public void testSpy() {
//		listMock.add("one");
//		listMock.add("two");
//		assertEquals(0, listMock.size());
//		
//		listSpy.add("one");
//		listSpy.add("two");
//		assertEquals(2, listSpy.size());
		
		studentMock.setStudentId(6);
		studentMock.setSubject("science");
		
		studentSpy.setStudentId(5);
		studentSpy.setSubject("arts");
		students.add(studentSpy);
		
		assertEquals(null, studentMock.getSubject());
		assertEquals("arts", studentSpy.getSubject());
		assertEquals(4, students.size());
	}


}
