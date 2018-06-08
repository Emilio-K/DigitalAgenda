package comp3350.digitalagenda.business;

import java.text.DecimalFormat;
import java.util.List;

import comp3350.digitalagenda.objects.Course;

public class CalculateGPA
{
	public static String gpa(List<Course> elements)
	{
		final String[] grades = {"A+","A","B+","B","C+","C","D","F","IP"};
		final double[] values = {4.5,4.0,3.5,3.0,2.5,2.0,1.0,0.0,0.0};
		String gpa;
		String grade;
		Course element;
		double gradeTotal;
		int elementCount;
		int validGrades;
		int missingGrades;
		int count;
		boolean found;

		
		validGrades = 0;
		missingGrades = 0;
		gradeTotal = 0;
		gpa = " ";
		
		
		if ((elements!=null) && (elements.size()>0))
		{
			for (elementCount=0; elementCount<elements.size(); elementCount++)
			{
				grade = "";
				if (!(elements.get(elementCount) instanceof Course))
				{	
					missingGrades = 0;
					validGrades = 0;
					elementCount = elements.size()+1;
					gpa = "?";
				}
				else
				{

					element = (Course) elements.get(elementCount);
					grade = ((Course) element).getGrade();
					
					found = false;
					if (grade.trim().equals(""))
					{	
						missingGrades++;
					}
					else if(grade.trim().equals("IP")){
						missingGrades++;
					}
					else
					{
						for (count=0; count<grades.length&&!found; count++)
						{
							if (grades[count].equals(grade))
							{
								found = true;
								gradeTotal += values[count];
								validGrades++;
							}
						}
					}
				}
			}
			if (((validGrades+missingGrades)==elements.size()) && (validGrades>0))
			{
				double gp;
				gp=(gradeTotal/validGrades);
				DecimalFormat df = new DecimalFormat("#.00");
				
				gpa=df.format(gp);
			}
			else if (missingGrades != elements.size())
			{	
			
				gpa = "?";
			}
		}
		return gpa;
	}
}
