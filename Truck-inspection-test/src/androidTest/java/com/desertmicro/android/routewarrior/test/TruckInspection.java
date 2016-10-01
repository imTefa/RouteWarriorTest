package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.widget.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


@SuppressWarnings("rawtypes")
public class TruckInspection extends ActivityInstrumentationTestCase2 {
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
        solo.enterText((EditText) solo.getView("userId"),"shred1");
        //click on password EditText,clear it and enter password
        //solo.clickOnView(solo.getView("password")); // commented for saving time
        solo.clearEditText((EditText) solo.getView("password"));
        solo.enterText((EditText) solo.getView("password"),"s1");
        //click on sign in button
        solo.clickOnView(solo.getView("sign_in_button"));
        //wait for select route dialog
        solo.waitForDialogToOpen();
        //initiate select route listView
        ListView routeList = solo.getView(ListView.class,0);
        //get list size
        int count = routeList.getCount();
        //select random route from list
        int rNum = randomItem(count);
        solo.clickOnView(getViewAtIndex(routeList, rNum, getInstrumentation()));
        //click ok button
        solo.clickOnView(solo.getView(android.R.id.button1));
        //wait for ServiceHistoryActivity
        solo.waitForActivity("ServiceHistoryActivity");
        //sleep for 2 second
        solo.sleep(2000);
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //click on select truck
        solo.clickOnView(solo.getView("truckId"));
        //initiate select truck list
        ListView truckList = solo.getView(ListView.class,0);
        //get list size
        count = truckList.getCount();
        //select random truck from list
        rNum = randomItem(count);
        solo.clickOnView(getViewAtIndex(truckList,rNum,getInstrumentation()));
        //click ok button
        solo.clickOnView(solo.getView(android.R.id.button1));
        //sleep for second
        solo.sleep(1000);
        //click on start button
        solo.clickOnView(solo.getView("start"));
        //initiate GridView of truck defective items
        GridView gv = (GridView) solo.getView(GridView.class,0);
        //number of items in GridView
        count = gv.getCount();
        //choose randomly number of defective items in truck
        int nDef = randomItem(count);
        //choose this items randomly
        for(int i = 0;i<nDef;i++){
            int item = randomItem(count);
            /*
            * select item
            * the way in GridView: need to select item number I
            * if I is even --> scroll to line I and select Checkbox number 0
            * else --> scroll to line I-1 and select Checkbox number 1
            *
            * note: I get this way experimentally
            * */
            if(item%2==0){
                solo.scrollListToLine(gv,item);
                if(!solo.isCheckBoxChecked(0))
                    solo.clickOnCheckBox(0);
            }else {
                solo.scrollListToLine(gv,item-1);
                if(!solo.isCheckBoxChecked(1))
                    solo.clickOnCheckBox(1);
            }
        }
        //click on next
        solo.clickOnView(solo.getView("next"));

        solo.sleep(5000);
    }

    //return random number from 0 to max
    private int randomItem(int max) {
        //if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
          //  return ThreadLocalRandom.current().nextInt(0,max);
        //}else{
            Random rand = new Random();
            return  rand.nextInt(max);
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
