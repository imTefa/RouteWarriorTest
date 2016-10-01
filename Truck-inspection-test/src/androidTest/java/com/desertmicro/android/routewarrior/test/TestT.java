package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class TestT extends ActivityInstrumentationTestCase2 {
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
    public TestT() throws ClassNotFoundException {
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
        //Sleep for 6837 milliseconds
		solo.sleep(6837);
        //Click on Sign in
		solo.clickOnView(solo.getView("sign_in_button"));
        //Enter the text: 'shred1'
		solo.clearEditText((android.widget.EditText) solo.getView("userId"));
		solo.enterText((android.widget.EditText) solo.getView("userId"), "shred1");
        //Sleep for 16832 milliseconds
		solo.sleep(16832);
        //Click on COMMERCIAL - Route 01208 - 4 Stops
		solo.clickOnView(solo.getView(android.R.id.text1));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 13601 milliseconds
		solo.sleep(13601);
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Sleep for 2654 milliseconds
		solo.sleep(2654);
        //Click on HomeView Route 01208 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 30));
        //Sleep for 886 milliseconds
		solo.sleep(886);
        //Click on Truck inspections
		solo.clickOnView(solo.getView("inspection"));
        //Wait for activity: 'com.desertmicro.android.routewarrior.activities.InspectionActivity'
		assertTrue("InspectionActivity is not found!", solo.waitForActivity("InspectionActivity"));
        //Sleep for 1616 milliseconds
		solo.sleep(1616);
        //Assert that: 'Retrieving Inspection History...' is shown
		assertTrue("'Retrieving Inspection History...' is not shown!", solo.waitForView(solo.getView(0x101)));
        //Sleep for 2267 milliseconds
		solo.sleep(2267);
        //Click on 963 Truck
		solo.clickOnView(solo.getView("truckId"));
        //Sleep for 666 milliseconds
		solo.sleep(666);
        //Click on 963 Truck
		solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Sleep for 1232 milliseconds
		solo.sleep(1232);
        //Click on 980 Truck
		solo.clickOnView(solo.getView(android.R.id.text1, 3));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 928 milliseconds
		solo.sleep(928);
        //Click on Start
		solo.clickOnView(solo.getView("start"));
        //Sleep for 8696 milliseconds
		solo.sleep(8696);
        //Scroll to Safety - Fire Extingisher
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 20);
        //Click on Safety - Fire Extingisher
		solo.clickOnView(solo.getView("point_description", 10));
        //Sleep for 968 milliseconds
		solo.sleep(968);
        //Click on Next
		solo.clickOnView(solo.getView("next"));
        //Sleep for 4228 milliseconds
		solo.sleep(4228);
        //Click on Discard
		solo.clickOnView(solo.getView("discard"));
        //Sleep for 1066 milliseconds
		solo.sleep(1066);
        //Click on Discard
		solo.clickOnView(solo.getView(android.R.id.button1));
	}
}
