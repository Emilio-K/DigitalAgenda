package comp3350.digitalagenda.business;

import java.util.List;

import comp3350.digitalagenda.application.Main;
import comp3350.digitalagenda.application.Services;
import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.objects.Student;
import comp3350.digitalagenda.persistence.DataAccess;

public class AccessStudents {
	DataAccess dataAccess;
	List<Course> courses;
	String GPA ="";
	
	
	
	public AccessStudents()
	{
		dataAccess = (DataAccess) Services.getDataAccess(Main.getDBPathName());
		
	}
	
	public String getAllCourses(List<Course> courses)
    {
        courses.clear();
        return dataAccess.getStudentCourseSequential(courses);
    }
	
	public String getGPA(List<Course> courses)
	{
		GPA = CalculateGPA.gpa(courses);
	
		return GPA;
	}
	
	public Student getStudentInfo()
	{
		return dataAccess.getStudentInfo();
	}
	
	public String editStudentInfo( String SID,String first, String last, String email)
	{
		return dataAccess.updateStudent(SID,first,last,email);
	}
	
	

}
