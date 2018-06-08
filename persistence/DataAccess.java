package comp3350.digitalagenda.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.objects.FinancialAid;
import comp3350.digitalagenda.objects.Student;

public interface DataAccess
{
	public void open(String string);

	public void close();
	
	public Course getDegreeCourse(String cID);
	
	public String updateStudent(String SID, String first, String last, String email);
	
	public String getAllCoursesSequential(List<Course> courseResult);
	
	public String getDegreeCoursesSequential(List<Course> courseResult);
	
	public String insertDegreeCourse(Course course);
	
	public String deleteDegreeCourse(Course course);

	public String getStudentCourseSequential(List<Course> courseResult);
	
	public Student getStudentInfo();
	
	public Course getCourseRandom(Course currentCourse);
	
	public String getEnrollment(Course course);
	
	public String getCurrentlyEnrolledList(List<Course> enrollmentList);
	
	public String insertEnrollment(Course currentEnrollment);
	
	public String updateEnrollment(Course currentEnrollment);
	
	public String deleteEnrollment(Course currentEnrollment);
	
	public String getFinancialAidSequential(List<FinancialAid> list);
	
	public ArrayList<FinancialAid> getFinancialAidRandom(FinancialAid currentFAid);
	
	public String insertFinancialAid(FinancialAid current);
	
	public String updateFinancialAid(FinancialAid current);
	
	public String deleteFinancialAid(FinancialAid current);

}



