package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class CheckBoxesTest extends ActivityInstrumentationTestCase2 {
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
    public CheckBoxesTest() throws ClassNotFoundException {
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
        //Sleep for 2596 milliseconds
		solo.sleep(2596);
        //Click on Sign in
		solo.clickOnView(solo.getView("sign_in_button"));
        //Enter the text: 'shred1'
		solo.clearEditText((android.widget.EditText) solo.getView("userId"));
		solo.enterText((android.widget.EditText) solo.getView("userId"), "shred1");
        //Sleep for 6639 milliseconds
		solo.sleep(6639);
        //Click on COMMERCIAL - Route 01408 - 16 Stops
		solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 8435 milliseconds
		solo.sleep(8435);
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Sleep for 1283 milliseconds
		solo.sleep(1283);
        //Click on All (16)
		solo.clickOnView(solo.getView(android.widget.TextView.class, 0));
        //Sleep for 876 milliseconds
		solo.sleep(876);
        //Click on HomeView Route 01408 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 34));
        //Sleep for 678 milliseconds
		solo.sleep(678);
        //Click on Truck inspections
		solo.clickOnView(solo.getView("inspection"));
        //Wait for activity: 'com.desertmicro.android.routewarrior.activities.InspectionActivity'
		assertTrue("InspectionActivity is not found!", solo.waitForActivity("InspectionActivity"));
        //Sleep for 5698 milliseconds
		solo.sleep(5698);
        //Scroll to Fuel Tanks
		android.widget.ListView listView0 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView0, 14);
        //Click on Fuel Tanks
		solo.clickOnView(solo.getView("point_description", 4));
        //Sleep for 1865 milliseconds
		solo.sleep(1865);
        //Scroll to Muffler
		android.widget.ListView listView1 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView1, 20);
        //Click on Muffler
		solo.clickOnView(solo.getView("point_description", 5));
        //Sleep for 2581 milliseconds
		solo.sleep(2581);
        //Scroll to Safety - Reflective Triangles
		android.widget.ListView listView2 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView2, 26);
        //Click on Safety - Reflective Triangles
		solo.clickOnView(solo.getView("point_description", 6));
        //Sleep for 1798 milliseconds
		solo.sleep(1798);
        //Scroll to Trip Recorder
		android.widget.ListView listView3 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView3, 38);
        //Click on Trip Recorder
		solo.clickOnView(solo.getView("point_description", 4));
        //Sleep for 2598 milliseconds
		solo.sleep(2598);
        //Scroll to Lights - Turn Indicators
		android.widget.ListView listView4 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView4, 18);
        //Click on Lights - Turn Indicators
		solo.clickOnView(solo.getView("point_description", 4));
        //Sleep for 2081 milliseconds
		solo.sleep(2081);
        //Scroll to Front Axle
		android.widget.ListView listView5 = (android.widget.ListView) solo.getView(android.widget.ListView.class, 0);
		solo.scrollListToLine(listView5, 14);
        //Click on Front Axle
		solo.clickOnView(solo.getView("point_description", 3));
        //Sleep for 1501 milliseconds
		solo.sleep(1501);
        //Click on Fifth Wheel
		solo.clickOnView(solo.getView("point_description"));
        //Sleep for 2416 milliseconds
		solo.sleep(2416);
        //Click on Next
		solo.clickOnView(solo.getView("next"));
	}
}
