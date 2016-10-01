package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


@SuppressWarnings("rawtypes")
public class BigTest extends ActivityInstrumentationTestCase2 {
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
    public BigTest() throws ClassNotFoundException {
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
        //Sleep for 1008 milliseconds
		solo.sleep(1008);
        //Click on Sign in
		solo.clickOnView(solo.getView("sign_in_button"));
        //Enter the text: 'shred1'
		solo.clearEditText((android.widget.EditText) solo.getView("userId"));
		solo.enterText((android.widget.EditText) solo.getView("userId"), "shred1");
        //Sleep for 6640 milliseconds
		solo.sleep(6640);
        //Click on COMMERCIAL - Route DS001 - 5 Stops
		solo.clickOnView(solo.getView(android.R.id.text1, 4));
        //Sleep for 530 milliseconds
		solo.sleep(530);
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 7727 milliseconds
		solo.sleep(7727);
        //Rotate the screen
		solo.setActivityOrientation(Solo.LANDSCAPE);
        //Sleep for 818 milliseconds
		solo.sleep(818);
        //Click on HomeView Route DS001 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 43));
        //Click on HomeView Route DS001 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 46));
        //Sleep for 689 milliseconds
		solo.sleep(689);
        //Click on HomeView Route DS001 - Shredding
		solo.clickOnView(solo.getView(android.widget.LinearLayout.class, 45));
        //Sleep for 524 milliseconds
		solo.sleep(524);
        //Click on Truck inspections
		solo.clickOnView(solo.getView("inspection"));
        //Wait for activity: 'com.desertmicro.android.routewarrior.activities.InspectionActivity'
		assertTrue("InspectionActivity is not found!", solo.waitForActivity("InspectionActivity"));
        //Sleep for 1838 milliseconds
		solo.sleep(1838);
        //Click on 956 Truck
		solo.clickOnView(solo.getView("truckId"));
        //Sleep for 609 milliseconds
		solo.sleep(609);
        //Click on 962 Truck
		solo.clickOnView(solo.getView(android.R.id.text1, 2));
        //Click on OK
		solo.clickOnView(solo.getView(android.R.id.button1));
        //Sleep for 1573 milliseconds
		solo.sleep(1573);
        //Click on Start
		solo.clickOnView(solo.getView("start"));
        //Sleep for 595 milliseconds
		solo.sleep(595);
        //Click on Battery
		solo.clickOnView(solo.getView("point_description", 2));
        //Click on Air Lines
		solo.clickOnView(solo.getView("point_description", 1));
        //Sleep for 633 milliseconds
		solo.sleep(633);
        //Click on Air Compressor
		solo.clickOnView(solo.getView("point_description"));
        //Sleep for 950 milliseconds
		solo.sleep(950);
        //Click on Next
		solo.clickOnView(solo.getView("next"));
        //Sleep for 3645 milliseconds
		solo.sleep(3645);
        //Click on Enter...
		solo.clickOnView(solo.getView("rem_button"));
        //Sleep for 1915 milliseconds
		solo.sleep(1915);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_box"));
        //Sleep for 2283 milliseconds
		solo.sleep(2283);
        //Enter the text: 'تالللال'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
		solo.enterText((android.widget.EditText) solo.getView("edit_box"), "تالللال");
        //Sleep for 711 milliseconds
		solo.sleep(711);
        //Click on OK
		solo.clickOnView(solo.getView("ok"));
        //Sleep for 1022 milliseconds
		solo.sleep(1022);
        //Click on Enter...
		solo.clickOnView(solo.getView("mileage"));
        //Sleep for 1874 milliseconds
		solo.sleep(1874);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_box"));
        //Sleep for 1639 milliseconds
		solo.sleep(1639);
        //Enter the text: '26952585'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
		solo.enterText((android.widget.EditText) solo.getView("edit_box"), "26952585");
        //Sleep for 1083 milliseconds
		solo.sleep(1083);
        //Click on OK
		solo.clickOnView(solo.getView("ok"));
        //Sleep for 1799 milliseconds
		solo.sleep(1799);
        //Click on Select
		solo.clickOnView(solo.getView("mech_name"));
        //Sleep for 9119 milliseconds
		solo.sleep(9119);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 802 milliseconds
		solo.sleep(802);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_box"));
        //Sleep for 1702 milliseconds
		solo.sleep(1702);
        //Enter the text: '766'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
		solo.enterText((android.widget.EditText) solo.getView("edit_box"), "766");
        //Sleep for 4458 milliseconds
		solo.sleep(4458);
        //Click on Cancel
		solo.clickOnView(solo.getView("cancel"));
        //Sleep for 880 milliseconds
		solo.sleep(880);
        //Click on Select
		solo.clickOnView(solo.getView("mech_name"));
        //Sleep for 1633 milliseconds
		solo.sleep(1633);
        //Assert that: 'Empty Text View' is shown
		assertTrue("'Empty Text View' is not shown!", solo.waitForView(solo.getView("internalEmpty")));
        //Sleep for 1868 milliseconds
		solo.sleep(1868);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 751 milliseconds
		solo.sleep(751);
        //Click on Empty Text View
		solo.clickOnView(solo.getView("edit_box"));
        //Sleep for 1348 milliseconds
		solo.sleep(1348);
        //Enter the text: '766'
		solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
		solo.enterText((android.widget.EditText) solo.getView("edit_box"), "766");
        //Sleep for 1417 milliseconds
		solo.sleep(1417);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 2797 milliseconds
		solo.sleep(2797);
        //Click on Cancel
		solo.clickOnView(solo.getView("cancel"));
        //Click on Select
		solo.clickOnView(solo.getView("complete_date"));
        //Sleep for 1446 milliseconds
		solo.sleep(1446);
        //Click on OK
		solo.clickOnView(solo.getView("ok"));
        //Sleep for 536 milliseconds
		solo.sleep(536);
        //Click on Capture...
		solo.clickOnView(solo.getView("mech_sig_btn"));
        //Sleep for 1666 milliseconds
		solo.sleep(1666);
        //Click on Save
		solo.clickOnView(solo.getView("next", 2));
        //Sleep for 2899 milliseconds
		solo.sleep(2899);
        //Click on Capture...
		solo.clickOnView(solo.getView("driver_sig_btn"));
        //Sleep for 3451 milliseconds
		solo.sleep(3451);
        //Click on Save
		solo.clickOnView(solo.getView("next", 2));
        //Sleep for 696 milliseconds
		solo.sleep(696);
        //Click on Capture...
		solo.clickOnView(solo.getView("driver_sig_btn"));
        //Sleep for 1333 milliseconds
		solo.sleep(1333);
        //Click on Save
		solo.clickOnView(solo.getView("next", 2));
        //Sleep for 4433 milliseconds
		solo.sleep(4433);
        //Click on Select
		solo.clickOnView(solo.getView("mech_name"));
        //Sleep for 930 milliseconds
		solo.sleep(930);
        //Assert that: 'Empty Text View' is shown
		assertTrue("'Empty Text View' is not shown!", solo.waitForView(solo.getView("internalEmpty")));
        //Assert that: 'Empty Text View' is shown
		assertTrue("'Empty Text View' is not shown!", solo.waitForView(solo.getView("internalEmpty")));
        //Assert that: 'Empty Text View' is shown
		assertTrue("'Empty Text View' is not shown!", solo.waitForView(solo.getView("internalEmpty")));
        //Sleep for 2642 milliseconds
		solo.sleep(2642);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 1131 milliseconds
		solo.sleep(1131);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 567 milliseconds
		solo.sleep(567);
        //Click on Add
		solo.clickOnView(solo.getView("add"));
        //Sleep for 824 milliseconds
		solo.sleep(824);
        //Click on Cancel
		solo.clickOnView(solo.getView("cancel"));
        //Sleep for 1254 milliseconds
		solo.sleep(1254);
        //Click on Submit
		solo.clickOnView(solo.getView("save"));
	}
}
