package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class Close extends ActivityInstrumentationTestCase2 {
  	private Solo solo;
  	
  	private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.desertmicro.android.routewarrior.activities.LoginActivity";

    private static Class<?> launcherActivityClass;
    static{
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
           throw new RuntimeException(e);
        }
    }
  	
  	@SuppressWarnings("unchecked")
    public Close() throws ClassNotFoundException {
        super(launcherActivityClass);
    }

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.desertmicro.android.routewarrior.activities.LoginActivity'
		solo.waitForActivity("LoginActivity", 2000);
        //Sleep for 1068 milliseconds
		solo.sleep(1068);
        //Click on Sign in
		solo.clickOnView(solo.getView("sign_in_button"));
        //Enter the text: 'shred1'
		solo.clearEditText((android.widget.EditText) solo.getView("userId"));
		solo.enterText((android.widget.EditText) solo.getView("userId"), "shred1");
        //Sleep for 6888 milliseconds
		solo.sleep(6888);
        //Click on COMMERCIAL - Route 01308 - 7 Stops
		solo.clickOnView(solo.getView(android.R.id.text1, 1));
        //Sleep for 543 milliseconds
		solo.sleep(543);
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 8298 milliseconds
		solo.sleep(8298);
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Sleep for 1486 milliseconds
		solo.sleep(1486);
        //Click on HomeView Route 01308 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 45));
        //Sleep for 664 milliseconds
		solo.sleep(664);
        //Click on Truck inspections
		solo.clickOnView(solo.getView("inspection"));
        //Wait for activity: 'com.desertmicro.android.routewarrior.activities.InspectionActivity'
		assertTrue("InspectionActivity is not found!", solo.waitForActivity("InspectionActivity"));
        //Sleep for 1250 milliseconds
		solo.sleep(1250);
        //Click on Close
		solo.clickOnView(solo.getView("exit"));
	}
}
