package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CheckBox;

import java.util.ArrayList;
import java.util.Random;


@SuppressWarnings("rawtypes")
public class List extends ActivityInstrumentationTestCase2 {
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
	public List() throws ClassNotFoundException {
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
		//Click on Post-Trip
		solo.clickOnView(solo.getView(android.widget.TextView.class, 8));
		//click on start
		solo.clickOnView(solo.getView("start", 1));
		//====================================================
		GridView gv = solo.getView(GridView.class, 1);
		//getViewAtIndex(gv,17,getInstrumentation());
		scrollListTo(gv,20,getInstrumentation());
		Log.e("TAAAAAG","gv "+ gv.getChildCount());
		ViewGroup viewGroup = (ViewGroup) gv.getChildAt(1);
		Log.e("TAAAAAG","vg "+viewGroup.getChildCount());
		CheckBox cb = (CheckBox) viewGroup.getChildAt(0);
		Log.e("TAAAAAG","cb "+cb.getText());
		solo.clickOnView(cb);
		//=====================================================
		solo.sleep(1000000);
		//solo.scrollListToLine(gv,6);
		//solo.clickOnView(viewGroup);
		//Log.e("TAAAAAG","vg");
		//solo.sleep(5000);
		//solo.clickOnView(v);
		//Log.e("TAAAAAG","v");
		//solo.sleep(5000);

		solo.sleep(1000000);


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
