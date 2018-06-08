package comp3350.digitalagenda.objects;

public class Section implements Comparable<Section>
{
	private static int currID = 1; 
	private int sectionID;
	private String time; //eg MWF 1330 = (Mon Wed Fri 1:30pm)
	private String prof;
	private String room;
	private String grade;
	
	public Section(String time, String prof, String room)
	{
		sectionID = currID;
		currID++;
		this.time = time;
		this.prof = prof;
		this.room = room;
		grade = "N";
	}

	public int getSectionID()
	{
		return sectionID;
	}

	public String getTime()
	{
		return time;
	}

	public String getProf()
	{
		return prof;
	}

	public String getRoom()
	{
		return room;
	}

	public String getGrade() 
	{
		return grade;
	}

	public void setGrade(String grade) 
	{
		this.grade = grade;
	}

	public String toString()
	{
		return time + "\n" + room + "\n" + prof;
	}
		
	public int compareTo(Section s)
	{
		int Cmp;
		if(sectionID == s.getSectionID())
		{
			Cmp = 0;
		}
		else if (sectionID < s.getSectionID())
		{
			Cmp = 1;
		}
		else
		{
			Cmp = -1;
		}
		return Cmp;
	}
}