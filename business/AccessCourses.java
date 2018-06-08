package comp3350.digitalagenda.business;

import java.util.List;

import comp3350.digitalagenda.application.Main;
import comp3350.digitalagenda.application.Services;
import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.persistence.DataAccess;


public class AccessCourses
{
	private DataAccess dataAccess;

	public AccessCourses()
	{
		dataAccess = (DataAccess)Services.getDataAccess(Main.dbName);
		
	}

    public String getAllCourses(List<Course> courses)
    {
        courses.clear();
        return dataAccess.getAllCoursesSequential(courses);
    }
    
    public Course getDegreeCourse(String cID)
    {
    	return dataAccess.getDegreeCourse(cID);
    }

    public String getDegreeCourses(List<Course> courses)
    {
        courses.clear();
        return dataAccess.getDegreeCoursesSequential(courses);
    }
    
    public String getStudentCourses(List<Course> courses)
    {
        courses.clear();
        return dataAccess.getStudentCourseSequential(courses);
    }
    
    public String deleteDegreeCourse(Course course)
    {
    	return dataAccess.deleteDegreeCourse(course);
    }


	public Course getRandom(Course course)
	{
		return dataAccess.getCourseRandom(course);
	}

	public String insertDegreeCourse(Course currentCourse)
	{
		return dataAccess.insertDegreeCourse(currentCourse);
	}
}
