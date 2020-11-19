package com.example.studenthuborganizer.Presenters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.studenthuborganizer.Models.SHUBORecord;
import com.example.studenthuborganizer.Models.SHUBUModel;
import com.example.studenthuborganizer.Views.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class SHUBOPresenter {
    MainActivity mActivityContext;
    SHUBUModel model;

    // Constructor to receive the Activity context and allow association
    @RequiresApi(api = Build.VERSION_CODES.N)
    public SHUBOPresenter(MainActivity context) {
        this.mActivityContext = context;
        model = new SHUBUModel(this);
    }

    public Context GetAppContext() {
        return mActivityContext.getApplicationContext();
    }

    public MainActivity GetMainActivity() {
        return mActivityContext;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void StartInitial() {
        // Called by MainActivity to start any intial synchronization before any methods are called

        // Add any code required


        ArrayList<Long> AllRecords = model.GetRecordIDs();
        if (AllRecords.size() > 0) {
            // For testing we want no events so delete any found
            for (int i = 0; i < AllRecords.size(); i++) {
                if (model.DeleteRecord(AllRecords.get(i)) == false){
                    Log.d("Testing SHUBOModel", "Failed to delete RecID: " + AllRecords.get(i));
                }
            }
        }
        String Title = "Test Event ";
        String Description = "Test Description ";
        String CourseName = "COMP-7082-0 of ";
        boolean IsGroup = false;

        //
        // Ugly way to create a record but you may need to do it this way for
        // event on same day 2020-11-18 from 18:00 to 19:00 hours
        //
        Calendar beginTime = Calendar.getInstance();
        beginTime.set(2020, 11, 18, 18, 00);
        long StartDate = beginTime.getTimeInMillis();
        Calendar endTime = Calendar.getInstance();
        endTime.set(2020, 11, 18, 19, 00);
        long EndDate = endTime.getTimeInMillis();

        SHUBORecord ARec1 = new SHUBORecord(-1, Title + 1, Description + 1, CourseName + "_1", IsGroup, StartDate, EndDate);
        ARec1.RecordID = model.AddRecord(ARec1);

        //
        // Create a record without start and end dates in constructor than manual add
        // an event from: 2020-11-20 13:30 to: 2020-11-20 15:50
        //
        SHUBORecord ARec2 = new SHUBORecord(-1, Title + 2, Description + 2, CourseName + "_2", IsGroup);
        ARec2.SetStartDate(2020, 11, 20, 13, 30);
        ARec2.SetEndDate(2020, 11, 20, 15, 50);
        ARec2.RecordID = model.AddRecord(ARec2);

        // this must be called last
        model.StartInitial();
    }

    public void OnActivityResultHandler(int requestCode, int resultCode, @Nullable Intent data) {
        //
        // TODO : Port all OnActivityResultHandler code from Views level to here. Views redirects to here
        //
    }
}
