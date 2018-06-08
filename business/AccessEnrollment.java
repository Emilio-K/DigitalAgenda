package comp3350.digitalagenda.business;

import java.util.List;

import comp3350.digitalagenda.application.Main;
import comp3350.digitalagenda.application.Services;
import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.persistence.DataAccess;

public class AccessEnrollment {
	
	private DataAccess dataAccess;
	
	public AccessEnrollment()
	{
		dataAccess = (DataAccess) Services.getDataAccess(Main.getDBPathName());
	}
	
	public String getAllEnrollmentData(List<Course> enrollmentList)
    {
        enrollmentList.clear();
        return dataAccess.getCurrentlyEnrolledList(enrollmentList);
    }
	
	public String insertEnrollmentData(Course currentEnrollment)
	{
		return dataAccess.insertEnrollment(currentEnrollment);
	}

	public String updateEnrollmentData(Course currentEnrollment)
	{
		return dataAccess.updateEnrollment(currentEnrollment);
	}

	public String deleteEnrollmentData(Course currentEnrollment)
	{
		return dataAccess.deleteEnrollment(currentEnrollment);
	}
	
	public String getEnrollmentData(Course course)
	{
		return dataAccess.getEnrollment(course);
	}
	
}
