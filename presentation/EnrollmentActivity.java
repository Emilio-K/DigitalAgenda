package comp3350.digitalagenda.presentation;



import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import comp3350.digitalagenda.R;
import comp3350.digitalagenda.business.AccessEnrollment;
import comp3350.digitalagenda.objects.Course;

public class EnrollmentActivity extends Activity{

	private AccessEnrollment enrollmentAccess; 
	private ArrayAdapter<Course> enrollmentAdapter;
	private ArrayList<Course> currentEnrollmentList;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrollment);

        enrollmentAccess = new AccessEnrollment();
        currentEnrollmentList = new ArrayList<Course>();
        
        TextView pageTitle = (TextView)findViewById(R.id.enrollmentTitle);
        pageTitle.setText("Current Weekly Schedule");
        
        populateEnrollmentList();
        
       
    }

	private void populateEnrollmentList() {
		String accessEnrollmentResult = enrollmentAccess.getAllEnrollmentData(currentEnrollmentList);
        System.out.println("This is aer: " + currentEnrollmentList);
        
      
        
        if (accessEnrollmentResult != null){
        
        	Messages.fatalError(this, accessEnrollmentResult);
        	
        }else{
        
        	enrollmentAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, currentEnrollmentList)
        	{@Override
                public View getView(int position, View convertView, ViewGroup parent) 
                {
                    View view = super.getView(position, convertView, parent);

                    TextView CourseTitle = (TextView) view.findViewById(android.R.id.text1);
                    TextView CourseInfo = (TextView) view.findViewById(android.R.id.text2);
                    
                    CourseTitle.setText(currentEnrollmentList.get(position).getCourseID() + " - " + currentEnrollmentList.get(position).getCourseName());
                    CourseInfo.setText(currentEnrollmentList.get(position).getSectionInfo());
                return view;
                }
        	};
        	final ListView listView = (ListView)findViewById(R.id.listCurrentEnrollment);
        	listView.setAdapter(enrollmentAdapter);
        }
	}

	@Override
	protected void onResume(){
		super.onResume();
		
		populateEnrollmentList();
		enrollmentAdapter.notifyDataSetChanged();
		
	}
	
	
}
