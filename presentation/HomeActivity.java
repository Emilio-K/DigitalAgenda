package comp3350.digitalagenda.presentation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import comp3350.digitalagenda.R;
import comp3350.digitalagenda.application.Main;


public class HomeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        copyDatabaseToDevice();
        
        Main.startUp();
      
        setContentView(R.layout.activity_home);
        
        Button profileButton = (Button)findViewById(R.id.buttonProfile);
        Button calendarButton = (Button)findViewById(R.id.buttonCalendar);
        Button degreeTrackerButton = (Button)findViewById(R.id.buttonDegreeTracker);
        Button enrollmentButton = (Button)findViewById(R.id.buttonEnrollment);
        Button financialAidButton = (Button)findViewById(R.id.buttonFinancial);
        
        profileButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        calendarButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        degreeTrackerButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        enrollmentButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));
        financialAidButton.getBackground().setColorFilter(new LightingColorFilter(0xFFF200, 0xFFF200));

    }

    @Override
    protected void onDestroy() 
    {
        super.onDestroy();

        Main.shutDown();
    }
    
    private void copyDatabaseToDevice() {
    	final String DB_PATH = "db";

    	String[] assetNames;
    	Context context = getApplicationContext();
    	File dataDirectory = context.getDir(DB_PATH, Context.MODE_PRIVATE);
    	AssetManager assetManager = getAssets();
    	
    	try {

    		assetNames = assetManager.list(DB_PATH);
    		for (int i = 0; i < assetNames.length; i++) {
    			assetNames[i] = DB_PATH + "/" + assetNames[i];
    		}

    		copyAssetsToDirectory(assetNames, dataDirectory);
    		
    		Main.setDBPathName(dataDirectory.toString() + "/" + Main.dbName);

    	} catch (IOException ioe) {
    		Messages.warning(this, "Unable to access application data: " + ioe.getMessage());
    	}
    }

    public void copyAssetsToDirectory(String[] assets, File directory) throws IOException {
    	AssetManager assetManager = getAssets();

    	for (String asset : assets) {
    		String[] components = asset.split("/");
    		String copyPath = directory.toString() + "/" + components[components.length - 1];
    		char[] buffer = new char[1024];
    		int count;
    		
    		File outFile = new File(copyPath);
    		
    		if (!outFile.exists()) {
    			InputStreamReader in = new InputStreamReader(assetManager.open(asset));
	    		FileWriter out = new FileWriter(outFile);
	    		
	    		count = in.read(buffer);
	    		while (count != -1) {
	    			out.write(buffer, 0, count);
	        		count = in.read(buffer);
	    		}
	    		
	    		out.close();
	    		in.close();
    		}
    	}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
       
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }
    
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {

        return super.onOptionsItemSelected(item);
    }

    public void buttonCalendarOnClick(View v) 
    {
        Intent calendarIntent = new Intent(HomeActivity.this, CalendarActivity.class);
        HomeActivity.this.startActivity(calendarIntent);
    }

    public void buttonDegreeTrackerOnClick(View v) 
    {
    	Intent degreeTrackerIntent = new Intent(HomeActivity.this, DegreeTrackerActivity.class);
        HomeActivity.this.startActivity(degreeTrackerIntent);
    }
    
    public void buttonEnrollmentOnClick(View v)
    {
    	Intent enrollmentIntent = new Intent (HomeActivity.this, EnrollmentActivity.class);
    	HomeActivity.this.startActivity(enrollmentIntent);
    }
    
    public void buttonProfileOnClick(View v)
    {
    	Intent profileIntent = new Intent (HomeActivity.this, ProfileActivity.class);
    	HomeActivity.this.startActivity(profileIntent);
    	
    }
    
    public void buttonFinancialOnClick(View v)
    {
    	Intent financialIntent = new Intent (HomeActivity.this, FinancialAidActivity.class);
    	HomeActivity.this.startActivity(financialIntent);
    }
    
}
