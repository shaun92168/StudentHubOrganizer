package com.example.studenthuborganizer.Models;

import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import com.example.studenthuborganizer.Presenters.SHUBOPresenter;

import java.util.ArrayList;

public class SHUBUModel {
    SHUBOPresenter mPresenterContext;
    SHUBOCalContract mCalContract;
    boolean mIsCalendarLoaded = false;

    private static final String TAG = "SHUBOModel";
    private static final String TAG_StartInitial = TAG + ".StartInitial";
    private static final String TAG_AddRecord = TAG + ".AddRecord";
    private static final String TAG_UpdateRecord = TAG + ".UpdateRecord";
    private static final String TAG_DeleteRecord = TAG + ".DeleteRecord";
    private static final String TAG_GetRecord = TAG + ".GetRecord";
    private static final String TAG_GetRecordIDs = TAG + ".GetRecordIDs";


    // Constructor to receive the Activity context and allow association
    @RequiresApi(api = Build.VERSION_CODES.N)
    public SHUBUModel(SHUBOPresenter context) {
        this.mPresenterContext = context;
        mCalContract = new SHUBOCalContract(context);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void StartInitial() {
        // Original call started by MainActivity to start any intial synchronization before any methods are called
        mIsCalendarLoaded = mCalContract.LoadCalendar();
        if (mIsCalendarLoaded == false) {
            Log.d(TAG_StartInitial, "Failed to Load Calendar");
            mIsCalendarLoaded = mCalContract.CreateCalendar();
            if (mIsCalendarLoaded == false) {
                Log.d(TAG_StartInitial, "Failed to Create Calendar");
            }
            else {
                Log.d(TAG_StartInitial, "Success Creating Calendar");
            }
        }
        else {
            Log.d(TAG_StartInitial, "Success Loading Calendar");
        }

        // Add any code required
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public long AddRecord(SHUBORecord EventRecord) {
        long recId = mCalContract.AddEvent(EventRecord);

        if (recId != -1) {
            Log.d(TAG_AddRecord, "Success adding rec ID: " + recId);
        } else {
            Log.d(TAG_AddRecord, "Failed to add rec ID: " + recId);
        }

        return recId;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean UpdateRecord(SHUBORecord EventRecord) {
        boolean result = mCalContract.UpdateEvent(EventRecord);

        if (result == true) {
            Log.d(TAG_UpdateRecord, "Success updating rec ID: " + EventRecord.RecordID);
        } else {
            Log.d(TAG_UpdateRecord, "Failed to update rec ID: " + EventRecord.RecordID);
        }

        return result;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean DeleteRecord(long EventID) {
        boolean result = mCalContract.DeleteEvent(EventID);

        if (result == true) {
            Log.d(TAG_DeleteRecord, "Success deleted ID: " + EventID);
        } else {
            Log.d(TAG_DeleteRecord, "Failed to delete ID: " + EventID);
        }

        return result;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public SHUBORecord GetRecord(long RecordID) {
        SHUBORecord Rec = mCalContract.GetEvent(RecordID);

        if (Rec != null) {
            Log.d(TAG_GetRecord, "Success getting rec ID: " + RecordID);
        } else {
            Log.d(TAG_GetRecord, "Failed to get rec ID: " + RecordID);
        }

        return Rec;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<Long> GetRecordIDs() {
        ArrayList<Long> EventIDs = mCalContract.GetAllEventIDs();

        if (EventIDs.size() == 0) {
            Log.d(TAG_GetRecordIDs, "Failed to find record IDs");
        } else {
            Log.d(TAG_GetRecordIDs, "Success found " + EventIDs.size() + " record IDs");
        }

        return EventIDs;
    }
}