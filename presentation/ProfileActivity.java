package comp3350.digitalagenda.presentation;

import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import comp3350.digitalagenda.R;
import comp3350.digitalagenda.business.AccessStudents;

public class ProfileActivity extends Activity {
	AccessStudents sa = new AccessStudents();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        Button submitButton = (Button)findViewById(R.id.profileSubmitButton);
        submitButton.setOnClickListener(buttonListener);
        submitButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        
        fillFieldsOnCreate();
        
	}
	
	private OnClickListener buttonListener = new OnClickListener(){

			public void onClick(View v) {

				EditText firstNameField = (EditText)findViewById(R.id.firstNameSpace);
				EditText lastNameField = (EditText)findViewById(R.id.lastNameSpace);
				EditText studentNumberField = (EditText)findViewById(R.id.studentNumberSpace);
				EditText studentEmailField = (EditText)findViewById(R.id.emailSpace);
				sa.editStudentInfo(studentNumberField.getText().toString(),firstNameField.getText().toString(), 
						lastNameField.getText().toString(), studentEmailField.getText().toString());
				
				finish();
			}
		};

	protected void fillFieldsOnCreate(){
		
		EditText firstNameField = (EditText)findViewById(R.id.firstNameSpace);
		EditText lastNameField = (EditText)findViewById(R.id.lastNameSpace);
		EditText studentNumberField = (EditText)findViewById(R.id.studentNumberSpace);
		EditText studentEmailField = (EditText)findViewById(R.id.emailSpace);
			
        	firstNameField.setText(sa.getStudentInfo().getFirstName());
        	lastNameField.setText(sa.getStudentInfo().getLastName());
        	studentNumberField.setText(sa.getStudentInfo().getSID());
        	studentEmailField.setText(sa.getStudentInfo().getEmail());
        
        
	}
	
	
       
	
		
	}
