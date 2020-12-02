package com.example.studenthuborganizer.Presenters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.studenthuborganizer.Models.SHUBORecord;
import com.example.studenthuborganizer.Models.SHUBUModel;
import com.example.studenthuborganizer.Views.AddActivity;
import com.example.studenthuborganizer.Views.MainActivity;
import com.example.studenthuborganizer.Views.ViewActivity;

import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class SHUBOPresenter {
    public static final int ADD_ACTIVITY_REQUEST_CODE = 11;
    public static final int EDIT_ACTIVITY_REQUEST_CODE = 12;
    public static final int VIEW_PAGE_REQUEST_CODE = 3;

    MainActivity mMainActivity = null;
    AddActivity mAddActivity = null;
    ViewActivity mViewActivity = null;
    SHUBUModel model;
    Long CachedRecForEdit = -1L;

    // Constructor to receive the Activity context and allow association
    @RequiresApi(api = Build.VERSION_CODES.N)
    public SHUBOPresenter(MainActivity context) {
        this.mMainActivity = context;
        model = new SHUBUModel(this);
    }

    public void SetAddActivity(AddActivity newActivity) {
        mAddActivity = newActivity;
    }

    public void SetViewActivity(ViewActivity newActivity) {
        mViewActivity = newActivity;
    }

    public Context GetAppContext() {
        return mMainActivity.getApplicationContext();
    }

    public MainActivity GetMainActivity() {
        return mMainActivity;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void StartInitial() {
        // Called by MainActivity to start any intial synchronization before any methods are called
        // Call first before any other code
        model.StartInitial();

        // Add any code required

    }

    public void SetForEdit(Long RecToEditID) {
        CachedRecForEdit = RecToEditID;
    }

    public Long GetForEditRec() {
        return CachedRecForEdit;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void OnActivityResultHandler(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                SHUBORecord aNewRec = new SHUBORecord();
                aNewRec.Title = (String) data.getStringExtra("ASSIGNMENTNAME");
                aNewRec.Description = (String) data.getStringExtra("DESCRIPTION");
                aNewRec.CourseName = (String) data.getStringExtra("COURSENAME");
                aNewRec.IsGroup = (boolean) data.getBooleanExtra("ISGROUP", false);
                Long DTInMs = data.getLongExtra("DATETIMEPICKEDINMILLIS", 0L);
                aNewRec.SetAllDayDate(DTInMs);
                aNewRec.RecordID = model.AddRecord(aNewRec);

                if (aNewRec.RecordID != -1) {
                    mViewActivity.createTask(aNewRec.RecordID);
                }
            }
        }

        if (requestCode == EDIT_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Long RecId = data.getLongExtra("RecID", -1L);
                SHUBORecord aRecToUpdate = model.GetRecord(RecId);
                if (aRecToUpdate.RecordID != -1) {
                    aRecToUpdate.Title = (String) data.getStringExtra("ASSIGNMENTNAME");
                    aRecToUpdate.Description = (String) data.getStringExtra("DESCRIPTION");
                    aRecToUpdate.CourseName = (String) data.getStringExtra("COURSENAME");
                    aRecToUpdate.IsGroup = (boolean) data.getBooleanExtra("ISGROUP", false);
                    Long DTInMs = data.getLongExtra("DATETIMEPICKEDINMILLIS", 0L);
                    aRecToUpdate.SetAllDayDate(DTInMs);
                    model.UpdateRecord(aRecToUpdate);
                }
            }
        }

        if (requestCode == VIEW_PAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
            }
        }
    }

    //
    // Interface Wrappers to get at raw model data
    //
    @RequiresApi(api = Build.VERSION_CODES.N)
    public long AddRecord(SHUBORecord EventRecord) {
        return model.AddRecord(EventRecord);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean UpdateRecord(SHUBORecord EventRecord) {
        return model.UpdateRecord(EventRecord);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean DeleteRecord(long EventID) {
        return model.DeleteRecord(EventID);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SHUBORecord GetRecord(long RecordID) {
        return model.GetRecord(RecordID);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Long> GetRecordIDs() {
        return model.GetRecordIDs();
    }
}
