package comp3350.digitalagenda.objects;

import java.util.ArrayList;

public class Degree 
{
	private String degName;
	private ArrayList<Course> requiredCourseList;
	private int electiveCrdHrs;
	
	public Degree(ArrayList<Course> courseList, String degreeName, int creditHours)
	{
		this.degName = degreeName;
		this.requiredCourseList = courseList;
		this.setElectiveCrdHrs(creditHours);
	}
	
	public ArrayList<Course> getCourseList()
	{
		return requiredCourseList;
	}
	
	public String getName()
	{
		return degName;
	}

	public int getElectiveCrdHrs() {
		return electiveCrdHrs;
	}
	
	public Course getCourse (int position){
		return requiredCourseList.get(position);		
	}

	public void setElectiveCrdHrs(int reqElectiveCrdHrs) {
		this.electiveCrdHrs = reqElectiveCrdHrs;
	}
	
	public String addCourse(Course course){
		if (requiredCourseList.indexOf(course)== -1){
			requiredCourseList.add(course);
			return null;
		} else {
			return ("Course " + course.getCourseName() + " already exists in degree.");
		}
	}
	
	public String removeCourse(Course course){

		if (requiredCourseList.indexOf(course)>=0){
			requiredCourseList.remove(course);
		return null;
		}else{
			return "Course " + course.getCourseName() + " not found, could not be removed.";
		}
	}

	
}
