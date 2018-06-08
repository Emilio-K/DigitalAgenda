package comp3350.digitalagenda.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.objects.FinancialAid;
import comp3350.digitalagenda.objects.Section;
import comp3350.digitalagenda.objects.Student;

public class DataAccessObject implements DataAccess
{
	private Statement st1,st4;
	private Connection c1;
	private ResultSet rs2, rs3;

	private String dbName;
	private String dbType;



	private ArrayList<FinancialAid> aidList;


	private String cmdString;
	private int updateCount;
	private String result;
	private static String EOF = "  ";

	public DataAccessObject(String dbName)
	{
		this.dbName = dbName;
	}

	public void open(String dbPath)
	{
		String url;
		try
		{
			// Setup for HSQL
			dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
			c1 = DriverManager.getConnection(url, "SA", "");
			st1 = c1.createStatement();
			st4 = c1.createStatement();
				
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		System.out.println("Opened " +dbType +" database " +dbPath);
	}

	public void close()
	{
		try
		{	// commit all changes to the database
			cmdString = "shutdown compact";
			rs2 = st1.executeQuery(cmdString);
			c1.close();
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		System.out.println("Closed " +dbType +" database " +dbName);
	}
	
	@Override
	public String getDegreeCoursesSequential(List<Course> courseResult) {
		
		String cID, cName, time, prof, room;
		cName = EOF;
		cID = EOF;
		
		String grade = EOF;
		Course course;
		
		
		result = null;
		try
		{
			cmdString = "Select * from DegreeCourses";
			rs2 = st1.executeQuery(cmdString);
		
		}
		
		catch (Exception e)
		{
			processSQLError(e);
		}
		try
		{
			while (rs2.next())
			{
				
				cID = rs2.getString("CourseID");
				cName = getCourseName(cID);
				grade = getCourseGrade(cID);
				time = getCourseTime(cID);
				
				prof = getCourseProf(cID);
				room = getCourseRoom(cID);
					course = new Course(cID,cName,time,prof,room,grade);
					courseResult.add(course);
		
			}
			
			rs2.close();
			
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		return result;
	}
	private String getCourseGrade(String cID) throws Exception
	{
		ResultSet rs6;
		String grade ="";
		rs6=st4.executeQuery("Select Grade from Enrolment where CourseID =" + "'" + cID + "'");
		if(rs6.next())
			grade = rs6.getString("Grade");
		rs6.close();
		return grade;
	}
	
	private String getCourseTime(String cID) throws Exception
	{
		ResultSet rs6;
		String time ="";
		rs6=st4.executeQuery("Select Time from Courses where CourseID =" + "'" + cID + "'");
		if(rs6.next())
			time = rs6.getString("Time");
		rs6.close();
		return time;
	}
	
	private String getCourseProf(String cID) throws Exception
	{
		ResultSet rs6;
		String prof ="";
		rs6=st4.executeQuery("Select Prof from Courses where CourseID =" + "'" + cID + "'");
		if(rs6.next())
			prof = rs6.getString("Prof");
		rs6.close();
		return prof;
	}
	
	private String getCourseRoom(String cID) throws Exception
	{
		ResultSet rs6;
		String room ="";
		rs6=st4.executeQuery("Select Room from Courses where CourseID =" + "'" + cID + "'");
		if(rs6.next())
			room = rs6.getString("Room");
		rs6.close();
		return room;
	}
	
	private String getCourseName(String cID) throws Exception
	{
		ResultSet rs7;
		String cName="";
		rs7 = st4.executeQuery("Select Name from Courses where CourseID =" + "'" + cID +"'");
		if(rs7.next())
			cName = rs7.getString("Name");
		rs7.close();
		return cName;
	}

	public Course getDegreeCourse(String cID)
	{
		String cName, cGrade;
		cName = EOF;
		cGrade = EOF;
		
		Course course=null;
		
		try
		{
			cmdString = "Select * from DegreeCourses where CourseID =" +"'" + cID +"'";
			rs2=st1.executeQuery(cmdString);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong");
			processSQLError(e);
		}
		try
		{
			if(rs2.next())
			{
				cName = getCourseName(cID);
				cGrade = getCourseGrade(cID);
				course = new Course(cID, cName, cGrade);
			}
			rs2.close();
			
		}
		catch(Exception e)
		{
			System.out.println("Error");
		}
		return course;
		
	}
	public String getStudentCourseSequential(List<Course> courseResult) {
		String  cID, grade;
		cID = EOF;
		grade = EOF;
		
		Course course;

		result = null;
		try
		{
			cmdString = "Select * from Enrolment";
			rs2 = st1.executeQuery(cmdString);
			
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		try
		{
			while (rs2.next())
			{
				cID = rs2.getString("CourseID");
				grade = rs2.getString("Grade");
				String cName=getCourseName(cID);
				course = new Course(cID,cName,grade);
				courseResult.add(course);
			}
			rs2.close();
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}

		return result;
	}
	
	public Student getStudentInfo(){
		String sID, fName, lName, email;
		sID= EOF;
		fName = EOF;
		lName = EOF;
		email = EOF;
		
		Student student=null;
		
		try
		{
			cmdString = "Select * from Student";
			rs2 = st1.executeQuery(cmdString);
			
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		try
		{
			if (rs2.next())
			{
				sID = rs2.getString("StudentID");
				fName = rs2.getString("Firstname");
				lName = rs2.getString("Lastname");
				email = rs2.getString("email");
			
				student= new Student(sID, fName, lName, email);
				
			}
			rs2.close();
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}

		return student;
		
		
	}

	public String getAllCoursesSequential(List<Course> courseResult)
	{
	
		String cID, cName, cGrade,time,prof,room ;
		cName = EOF;
		cGrade = EOF;
		cID = EOF;
		time=EOF;
		prof=EOF;
		room=EOF;
		Course course;

		result = null;
		try
		{
			cmdString = "Select * from Courses";
			rs2 = st1.executeQuery(cmdString);
			
		}
		catch (Exception e)
		{
			processSQLError(e);
		}
		try
		{
			while (rs2.next())
			{
				cID = rs2.getString("CourseID");
				cName = rs2.getString("Name");
				time = rs2.getString("Time");
				prof = rs2.getString("Prof");
				room = rs2.getString("Room");
				cGrade=getCourseGrade(cID);
				course = new Course(cID, cName, time,prof,room,cGrade);
				courseResult.add(course);
			}
			rs2.close();
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}

		return result;
	}

	public Course getCourseRandom(Course newCourse)
	{
		Course course=null;
		String cID, cName, cGrade;
		cID = EOF;
		cName = EOF;
		cGrade = EOF;
	
		try
		{
			cmdString = "Select * from Courses where CourseID='" + newCourse.getCourseID() +"'";
			rs3 = st1.executeQuery(cmdString);
		
			while (rs3.next())
			{
				cID = rs3.getString("CourseID");
				cName = rs3.getString("Name");
				cGrade = getCourseGrade(cID);
				course = new Course(cID, cName, cGrade);
	
			}
			rs3.close();
		} catch (Exception e)
		{
			processSQLError(e);
		}
		return course;
	}
	
	public String updateStudent(String SID,String first, String last,  String email)
	{
		String values;
		

		result = null;
		try
		{
			
			values = "StudentID='" + SID  +"', FirstName='" +first
			         +"', Lastname='" + last
			         +"', email='" + email +"'";
			
			cmdString = "Update Student " +" Set " +values +" ";
		
			updateCount = st1.executeUpdate(cmdString); 
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		return result;
	}

	public String insertDegreeCourse(Course course)
	{
		String values;

		result = null;
		try
		{
			values = "'" + course.getCourseID()
			         +"', 1";
			cmdString = "Insert into DegreeCourses " +" Values(" +values +")";
			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		return result;
	}
	
	public String getCurrentlyEnrolledList(List<Course> courses)
	{
		result=null;
		Course course;
		String cID, cName, prof, time, room;
		cID = EOF;
		cName = EOF;
		try
		{
			cmdString = "Select * from Enrolment where Grade='IP'";
			rs3 = st1.executeQuery(cmdString);
			
			while (rs3.next())
			{
				cID = rs3.getString("CourseID");
				cName=getCourseName(cID);
				prof = rs3.getString("Prof");
				time = rs3.getString("Time");
				room = rs3.getString("Room");
				
				course = new Course(cID, cName, time, prof ,room);
				courses.add(course);
			}
			
			rs3.close();
		} catch (Exception e)
		{
			processSQLError(e);
		}
		return result;
	
	}
	
	public String insertEnrollment(Course course)
	{

		Section sec = course.getSection(0);
		String prof = sec.getProf();
		String time = sec.getTime();
		String room = sec.getRoom();
		String grade = "IP";
		sec.setGrade("IP");
		String values;

		result = null;
		try
		{

			values = "'" +course.getCourseID()
			         +"', '1"+
			         "', '" + time +"', '" + prof + "', '" + room + "', '" + grade +"'";


			cmdString = "Insert into Enrolment " +" Values(" +values +")";
			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		return result;
		
	}
	
	public String getEnrollment(Course course){
		result = null;
		String prof,time, room;
		try
		{
			cmdString = "Select * from Enrolment Where CourseID=" + "'" + course.getCourseID() + "'";
			rs2=st1.executeQuery(cmdString);
			
			if(rs2.next())
			{

				prof = rs2.getString("Prof");
				time = rs2.getString("Time");
				room=rs2.getString("Room");
				course = new Course(course.getCourseID(),"1",time,prof,room,"IP");
			}
			rs2.close();
		}catch(Exception e)
		{
			result = processSQLError(e);
		}
		return result;
	}
	
	public String deleteEnrollment(Course course)
	{
		
		result = null;
		try
		{
			cmdString = "Delete from Enrolment where CourseID='" + course.getCourseID()+"'";
		
			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		return result;
	}
	
	public String updateEnrollment(Course course)
	{
		return "";
	}


	

	public String checkWarning(Statement st, int updateCount)
	{
		String result;

		result = null;
		try
		{
			SQLWarning warning = st.getWarnings();
			if (warning != null)
			{
				result = warning.getMessage();
			}
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		if (updateCount != 1)
		{
			result = "Tuple not inserted correctly.";
		}
		return result;
	}

	public String processSQLError(Exception e)
	{
		String result = "*** SQL Error: " + e.getMessage();

		
		e.printStackTrace();
		
		return result;
	}

	@Override
	public String getFinancialAidSequential(List<FinancialAid> list) 
	{
		
		result=null;
		FinancialAid fa;
		String name;
		double amt;
		try
		{
			cmdString = "Select * from FinancialAid";
			rs3 = st1.executeQuery(cmdString);
			
			while (rs3.next())
			{
				name = rs3.getString("Name");
				amt = Double.parseDouble(rs3.getString("Amount"));
				fa = new FinancialAid(name, amt);
				list.add(fa);
			}
			rs3.close();
		} catch (Exception e)
		{
			processSQLError(e);
		}
		return result;
	
	}

	@Override
	public String insertFinancialAid(FinancialAid currentAid)
	{
		
		result = null;
		try
		{
			cmdString = "Insert into FinancialAid " +" Values('" +currentAid.getAidName()+"',"+currentAid.getAidAmount()+")";

			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		return result;
	}

	@Override
	public String updateFinancialAid(FinancialAid currentAid)
	{
	
		
		result = null;
		try
		{			
			cmdString = "Update FinancialAid " +" Set Amount='"+currentAid.getAidAmount()+"' Where Name='"+currentAid.getAidName()+"'";
			
			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		return result;
	}

	@Override
	public String deleteFinancialAid(FinancialAid currentAid)
	{
	
		
		result = null;
		try
		{
			cmdString = "Delete from FinancialAid where Name='" + currentAid.getAidName()+"'";
			
			updateCount = st1.executeUpdate(cmdString);
			result = checkWarning(st1, updateCount);
		}
		catch (Exception e)
		{
			result = processSQLError(e);
		}
		
		return result;
	}

	@Override
	public ArrayList<FinancialAid> getFinancialAidRandom(FinancialAid currentFAid) {
		
		
		FinancialAid fAid;
		String aidName;
		double aidAmt;
		aidList = new ArrayList<FinancialAid>();
		try
		{
			cmdString = "Select * from FinancialAid where Name='" + currentFAid.getAidName() +"'";
			rs3 = st1.executeQuery(cmdString);
			
			while (rs3.next())
			{
				aidName = rs3.getString("Name");
				aidAmt = rs3.getDouble("Amount");
				fAid = new FinancialAid(aidName, aidAmt);
				aidList.add(fAid);
			}
			rs3.close();
		} catch (Exception e)
		{
			processSQLError(e);
		}
		return aidList;
	}

	@Override
	public String deleteDegreeCourse(Course course) 
	{	
			String values;

			result = null;
			try
			{
				values = "'" + course.getCourseID() +"'";
				cmdString = "Delete from DegreeCourses where CourseID=" +values;
				
				updateCount = st1.executeUpdate(cmdString);
				result = checkWarning(st1, updateCount);
			}
			catch (Exception e)
			{
				result = processSQLError(e);
			}
			return result;
		
	}

	
}
