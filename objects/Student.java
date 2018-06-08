package comp3350.digitalagenda.objects;

import java.util.ArrayList;

public class Student 
{

	private String firstName;
	private String lastName;
	private String sID;
	private ArrayList<Course> coursesTaken;
	private String email;
	
	public Student(String firstName, String lastName, String sID, String email, ArrayList<Course> coursesList)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.sID = sID;
		this.email = email;
		this.coursesTaken = coursesList;
	}
	
	public Student( String sID,String firstName, String lastName, String email)
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.sID = sID;
		this.email = email;

	}
	
	public Student()
	{
		this.firstName = "firstName";
		this.lastName = "lastName";
		this.sID = "0000000"; 
		this.coursesTaken = null;
		this.email = "name@email.com";
	}
	
	public ArrayList<Course> getCourseList()
	{
		return coursesTaken;
	}
	
	public String getFirstName()
	{
		return firstName;
	}
	
	public String getLastName()
	{
		return lastName;
	}
	
	public String getSID()
	{
		return sID;
	}
	
	public void setFirstName(String firstName)
	{
		this.firstName = firstName; 
	}
	
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	
	public void setStudentID(String sID)
	{
		this.sID = sID;
	}

	public void setEmail(String email){
		this.email = email;
	}
	
	public String getEmail() {
		
		return email;
	}
}
