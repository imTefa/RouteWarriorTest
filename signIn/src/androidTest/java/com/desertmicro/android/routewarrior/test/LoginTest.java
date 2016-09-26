package com.desertmicro.android.routewarrior.test;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.robotium.solo.Solo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;


@SuppressWarnings("rawtypes")
public class LoginTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;
    private Date sDate;


    //change some thinga ssdf asfsdf asfasdf


    //file chooser dialog
    Dialog dialog;
    private String[] mFileList;
    private File mPath;
    private String mChoosenFile;
    private static final String FTYPE = ".txt";
    private static final int DIALOG_LOAD_FILE = 1000;


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
    public LoginTest() throws ClassNotFoundException {
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

        //file chooser dialog
        try {
            displayDialogInUI();
        } catch (Exception e) {
            e.printStackTrace();
        }

        solo.waitForDialogToClose();


        //prepare result file
        try {
            prepareResultFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //reading users
        try {
            File file = new File(mPath,mChoosenFile);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] user = line.split(",");
                // start time
                sDate = new Date();
                signIn(user[0], user[1]);
                solo.waitForDialogToOpen();
                if (solo.searchButton("Close")) {
                    solo.clickOnView(solo.getView("exit"));
                    solo.clickOnView(solo.getView(android.R.id.button2));
                    printResult(true, user[0]);
                } else if (solo.searchText("Select Route")) {
                    solo.clickOnView(solo.getView(android.R.id.button2));
                    printResult(true, user[0]);
                } else {
                    solo.clickOnView(solo.getView(android.R.id.button1));
                    printResult(false, user[0]);
                }
            }
            br.close();
            openResultFile();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    //run dialog in UI
    public void displayDialogInUI() throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mPath =Environment.getExternalStorageDirectory();
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


    //////////////////////////
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
        File dir = new File(Environment.getExternalStorageDirectory(), "Test_Result");
        if (!dir.exists()) {
            dir.mkdir();
        }
        File file = new File(dir, "Result.txt");
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