package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.*;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Random;


@SuppressWarnings("rawtypes")
public class TruckInspection extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    //number & list of defective
    private int nDef;
    private ArrayList<String> preDefList = new ArrayList<>();
    private ArrayList<String> postDefList = new ArrayList<>();

    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.desertmicro.android.routewarrior.activities.LoginActivity";

    private static Class<?> launcherActivityClass;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public TruckInspection() throws ClassNotFoundException {
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
        //click on username EditText,clear it and enter username
        //solo.clickOnView(solo.getView("userId")); // commented for saving time
        solo.clearEditText((EditText) solo.getView("userId"));
        solo.enterText((EditText) solo.getView("userId"), "shred1");
        //click on password EditText,clear it and enter password
        //solo.clickOnView(solo.getView("password")); // commented for saving time
        solo.clearEditText((EditText) solo.getView("password"));
        solo.enterText((EditText) solo.getView("password"), "s1");
        //click on sign in button
        solo.clickOnView(solo.getView("sign_in_button"));
        //wait for select route dialog
        solo.waitForDialogToOpen();
        //initiate select route listView
        ListView routeList = solo.getView(ListView.class, 0);
        //get list size
        int count = routeList.getCount();
        //select random route from list
        int rNum = randomItem(count);
        solo.clickOnView(getViewAtIndex(routeList, rNum, getInstrumentation()));
        //click ok button
        solo.clickOnView(solo.getView(android.R.id.button1));
        //wait for ServiceHistoryActivity
        solo.waitForActivity("ServiceHistoryActivity", 20000);
        //sleep for 2 second
        solo.sleep(2000);
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //wait for inspection activity
        solo.waitForActivity("InspectionActivity");
        //click on select truck
        solo.clickOnView(solo.getView("truckId"));
        //initiate select truck list
        ListView truckList = solo.getView(ListView.class, 0);
        //get list size
        count = truckList.getCount();
        //select random truck from list
        rNum = randomItem(count);
        solo.clickOnView(getViewAtIndex(truckList, rNum, getInstrumentation()));
        //click ok button
        solo.clickOnView(solo.getView(android.R.id.button1));
        //sleep for second
        solo.sleep(1000);
        //click on start button
        solo.clickOnView(solo.getView("start"));
        //initiate GridView of truck defective items
        GridView gv = solo.getView(GridView.class, 0);
        //number of items in GridView
        count = gv.getCount();
        //choose randomly number of defective items in truck
        nDef = randomItem(count);
        //choose this items randomly
        for (int i = 0; i < nDef; i++) {
            int item = randomItem(count);
            /*
            * select item
            * to select item number x in gridView, scroll to line x-1 and select item number x%4
            *
            * note: I get this way experimentally
            */
            solo.scrollListToLine(gv, item - 1);
            //index of item in line
            int index = item % 4;
            if (!solo.isCheckBoxChecked(index)) {
                solo.clickOnCheckBox(index);
                android.widget.CheckBox cb = solo.getView(CheckBox.class, index);
                String name = (String) cb.getText();
                Log.e("Checkbox......", name + " , " + item);
                preDefList.add(name);
            }
        }
        //click on next
        solo.clickOnView(solo.getView("next"));
        solo.sleep(1000);
        //click on Enter remark button
        solo.clickOnView(solo.getView("rem_button"));
        //click on remarks EditText,clear it and enter remark
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "Brakes are loud");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on enter Odometer reading button
        solo.clickOnView(solo.getView("mileage"));
        //click on Odometer reading EditText,clear it and enter value
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "25369");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on select mechanic
        solo.clickOnView(solo.getView("mech_name"));
        //list of mechanics
        ListView mechList = solo.getView(ListView.class, 0);
        //get list count
        count = mechList.getCount();
        rNum = randomItem(count);
        //click on randomly selected item
        solo.clickOnView(getViewAtIndex(mechList, rNum, getInstrumentation()));
        //click on ok
        solo.clickOnView(solo.getView("ok"));
        //sleep for 2 second
        solo.sleep(2000);
        //Click on Select work completed date and time
        solo.clickOnView(solo.getView("complete_date"));
        //Click on OK
        solo.clickOnView(solo.getView("ok"));
        //Click on Capture mechanic signature button
        solo.clickOnView(solo.getView("mech_sig_btn"));
        //Draw signature
        int screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        int screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        int fromX, toX, fromY, toY = 0;
        fromX = screenWidth / 2;
        toX = screenWidth / 2;
        fromY = (screenHeight / 2) + (screenHeight / 3);
        toY = (screenHeight / 2) - (screenHeight / 3);
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //optional scroll on small screens
        solo.drag(fromX, toX, fromY, toY, 40);
        solo.sleep(2000);
        //click on Capture driver signature button
        solo.clickOnView(solo.getView("driver_sig_btn"));
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //click on submit button
        solo.clickOnView(solo.getView("save"));
        //slepp for 2 seconds
        solo.sleep(2000);
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //click on pre-trip history
        solo.clickOnView(solo.getView(android.widget.TextView.class, 9));
        //select item from pre-trip history on
        solo.clickInList(1, 2);
        //click ok
        solo.clickOnView(solo.getView("ok"));
        //Click on Post-Trip
        solo.clickOnView(solo.getView(android.widget.TextView.class, 8));
        //click on start
        solo.clickOnView(solo.getView("start", 1));
        //intiate GridView
        gv = solo.getView(GridView.class, 1);
        //get gridVIew count
        count = gv.getCount();
        nDef = randomItem(count);
        for (int i = 0; i < nDef; i++) {
            int item = randomItem(count);
            solo.scrollListToLine(gv, item - 1);
            if (item % 2 == 0) {
                CheckBox cb = solo.getView(CheckBox.class, 6);
                if (!cb.isChecked()) {
                    solo.clickOnCheckBox(6);
                    postDefList.add((String) cb.getText());
                }
            } else {
                CheckBox cb = solo.getView(CheckBox.class, 7);
                if (!cb.isChecked()) {
                    solo.clickOnCheckBox(7);
                    postDefList.add((String) cb.getText());
                }
            }
            solo.sleep(1000);
        }
        //click on next
        solo.clickOnView(solo.getView("next",1));
        solo.sleep(1000);
        //click on Enter remark button
        solo.clickOnView(solo.getView("rem_button",1));
        //click on remarks EditText,clear it and enter remark
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "Brakes are loud");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on enter Odometer reading button
        solo.clickOnView(solo.getView("mileage",1));
        //click on Odometer reading EditText,clear it and enter value
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "25369");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on select mechanic
        solo.clickOnView(solo.getView("mech_name",1));
        //list of mechanics
        mechList = solo.getView(ListView.class, 0);
        //get list count
        count = mechList.getCount();
        rNum = randomItem(count);
        //click on randomly selected item
        solo.clickOnView(getViewAtIndex(mechList, rNum, getInstrumentation()));
        //click on ok
        solo.clickOnView(solo.getView("ok"));
        //sleep for 2 second
        solo.sleep(2000);
        //Click on Select work completed date and time
        solo.clickOnView(solo.getView("complete_date",1));
        //Click on OK
        solo.clickOnView(solo.getView("ok"));
        //Click on Capture mechanic signature button
        solo.clickOnView(solo.getView("mech_sig_btn",1));
        //Draw signature
        /*screenWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
        screenHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        //fromX, toX, fromY, toY = 0;
        fromX = screenWidth / 2;
        toX = screenWidth / 2;
        fromY = (screenHeight / 2) + (screenHeight / 3);
        toY = (screenHeight / 2) - (screenHeight / 3);*/
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //optional scroll on small screens
        solo.drag(fromX, toX, fromY, toY, 40);
        solo.sleep(2000);
        //click on Capture driver signature button
        solo.clickOnView(solo.getView("driver_sig_btn",1));
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //click on submit button
        solo.clickOnView(solo.getView("save",1));
        //slepp for 2 seconds
        solo.sleep(2000);
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //click on pre-trip history
        solo.clickOnView(solo.getView(android.widget.TextView.class, 10));
        //select item from pre-trip history on
        solo.clickInList(1, 3);
        //click ok
        solo.clickOnView(solo.getView("ok"));
        solo.sleep(1000);
        //click on close button
        solo.clickOnView(solo.getView("exit"));
        //logout
        logout();
        solo.sleep(5000);
    }

    private void logout() {
        //click logout form list
        solo.sendKey(solo.MENU);
        solo.clickInList(4);
        solo.waitForDialogToOpen(1000);
        //click on logout button
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.waitForDialogToOpen(1000);
        //select route status
        solo.clickOnView(solo.getView(android.R.id.text1, 0));
        //click ok
        solo.clickOnView(solo.getView(android.R.id.button1));
        solo.setActivityOrientation(Solo.PORTRAIT);

    }


    //return random number from 0 to max

    private int randomItem(int max) {
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        //  return ThreadLocalRandom.current().nextInt(0,max);
        //}else{
        Random rand = new Random();
        return rand.nextInt(max);
        //}
    }


    public View getViewAtIndex(final ListView listElement, final int indexInList, Instrumentation instrumentation) {
        ListView parent = listElement;
        if (parent != null) {
            if (indexInList <= parent.getAdapter().getCount()) {
                scrollListTo(parent, indexInList, instrumentation);
                int indexToUse = indexInList - parent.getFirstVisiblePosition();
                return parent.getChildAt(indexToUse);
            }
        }
        return null;
    }

    public <T extends AbsListView> void scrollListTo(final T listView,
                                                     final int index, Instrumentation instrumentation) {
        instrumentation.runOnMainSync(new Runnable() {
            @Override
            public void run() {
                listView.setSelection(index);
            }
        });
        instrumentation.waitForIdleSync();
    }


}
