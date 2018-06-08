package comp3350.digitalagenda.objects;

import java.util.ArrayList;

public class Course implements Comparable<Course>
{
	private enum Enrollment {inProg, complete, incomplete};
	private Enrollment enroll;
	private String courseID;
	private String courseName;
	ArrayList<Section> sections = new ArrayList<Section>();
	
	public Course(String courseID)
	{
		this.courseID = courseID;
		courseName = null;
		enroll = Enrollment.incomplete;
	}

	public Course(String courseID, String courseName)
	{
		this.courseID = courseID;
		this.courseName = courseName;
		enroll = Enrollment.incomplete;
	}

	public Course(String courseID, String courseName, String theGrade)
	{
		this.courseID = courseID;
		this.courseName = courseName;
		Section sect = new Section("NA", "NA", "NA");
		sect.setGrade(theGrade);
		sections.add(sect);
		enroll = Enrollment.complete;
	}
	
	public Course(String courseID, String courseName, String time, String prof, String room, String theGrade)
	{
		this.courseID= courseID;
		this.courseName = courseName;
		Section sect = new Section(time,prof,room);
		sections.add(sect);
		sect.setGrade(theGrade);
	}
	
	
	
	public Course(String courseID, String courseName, String time, String prof, String room)
	{
		this.courseID = courseID;
		this.courseName = courseName;
		Section sect = new Section(time, prof, room);
		sections.add(sect);
		sect.setGrade("IP");
		enroll = Enrollment.inProg;
	}

	public String getCourseID()
	{
		return courseID;
	}

	public String getCourseName()
	{
		return courseName;
	}
	
	public String getSectionInfo()
	{
		String result = "";
		
		if(sections.size() != 0)
		{
			Section sect = sections.get(sections.size()-1);
			result = sect.toString();
		}
		
		return result;
	}
	
	public Section getSection(int i){
		return sections.get(i);
		
	}

	public String toString()
	{
		return "Course: " + courseID + " " + courseName;
	}
	
	public boolean equals(Object object)
	{
		boolean result;
		Course c;
		
		result = false;
		
		if (object instanceof Course)
		{
			c = (Course) object;
			if (((c.courseID == null) && (courseID == null)) || (c.courseID.equals(courseID)))
			{
				result = true;
			}
		}
		return result;
	}
	
	public int compareTo(Course c)
	{
		int Cmp = courseID.compareTo(c.courseID);
		return Cmp;
	}

	public String getGrade() 
	{
		String result = "N";
		
		if(!sections.isEmpty())
		{
			for(int loop = 0; loop < sections.size(); loop++)
			{
				result = sections.get(loop).getGrade();
			}
		}
		
		return result;
	}
	
	public boolean inProgress()
	{
		boolean result;
		switch (enroll)
		{
		case inProg:
			result = true;
			break;
		default:
			result = false;
			break;
		}
		return result;
	}
	
	public void setName(String name){
		courseName = name;
	}
	
	public void setGrade(String grade){
		
	}
}