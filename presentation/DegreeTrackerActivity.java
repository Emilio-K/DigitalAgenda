package comp3350.digitalagenda.presentation;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import comp3350.digitalagenda.R;
import comp3350.digitalagenda.business.AccessCourses;
import comp3350.digitalagenda.business.AccessEnrollment;
import comp3350.digitalagenda.business.AccessStudents;
import comp3350.digitalagenda.business.CourseCommonality;
import comp3350.digitalagenda.objects.Course;
import comp3350.digitalagenda.objects.Degree;

public class DegreeTrackerActivity extends Activity {

    private AccessCourses accessCourses;

    private AccessStudents accessStudents;
    
    private AccessEnrollment accessEnroll;
    
    private Degree degree;
    private ArrayList<Course> studentCourseList;
    private ArrayList<Course> degreeCourseList;
    private ArrayList<Course> comparisonList;
    private ArrayList<Course> allCourseList;
    private ArrayList<Course> enrollmentList;
    private ArrayAdapter<Course> courseArrayAdapter;
    private ArrayAdapter<Course> spinnerArrayAdapter;
    private int selectedCoursePosition = -1;
    private String displayGrade = "";
    private ListView listView;
    private TextView GPALine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_degreetracker);

        accessCourses = new AccessCourses();
        accessStudents = new AccessStudents();
        accessEnroll = new AccessEnrollment();
       
        studentCourseList = new ArrayList<Course>();
        degreeCourseList = new ArrayList<Course>();
        allCourseList = new ArrayList<Course>();
        enrollmentList = new ArrayList<Course>();
        
        String result = accessCourses.getDegreeCourses(degreeCourseList);
        String accessStudResult = accessCourses.getStudentCourses(studentCourseList);
        String allCourseResult = accessCourses.getAllCourses(allCourseList);
        String enrollResult = accessEnroll.getAllEnrollmentData(enrollmentList);
        
        comparisonList = CourseCommonality.getCommon(degreeCourseList, studentCourseList);
        degree = new Degree (degreeCourseList, "Degree 1", 0);
        
        Button buttonDelete = (Button)findViewById(R.id.buttonCourseDelete);
        Button buttonEnroll = (Button)findViewById(R.id.buttonDTEnroll);
        Button buttonAdd = (Button)findViewById(R.id.buttonCourseAdd);
        buttonDelete.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
        buttonEnroll.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
        buttonAdd.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        
        if (result != null && accessStudResult != null && enrollResult !=null)

        {
            Messages.fatalError(this, result);
        }
        else
        {
            initializeAdapter();
            
	        GPALine = (TextView)findViewById(R.id.GPAline);
	        GPALine.setText("GPA: " + "" + accessStudents.getGPA(degree.getCourseList()));
	        
	        TextView legend = (TextView)findViewById(R.id.completeLegend);
	        legend.setText("Completed");
	        legend.setTextColor(Color.rgb(0, 160, 0));
	        TextView legend2 = (TextView)findViewById(R.id.IncompleteLegend);
	        legend2.setText("Incomplete");
	        legend2.setTextColor(Color.RED);
	        TextView legend3 = (TextView)findViewById(R.id.inprogressLegend);
	        legend3.setText("In Progress");
	        legend3.setTextColor(Color.BLUE);
            
            listView = (ListView)findViewById(R.id.listCourses);
            listView.setAdapter(courseArrayAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                	final int position = pos;
                	final Button button = (Button)findViewById(R.id.buttonCourseDelete);
                	final Button enrollButton = (Button)findViewById(R.id.buttonDTEnroll);
                	
                	   if (position == selectedCoursePosition) {
                           listView.setItemChecked(position, false);
                           button.setEnabled(false);
                           button.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
                           enrollButton.setEnabled(false);
                           enrollButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));

                           selectedCoursePosition = -1;
                       } else {
                           listView.setItemChecked(position, true);
                           button.setEnabled(true);
                           button.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                           if(enrollmentList.indexOf(listView.getItemAtPosition(listView.getCheckedItemPosition()))==-1){
                           enrollButton.setEnabled(true);
                           enrollButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                           }else{
                        	   enrollButton.setEnabled(false);
                        	   enrollButton.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));
                           }
                           selectedCoursePosition = position;
                           selectCourseAtPosition(position);
                       }
                	
                	 button.setOnClickListener(new View.OnClickListener() {
                         public void onClick(View v) {
                             buttonCourseDeleteOnClick(v);

                    if (position == selectedCoursePosition) {
                        listView.setItemChecked(position, false);
                        button.setEnabled(false);
                        button.getBackground().setColorFilter(new LightingColorFilter(0x751baf, 0x751baf));

                        selectedCoursePosition = -1;
                    } else {
                        listView.setItemChecked(position, true);
                        button.setEnabled(true);
                        button.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                        if(enrollmentList.indexOf(listView.getItemAtPosition(listView.getCheckedItemPosition()))==-1){
                            enrollButton.setEnabled(true);
                            enrollButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                            }else{
                         	   enrollButton.setEnabled(false);
                         	   enrollButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
                            }
                        selectedCoursePosition = position;
                        selectCourseAtPosition(position);
                    }
                         }
                     });
                }
            });
            
        }//else
        
        if (allCourseResult != null){
        	Messages.fatalError(this, allCourseResult);
        }else{
        	Spinner spinner = (Spinner)findViewById(R.id.courseListSpinner);
        	
        	spinnerArrayAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_spinner_item, allCourseList );
        	spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        	spinner.setAdapter(spinnerArrayAdapter);
        }
        
    }


	private void initializeAdapter() {
		courseArrayAdapter = new ArrayAdapter<Course>(this, android.R.layout.simple_list_item_activated_2, android.R.id.text1, degreeCourseList )
		{
		    @Override
		    public View getView(int position, View convertView, ViewGroup parent) 
		    {
		        View view = super.getView(position, convertView, parent);

		        TextView courseTitle = (TextView) view.findViewById(android.R.id.text1);
		        TextView courseDescription = (TextView) view.findViewById(android.R.id.text2);

		        colourCodeCourses(position,courseTitle,courseDescription);
		       
		        return view;
		    }
		};
	}
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_courses, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void selectCourseAtPosition(int position) {
        @SuppressWarnings("unused")
		Course selected = courseArrayAdapter.getItem(position);
    }
    
    public void buttonEnrollOnClick(View v){
    	ListView list = (ListView)findViewById(R.id.listCourses);
    	int position = list.getCheckedItemPosition();
    	Course course = (Course)list.getItemAtPosition(position);
    	TextView courseID = (TextView)findViewById(android.R.id.text1);
    	TextView courseName = (TextView)findViewById(android.R.id.text2);
    	
    	accessEnroll.insertEnrollmentData(course);
    	degree.addCourse(course);
    	
    	colourCodeCourses(position, courseID, courseName);
    	courseArrayAdapter.notifyDataSetChanged();
    }

    public void buttonCourseAddOnClick(View v) {
    	Spinner spinner = (Spinner)findViewById(R.id.courseListSpinner);
        Course course = (Course) spinner.getSelectedItem();
        String result;

        result = validateCourseData(course, true);
      
        if (result == null) {
            result = accessCourses.insertDegreeCourse(course);
        	degree.getCourseList();
        	degreeCourseList.add(course);
        	comparisonList = CourseCommonality.getCommon(degreeCourseList, studentCourseList);
        	GPALine.setText("GPA: " + "" + accessStudents.getGPA(degree.getCourseList()));
			courseArrayAdapter.notifyDataSetChanged();
        } else {
            Messages.warning(this, result);
        }
    }
    
    public void buttonCourseDeleteOnClick(View v) {
    	
    	if (degree.getCourseList().isEmpty() != true){
    		buttonDeleteClick(v);
    	}
    	courseArrayAdapter.notifyDataSetChanged();
    }
    
    private void buttonDeleteClick(View v){
    	
    	ListView list = (ListView)findViewById(R.id.listCourses);
    	int position = list.getCheckedItemPosition();
    	
    	String result = null;
    	 
        Course course = (Course) list.getItemAtPosition(position);
        result = accessCourses.deleteDegreeCourse(course);
        
        if (result == null) {
        	degree.getCourseList();
        	degreeCourseList.remove(course);
			courseArrayAdapter.notifyDataSetChanged();
			GPALine.setText("GPA: " + "" + accessStudents.getGPA(degree.getCourseList()));
        } else {
            Messages.warning(this, result);
        }
        courseArrayAdapter.notifyDataSetChanged();
    	
    }

    private String validateCourseData(Course course, boolean isNewCourse) {

    	if (isNewCourse && degreeCourseList.contains(course)){
    		return "Course ID " + course.getCourseID() + " already exists.";
    	}
       
        return null;
    }

	private void colourCodeCourses(int position, TextView courseTitle, TextView courseDescription) {
		displayGrade = "";
		if (degree.getCourse(position).getGrade()!="N")
		{
			displayGrade = " {" + degree.getCourse(position).getGrade() + "} ";
		}
		courseTitle.setText(degree.getCourse(position).getCourseID()+ " " + displayGrade );
		courseDescription.setText(degree.getCourse(position).getCourseName());
		
		if (comparisonList.contains(degree.getCourse(position)))
		{
			if (degree.getCourse(position).getGrade().equals("IP")){
				courseTitle.setTextColor(Color.rgb(0,0,160));
			}else{
			courseTitle.setTextColor(Color.rgb(0, 160, 0));
			}
		}
		else
		{
			courseTitle.setTextColor(Color.rgb(160, 0, 0));
		}
	}
    
}
