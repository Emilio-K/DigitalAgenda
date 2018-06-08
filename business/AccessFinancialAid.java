package comp3350.digitalagenda.business;

import java.util.ArrayList;
import java.util.List;

import comp3350.digitalagenda.application.Main;
import comp3350.digitalagenda.application.Services;
import comp3350.digitalagenda.objects.FinancialAid;
import comp3350.digitalagenda.persistence.DataAccess;

public class AccessFinancialAid {
	
	private DataAccess dataAccess;
	private FinancialAid fAid;
	private ArrayList<FinancialAid> listFAid;
	private int currentAid;
	private double total;
	
	public AccessFinancialAid()
	{
		dataAccess = (DataAccess) Services.getDataAccess(Main.dbName);
		fAid = null;
		listFAid = null;
		currentAid = 0;
	}
	
	public String getAllFinancialAid(List<FinancialAid> thefAidsList)
    {
        thefAidsList.clear();
        return dataAccess.getFinancialAidSequential(thefAidsList);
    }
	
	public FinancialAid getRandom(String aidName)
	{
		listFAid = dataAccess.getFinancialAidRandom(new FinancialAid(aidName));
		currentAid = 0;
		if (currentAid < listFAid.size())
		{
			fAid = (FinancialAid) listFAid.get(currentAid);
			currentAid++;
		}
		else
		{
			listFAid = null;
			fAid = null;
			currentAid = 0;
		}
		return fAid;
	}
	
	public String insertFinancialAid(FinancialAid currentAid)
	{
		return dataAccess.insertFinancialAid(currentAid);
	}

	public String updateFinancialAid(FinancialAid currentAid)
	{
		return dataAccess.updateFinancialAid(currentAid);
	}

	public String deleteFinancialAid(FinancialAid currentAid)
	{
		return dataAccess.deleteFinancialAid(currentAid);
	}
	
	public String getTotalAid(ArrayList<FinancialAid> fAids)
	{
		total = CalculateTotalFinancialAid.totalAid(fAids);
	
		return Double.toString(total);
	}
	
}
