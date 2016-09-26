package com.desertmicro.android.routewarrior.test;

import com.robotium.solo.*;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import android.test.ActivityInstrumentationTestCase2;
import java.io.File;
import java.io.FilenameFilter;


@SuppressWarnings("rawtypes")
public class DialogTest extends ActivityInstrumentationTestCase2 {
    private Solo solo;

    Dialog dialog;
    private String[] mFileList;
    private File mPath = new File(Environment.getExternalStorageDirectory() + "");
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
    public DialogTest() throws ClassNotFoundException {
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
        try {
            DisplayToastInActivity();
        } catch (Exception e) {
            e.printStackTrace();
        }
        solo.waitForDialogToClose();
    }


    public void DisplayToastInActivity() throws Exception {
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

    private void showDialog() {
        onCreateDialog(DIALOG_LOAD_FILE).show();
    }


}
