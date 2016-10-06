package com.desertmicro.android.routewarrior.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ListView;

import com.robotium.solo.Solo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Tefa on 06/10/2016.
 */
public class TruckInspectionTestOrganized extends ActivityInstrumentationTestCase2 {

    Solo solo;
    private static final String LAUNCHER_ACTIVITY_FULL_CLASSNAME = "com.desertmicro.android.routewarrior.activities.LoginActivity";
    private static Class<?> launcherActivityClass;

    Date sDate;
    //file chooser dialog
    Dialog dialog;
    private String[] mFileList;
    private File mPath;
    private String mChoosenFile;
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;
    private ArrayList<String> preDefList = new ArrayList<>();
    private ArrayList<String> postDefList = new ArrayList<>();
    int count, rNum;

    static {
        try {
            launcherActivityClass = Class.forName(LAUNCHER_ACTIVITY_FULL_CLASSNAME);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @SuppressWarnings("unchecked")
    public TruckInspectionTestOrganized() throws ClassNotFoundException {
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
        //wait for LoginActivity
        solo.waitForActivity("LoginActivity", 2000);
        //file chooser dialog
        try {
            displayDialogInUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //wait for dialog to close
        solo.waitForDialogToClose();
        //prepare result file
        try {
            prepareResultFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            File file = new File(mPath, mChoosenFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                // start date
                sDate = new Date();
                userIspect(user[0], user[1]);
            }
            br.close();
            openResultFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void userIspect(String username, String password) {
        //call signIn method
        signIn(username, password);
        //wait for dialog
        solo.waitForDialogToOpen();
        // check if login is fail or success
        if (solo.searchText("Select Route")) {
            //call select route method
            selectRoute();
            //handle random dialog
            solo.waitForDialogToOpen(2000);
            handleRandomDialog();
            //wait for ServiceHistoryActivity
            solo.waitForActivity("ServiceHistoryActivity", 20000);
            //sleep for 2 second
            solo.sleep(2000);
            //call truck inspection method
            truckInspection();
            //print result
            try {
                printResult(true, username);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //logout
            logout();
            solo.sleep(5000);
        } else {
            //handle login fail dialog
            //click ok
            solo.clickOnView(solo.getView(android.R.id.button1));
            //print result
            try {
                printResult(false, username);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void signIn(String userName, String password) {
        //click on userId editText
        solo.clickOnView(solo.getView("userId"));
        //clear editText and sumbit userName
        solo.clearEditText((android.widget.EditText) solo.getView("userId"));
        solo.enterText((android.widget.EditText) solo.getView("userId"), userName);
        //click on password editText
        solo.clickOnView(solo.getView("password"));
        //clear editText and sumbit password
        solo.clearEditText((android.widget.EditText) solo.getView("password"));
        solo.enterText((android.widget.EditText) solo.getView("password"), password);
        solo.clickOnView(solo.getView("userId"));
        //Click on Sign in
        solo.clickOnView(solo.getView("sign_in_button"));
    }

    private void selectRoute() {
        //initiate select route listView
        ListView routeList = solo.getView(ListView.class, 0);
        //get list size
        count = routeList.getCount();
        //select random route from list
        rNum = randomItem(count);
        solo.clickOnView(getViewAtIndex(routeList, rNum, getInstrumentation()));
        //click ok button
        solo.clickOnView(solo.getView(android.R.id.button1));
    }

    private void handleRandomDialog() {
        if (solo.waitForDialogToOpen()) {
            solo.clickOnView(solo.getView(android.R.id.button1));
        }
    }

    private void truckInspection() {
        /****select truck****/
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //wait for inspection activity
        solo.waitForActivity("InspectionActivity");
        solo.sleep(1000);
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
        /****Pre-trip inspection****/
        //click on pre-trip tab
        solo.clickOnText("Pre-Trip");
        //click on start button
        solo.clickOnView(solo.getView("start"));
        //select pre trip defective items
        selectPreTripDefectiveItems();
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
        //sleep for second
        solo.sleep(1000);
        //click on pre-trip history
        solo.clickOnText("Pre-Trip History");
        //select item from pre-trip history on
        solo.clickInList(1, 2);
        //click ok
        solo.clickOnView(solo.getView("ok"));
        //sleep for second
        solo.sleep(1000);
        /****Post-trip inspection****/
        //Click on Post-Trip
        solo.clickOnText("Post-Trip");
        //click on start
        solo.clickOnView(solo.getView("start", 1));
        //select post trip defective items
        selectPostTripDefectiveItems();
        //click on next
        solo.clickOnView(solo.getView("next", 1));
        solo.sleep(1000);
        //click on Enter remark button
        solo.clickOnView(solo.getView("rem_button", 1));
        //click on remarks EditText,clear it and enter remark
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "Brakes are loud");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on enter Odometer reading button
        solo.clickOnView(solo.getView("mileage", 1));
        //click on Odometer reading EditText,clear it and enter value
        //solo.clickOnView(solo.getView("edit_box")); // commented to save time
        solo.clearEditText((android.widget.EditText) solo.getView("edit_box"));
        solo.enterText((android.widget.EditText) solo.getView("edit_box"), "25369");
        //click buton ok
        solo.clickOnView(solo.getView("ok"));
        //click on select mechanic
        solo.clickOnView(solo.getView("mech_name", 1));
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
        solo.clickOnView(solo.getView("complete_date", 1));
        //Click on OK
        solo.clickOnView(solo.getView("ok"));
        //Click on Capture mechanic signature button
        solo.clickOnView(solo.getView("mech_sig_btn", 1));
        //Draw signature
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //optional scroll on small screens
        solo.drag(fromX, toX, fromY, toY, 40);
        solo.sleep(2000);
        //click on Capture driver signature button
        solo.clickOnView(solo.getView("driver_sig_btn", 1));
        solo.drag(fromX, toX, fromY, toY, 40);
        //Click on Save
        solo.clickOnView(solo.getButton("Save"));
        //click on submit button
        solo.clickOnView(solo.getView("save", 1));
        //slepp for 2 seconds
        solo.sleep(2000);
        //click on action bar home button
        solo.clickOnActionBarHomeButton();
        //click on truck inspection
        solo.clickOnView(solo.getView("inspection"));
        //sleep for second
        solo.sleep(1000);
        //click on post-trip history
        solo.clickOnText("Post-Trip History");
        //select item from post-trip history on
        solo.clickInList(1, 3);
        //click ok
        solo.clickOnView(solo.getView("ok"));
        solo.sleep(1000);
        //click on close button
        solo.clickOnView(solo.getView("exit"));

    }


    private void selectPreTripDefectiveItems() {
        //initiate GridView
        GridView gridView = (GridView) solo.getView("truck_points");
        //GridView child count
        int gvCount = gridView.getCount();
        //GridView number of columns
        int nColumn = gridView.getNumColumns();
        //GridView number of childes in view
        int currentChilds = gridView.getChildCount();
        //randomly choose number of defective items
        int numOfSelectedItems = randomItem(gvCount);
        for (int i = 0; i < numOfSelectedItems; i++) {
            //choose item number random
            int r = randomItem(gvCount);
            //get item index in view
            int index = r % nColumn;
            //scroll to item line
            scrollListTo(gridView, r, getInstrumentation());
            //get checkBox parent view
            ViewGroup viewGroup = (ViewGroup) gridView.getChildAt(index);
            //get checkBox
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(0);
            //check if checkbox is checked
            if (!checkBox.isChecked()) {
                solo.clickOnView(viewGroup);
                preDefList.add((String) checkBox.getText());
                solo.sleep(1000);
                //Log.e(TAG, "GV#1," + " column: " + nColumn + " " + checkBox.getText() + " item: " + r + ", index: " + index);
            }
        }
    }

    private void selectPostTripDefectiveItems() {
        //initiate GridView
        GridView gridView = (GridView) solo.getView("truck_points", 1);
        //GridView child count
        int gvCount = gridView.getCount();
        //GridView number of columns
        int nColumn = gridView.getNumColumns();
        //GridView number of childes in view
        int currentChilds = gridView.getChildCount();
        //randomly choose number of defective items
        int numOfSelectedItems = randomItem(gvCount);
        for (int i = 0; i < numOfSelectedItems; i++) {
            //choose item number random
            int r = randomItem(gvCount);
            //get item index in view
            int index = r % nColumn;
            //scroll to item line
            scrollListTo(gridView, r, getInstrumentation());
            //get checkBox parent view
            ViewGroup viewGroup = (ViewGroup) gridView.getChildAt(index);
            //get checkBox
            CheckBox checkBox = (CheckBox) viewGroup.getChildAt(0);
            //check if checkbox is checked
            if (!checkBox.isChecked()) {
                solo.clickOnView(viewGroup);
                preDefList.add((String) checkBox.getText());
                solo.sleep(1000);
                //Log.e(TAG, "GV#1," + " column: " + nColumn + " " + checkBox.getText() + " item: " + r + ", index: " + index);
            }
        }
    }

    //return random number from 0 to max
    private int randomItem(int max) {
            Random rand = new Random();
            return rand.nextInt(max);
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

    //run dialog in UI
    public void displayDialogInUI() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPath = new File(Environment.getExternalStorageDirectory() + "");
                loadFielList();
                showDialog();
            }
        };

        solo.getCurrentActivity().runOnUiThread(runnable);
    }

    private void showDialog() {
        onCreateDialog(DIALOG_LOAD_FILE).show();
    }

    private void loadFielList() {

        try {
            mPath.mkdirs();
        } catch (SecurityException e) {
        }

        if (mPath.exists()) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File file, String s) {
                    File sel = new File(file, s);
                    return s.contains(FTYPE) || sel.isDirectory();
                }
            };
            mFileList = mPath.list(filter);
        } else mFileList = new String[0];

    }

    protected Dialog onCreateDialog(int id) {
        dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(solo.getCurrentActivity());
        switch (id) {
            case DIALOG_LOAD_FILE:
                builder.setTitle("Choose users file");
                if (mFileList == null) {
                    dialog = builder.create();
                    return dialog;
                }
                builder.setItems(mFileList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mChoosenFile = mFileList[i];
                        File ch = new File(mPath, mChoosenFile);
                        if (ch.isDirectory()) {
                            mPath = ch;
                            loadFielList();
                            dialog.cancel();
                            dialog.dismiss();
                            showDialog();
                        }
                    }
                });
                break;
        }

        dialog = builder.show();
        return dialog;
    }

    private void prepareResultFile() throws IOException {
        String s = "Test Case : ROUTE LISTING\n"
                + "User    Result    Time\n" +
                "-----    -----    -----\n";

        File dir = new File(Environment.getExternalStorageDirectory(), "Test_Result");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, "Result.txt");
        FileWriter fw = new FileWriter(file);
        fw.write(s);
        fw.flush();
        fw.close();
    }

    private void printResult(boolean bool, String userName) throws IOException {
        File file = new File(new File(Environment.getExternalStorageDirectory(), "Test_Result"), "Result.txt");
        FileWriter fw = new FileWriter(file, true);
        if (bool) {
            double s = ((new Date()).getTime() - sDate.getTime()) / 1000;
            fw.append(userName + "    " + "success    " + s + " s\n");
        } else {
            fw.append(userName + "    fail\n");
        }
        fw.flush();
        fw.close();
    }

    private void openResultFile() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.parse("file:///sdcard/Test_Result/Result.txt");
        intent.setDataAndType(uri, "text/plain");
        getActivity().startActivity(intent);
    }
}
