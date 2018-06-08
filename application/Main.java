package digitalagenda.application;



public class Main
{
	public static final String dbName ="defdb";
	private static String dbPathName = "assets/db/defdb";

	public static void main(String[] args)
	{
		startUp();
		
		shutDown();
		System.out.println("All done");
	}

	public static void startUp()
	{
		digitalagenda.application.Services.createDataAccess(dbName);
	}

	public static void shutDown()
	{
		comp3350.digitalagenda.application.Services.closeDataAccess();
	}
	
	public static String getDBPathName() {
		if (dbPathName == null)
			return dbName;
		else
			return dbPathName;
	}
	
	public static void setDBPathName(String pathName) {
		System.out.println("Setting DB path to: " + pathName);
		dbPathName = pathName;
	}
}
