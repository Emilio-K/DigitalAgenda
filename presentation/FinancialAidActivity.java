package comp3350.digitalagenda.presentation;




import java.util.ArrayList;

import comp3350.digitalagenda.R;
import comp3350.digitalagenda.business.AccessFinancialAid;
import comp3350.digitalagenda.objects.FinancialAid;
import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class FinancialAidActivity extends Activity {
	
	 private AccessFinancialAid financialAidAccess;
	 private ArrayAdapter<FinancialAid> aidArrayAdapter;
	 private ArrayList<FinancialAid> financialAidList;
	 private int selectedAidPosition = -1;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_financialaid);
	    
	    financialAidList = new ArrayList<FinancialAid>();
	    financialAidAccess = new AccessFinancialAid();
	    
	    Button updateButton = (Button)findViewById(R.id.buttonAidUpdate);
		Button deleteButton = (Button)findViewById(R.id.buttonAidDelete);
		Button createButton = (Button)findViewById(R.id.buttonAidCreate);
		updateButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
		deleteButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
		createButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));

	    String accessFinancialResult = financialAidAccess.getAllFinancialAid(financialAidList);
	    
	    if (accessFinancialResult != null)

        {
            Messages.fatalError(this, accessFinancialResult);
        }
        else
        {
            aidArrayAdapter = new ArrayAdapter<FinancialAid>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, financialAidList)
            { 
            	@Override
            	public View getView(int position, View convertView, ViewGroup parent) 
			    {
			        View view = super.getView(position, convertView, parent);
			
			        TextView AidName = (TextView) view.findViewById(android.R.id.text1);
			        TextView AidAmount = (TextView) view.findViewById(android.R.id.text2);
			        
			        
			        AidName.setText(financialAidList.get(position).getAidName());
			        AidAmount.setText(Double.toString(financialAidList.get(position).getAidAmount()));
			        return view;
			    }
			};
            		
		    final ListView listView = (ListView)findViewById(R.id.listFinancialAid);
	        listView.setAdapter(aidArrayAdapter);
	        
	        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	        	@Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        		Button updateButton = (Button)findViewById(R.id.buttonAidUpdate);
	        		Button deleteButton = (Button)findViewById(R.id.buttonAidDelete);
	        		EditText editName = (EditText)findViewById(R.id.editAidName);
	        		if (position == selectedAidPosition) {
                        listView.setItemChecked(position, false);
                        updateButton.setEnabled(false);
                        updateButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
                        deleteButton.setEnabled(false);
                        deleteButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
                        editName.setEnabled(true);
                        selectedAidPosition = -1;
                    } else {
                        listView.setItemChecked(position, true);
                        updateButton.setEnabled(true);
                        updateButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                        deleteButton.setEnabled(true);
                        deleteButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                        editName.setEnabled(false);
                        editName.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
                        selectedAidPosition = position;
                        selectAidAtPosition(position);
                    }	        		
	        	}
			});
	        
	        TextView aidTotal = (TextView)findViewById(R.id.veiwAid);
	        aidTotal.setText(financialAidAccess.getTotalAid(financialAidList));
	        
        }   
	}
	
    public void selectAidAtPosition(int position) {
    	FinancialAid selected = aidArrayAdapter.getItem(position);

        EditText editName = (EditText)findViewById(R.id.editAidName);
        EditText editAmount = (EditText)findViewById(R.id.editAidAmount);

        editName.setText(selected.getAidName());
        editAmount.setText(Double.toString(selected.getAidAmount()));
    }
    
    
    public void buttonAidCreateOnClick(View v) {
        FinancialAid fAid = createAidFromEditText();
        String result;

        result = validateAidData(fAid, true);
        if (result == null) {
            result = financialAidAccess.insertFinancialAid(fAid);
            if (result == null) {
                financialAidAccess.getAllFinancialAid(financialAidList);
                aidArrayAdapter.notifyDataSetChanged();
                int pos = financialAidList.indexOf(fAid);
                if (pos >= 0) {
                    ListView listView = (ListView)findViewById(R.id.listFinancialAid);
                    listView.setSelection(pos);
                }
                TextView aidTotal = (TextView)findViewById(R.id.veiwAid);
    	        aidTotal.setText(financialAidAccess.getTotalAid(financialAidList));
            } else {
                Messages.fatalError(this, result);
            }
        } else {
            Messages.warning(this, result);
        }
    }
    
    public void buttonAidUpdateOnClick(View v) {
        FinancialAid fAid = createAidFromEditText();
        String result;

        result = validateAidData(fAid, false);
        if (result == null) {
            result = financialAidAccess.updateFinancialAid(fAid);
            if (result == null) {
            	financialAidAccess.getAllFinancialAid(financialAidList);
                aidArrayAdapter.notifyDataSetChanged();
                int pos = financialAidList.indexOf(fAid);
                if (pos >= 0) {
                    ListView listView = (ListView)findViewById(R.id.listFinancialAid);
                    listView.setSelection(pos);
                }
                TextView aidTotal = (TextView)findViewById(R.id.veiwAid);
    	        aidTotal.setText(financialAidAccess.getTotalAid(financialAidList));
            } else {
                Messages.fatalError(this, result);
            }
        } else {
        	Messages.warning(this, result);
        }
        
        
    }
    
    public void buttonAidDeleteOnClick(View v) {
        FinancialAid fAid = createAidFromEditText();
        String result;

        result = financialAidAccess.deleteFinancialAid(fAid);
        if (result == null) {
            int pos = financialAidList.indexOf(fAid);
            if (pos >= 0) {
                ListView listView = (ListView) findViewById(R.id.listFinancialAid);
                listView.setSelection(pos);
            }
            financialAidAccess.getAllFinancialAid(financialAidList);
            aidArrayAdapter.notifyDataSetChanged();
            TextView aidTotal = (TextView)findViewById(R.id.veiwAid);
	        aidTotal.setText(financialAidAccess.getTotalAid(financialAidList));
        } else {
        	Messages.warning(this, result);
        }
    }
	
    private FinancialAid createAidFromEditText() {
        EditText editName = (EditText)findViewById(R.id.editAidName);
        EditText editAmount = (EditText)findViewById(R.id.editAidAmount);
        FinancialAid fAid;
        String aidName = editName.getText().toString();
        String aidAmt = editAmount.getText().toString();
        
        if(aidAmt != null && !aidAmt.isEmpty()){
        	fAid = new FinancialAid(aidName, Double.parseDouble(aidAmt));
        }else{
        	fAid = new FinancialAid(aidName);
        }
        
        return fAid;
    }
    
    private String validateAidData(FinancialAid fAid, boolean isNewFAid) {
        if (fAid.getAidName().length() == 0) {
            return "Financial Aid Name required";
        }
        if (isNewFAid && financialAidList.contains(fAid)) {
            return "Financial Aid: " + fAid.getAidName() + " already exists.";
        }
        if (isNewFAid && financialAidAccess.getRandom(fAid.getAidName()) != null) {
            return "Financial Aid: " + fAid.getAidName() + " already exists.";
        }

        return null;
    }
  
}
	
	
