package comp3350.digitalagenda.business;

import java.util.*;

import comp3350.digitalagenda.objects.Course;

public class CourseCommonality 
{
	
	public static ArrayList<Course> getCommon(ArrayList<Course> listA, ArrayList<Course> listB)
	{
		ArrayList<Course> common;
		
		if(listA != null && listB != null && !listA.isEmpty() && !listB.isEmpty())
		{
			common = new ArrayList<Course>(listA);		
			common.retainAll(listB);
		}
		else
		{
			common = new ArrayList<Course>();			
		}
		return common;
	}
	
	public static ArrayList<Course> getExclusiveTo(ArrayList<Course> listA, ArrayList<Course> listB)
	{
		ArrayList<Course> unCommon;
		if(listA != null && listB != null && !listA.isEmpty() && !listB.isEmpty())
		{
			unCommon = new ArrayList<Course>(listA);		
			unCommon.removeAll(listB);
		}
		else if(listA != null && listB == null && !listA.isEmpty())
		{
			unCommon = new ArrayList<Course>(listA);
		}
		else if(listA != null && !listA.isEmpty() && listB.isEmpty())
		{
			unCommon = new ArrayList<Course>(listA);
		}
		else if(listA == null && listB != null && !listB.isEmpty())
		{
			unCommon = new ArrayList<Course>(listB);
		}
		else if(listB != null && !listB.isEmpty() && listA.isEmpty())
		{
			unCommon = new ArrayList<Course>(listB);
		}
		else
		{
			unCommon = new ArrayList<Course>();
		}
		return unCommon;
	}
}
