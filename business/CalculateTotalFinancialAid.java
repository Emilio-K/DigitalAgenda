package comp3350.digitalagenda.business;

import java.util.ArrayList;

import comp3350.digitalagenda.objects.FinancialAid;

public class CalculateTotalFinancialAid {
	public static double totalAid(ArrayList<FinancialAid> theFAids){
		double total = 0;
		
		for(int i =0; i<theFAids.size();i++){
			total += theFAids.get(i).getAidAmount();
		}
		
		return total;
	}

}
