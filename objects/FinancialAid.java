package comp3350.digitalagenda.objects;


public class FinancialAid
{

	private String aidName;
	private double aidAmount;
	
	public FinancialAid(){
		
		this.aidName = "";
		this.aidAmount = 0.00;
		
	}
	public FinancialAid(String title)
	{
			
			this.aidName = title;
			this.aidAmount = 0.00;		
	}
	public FinancialAid(double amount)
	{
		
		this.aidName = "";
		this.aidAmount = amount;
	}
	
	public FinancialAid(String title, double amount)
	{
		
		this.aidName = title;
		this.aidAmount = amount;		
	}
	
	public String getAidName()
	{
		return aidName;
	}

	public void setAidName(String aidName)
	{
		this.aidName = aidName;
	}

	public double getAidAmount()
	{
		return aidAmount;
	}

	public void setAidAmount(double aidAmount)
	{
		this.aidAmount = aidAmount;
	}
	
	public String toString()
	{
		return "FinancialAid: " +aidName +" " +aidAmount;
	}
	
	public boolean equals(Object object)
	{
		boolean result;
		FinancialAid fAid;
		
		result = false;
		
		if (object instanceof FinancialAid)
		{
			fAid = (FinancialAid) object;
			if (((fAid.aidName == null) && (aidName == null)) || (fAid.aidName.equals(aidName)))
			{
				result = true;
			}
		}
		return result;
	}
	
	


	
}
