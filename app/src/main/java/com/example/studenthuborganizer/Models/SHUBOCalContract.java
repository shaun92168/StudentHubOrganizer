package com.example.studenthuborganizer.Models;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;
import android.app.Activity;

import androidx.annotation.RequiresApi;

import com.example.studenthuborganizer.Presenters.SHUBOPresenter;

import java.util.ArrayList;


@RequiresApi(api = Build.VERSION_CODES.N)
public class SHUBOCalContract {
    SHUBOPresenter mContext;

    private ContentResolver mCr;
    private Activity mActivity;
    private long mLoadedCalID = -1;

    private static final String TAG = "SHUBOCalContract";
    private static final String TAG_LoadCal = TAG + ".LoadCalendar";
    private static final String TAG_CreateCalendar = TAG + ".CreateCalendar";
    private static final String TAG_AddEvent = TAG + ".AddEvent";
    private static final String TAG_UpdateEvent = TAG + ".UpdateEvent";
    private static final String TAG_DeleteEvent = TAG + ".DeleteEvent";
    private static final String TAG_GetEvent = TAG + ".GetEvent";
    private static final String TAG_GetAllEventIDs = TAG + ".GetAllEventIDs";

    public static final String SHUBO_ACCOUNT_NAME = "SHUBO.com";
    public static final String SHUBO_CALENDAR_NAME = "SHUBO Calendar";
    public static final String SHUBO_DEFAULT_TIMEZONE = TimeZone.getDefault().getID();
    public static final String SHUBO_OWNER_ACOUNT = "SHUBO@google.com";
    public static final String SHUBO_CALENDAR_COLOR = "0xff32cd32"; // LimeGreen

    private static final String mRegExpr = "::";


    public SHUBOCalContract(SHUBOPresenter Context) {
        this.mContext = Context;
        mActivity = Context.GetMainActivity();
        mCr = mActivity.getContentResolver();
    }


    @SuppressLint("LongLogTag")
    public boolean LoadCalendar() {
        boolean result = false;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            mLoadedCalID = getCalendarId();

            if (mLoadedCalID == -1) {
                Log.d(TAG_LoadCal, "Failed to load Calendar: " + SHUBO_CALENDAR_NAME);
            } else {
                result = true;
                Log.d(TAG_LoadCal, "Success loading Calendar: " + SHUBO_CALENDAR_NAME);
            }
        } else {
            Log.d(TAG_LoadCal, "Failed no permission to load Calendar: " + SHUBO_CALENDAR_NAME);
        }

        return result;
    }


    @SuppressLint("LongLogTag")
    public boolean CreateCalendar() {
        boolean result = false;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            ContentValues values = new ContentValues();
            values.put(CalendarContract.Calendars.ACCOUNT_NAME, SHUBO_ACCOUNT_NAME);
            values.put(CalendarContract.Calendars.ACCOUNT_TYPE, CalendarContract.ACCOUNT_TYPE_LOCAL);
            values.put(CalendarContract.Calendars.NAME, SHUBO_CALENDAR_NAME);
            values.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, SHUBO_CALENDAR_NAME);
            values.put(CalendarContract.Calendars.CALENDAR_COLOR, SHUBO_CALENDAR_COLOR);
            values.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                    CalendarContract.Calendars.CAL_ACCESS_OWNER);
            values.put(CalendarContract.Calendars.OWNER_ACCOUNT, SHUBO_OWNER_ACOUNT);
            values.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, SHUBO_DEFAULT_TIMEZONE);
            values.put(CalendarContract.Calendars.SYNC_EVENTS, 1);
            Uri.Builder builder = CalendarContract.Calendars.CONTENT_URI.buildUpon();
            builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME, SHUBO_ACCOUNT_NAME);
            builder.appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
                    CalendarContract.ACCOUNT_TYPE_LOCAL);
            builder.appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true");
            Uri uri = mCr.insert(builder.build(), values);

            mLoadedCalID = getCalendarId();
            if (mLoadedCalID == -1) {
                Log.d(TAG_CreateCalendar, "Failed to create Calendar: " + SHUBO_CALENDAR_NAME);
            } else {
                result = true;
                Log.d(TAG_CreateCalendar, "Success created Calendar: " + SHUBO_CALENDAR_NAME);
            }
        } else {
            Log.d(TAG_CreateCalendar, "Failed no permission to create Calendar: " + SHUBO_CALENDAR_NAME);
        }

        return result;
    }


    @SuppressLint("LongLogTag")
    public long AddEvent(SHUBORecord EventRecord) {
        long eventID = -1;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            // Description is split into "Course::Description::1|0" where 1|0 is 0=Individual & 1=Group
            String Description = EventRecord.CourseName + mRegExpr + EventRecord.Description + mRegExpr;
            Description += (EventRecord.IsGroup == true) ? 1 : 0;

            // Insert Event
            ContentValues values = new ContentValues();
            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.DTSTART, EventRecord.GetStartDate());
            values.put(CalendarContract.Events.DTEND, EventRecord.GetEndDate());
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            values.put(CalendarContract.Events.TITLE, EventRecord.Title);
            values.put(CalendarContract.Events.DESCRIPTION, Description);
            values.put(CalendarContract.Events.CALENDAR_ID, mLoadedCalID);

            Uri uri = mCr.insert(CalendarContract.Events.CONTENT_URI, values);

            // Retrieve ID for new event
            String newID = uri.getLastPathSegment();
            if (newID != null) {
                eventID = new Long(newID);
                Log.d(TAG_AddEvent, "Success adding record title: " + EventRecord.Title);
            } else {
                Log.d(TAG_AddEvent, "Failed to add record title: " + EventRecord.Title);
            }
        } else {
            Log.d(TAG_AddEvent, "Failed no permission to add record title: " + EventRecord.Title);
        }

        return eventID;
    }


    // EventRecord must have a valid event id for this to work
    @SuppressLint("LongLogTag")
    public boolean UpdateEvent(SHUBORecord EventRecord) {
        boolean result = false;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            // Description is split into "Course::Description::1|0" where 1|0 is 0=Individual & 1=Group
            String Description = EventRecord.CourseName + mRegExpr + EventRecord.Description + mRegExpr;
            Description += (EventRecord.IsGroup == true) ? 1 : 0;

            // Update Event
            ContentValues values = new ContentValues();
            TimeZone timeZone = TimeZone.getDefault();
            values.put(CalendarContract.Events.DTSTART, EventRecord.GetStartDate());
            values.put(CalendarContract.Events.DTEND, EventRecord.GetEndDate());
            values.put(CalendarContract.Events.EVENT_TIMEZONE, timeZone.getID());
            values.put(CalendarContract.Events.TITLE, EventRecord.Title);
            values.put(CalendarContract.Events.DESCRIPTION, Description);
            values.put(CalendarContract.Events.CALENDAR_ID, mLoadedCalID);

            String[] selArgs = new String[]{ Long.toString(EventRecord.RecordID)};

            int updated = mCr.update(CalendarContract.Events.CONTENT_URI, values,
                                     CalendarContract.Events._ID + " =? ", selArgs);

            if (updated > 0) {
                result = true;
                Log.d(TAG_UpdateEvent, "Success updated ID: " + EventRecord.RecordID
                                            + " found: " + updated);
            } else {
                Log.d(TAG_UpdateEvent, "Failed to update ID: " + EventRecord.RecordID);
            }
        } else {
            Log.d(TAG_UpdateEvent, "Failed no permission to update record ID: "
                                        + EventRecord.RecordID);
        }

        return result;
    }


    @SuppressLint("LongLogTag")
    public boolean DeleteEvent(long EventID) {
        boolean result = false;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            String[] selArgs = new String[]{Long.toString(EventID)};
            int deleted = mCr.delete(CalendarContract.Events.CONTENT_URI,
                                    CalendarContract.Events._ID + " =? ",
                    selArgs);
            if (deleted > 0) {
                result = true;
                Log.d(TAG_DeleteEvent, "Success deleted ID: " + EventID + " found: " + deleted);
            } else {
                Log.d(TAG_DeleteEvent, "Failed to delete ID: " + EventID);
            }
        } else {
            Log.d(TAG_DeleteEvent, "Failed no permission to delete record ID: " + EventID);
        }

        return result;
    }


    @SuppressLint("LongLogTag")
    public SHUBORecord GetEvent(long EventID) {
        SHUBORecord eventRec = null;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            String[] proj = new String[]{CalendarContract.Events._ID,
                    CalendarContract.Events.TITLE,
                    CalendarContract.Events.DESCRIPTION,
                    CalendarContract.Events.DTSTART,
                    CalendarContract.Events.DTEND};

            Cursor cursor = mCr.query(CalendarContract.Events.CONTENT_URI,
                    proj,
                    CalendarContract.Events._ID + " = ? ",
                    new String[]{Long.toString(EventID)},
                    null);

            if (cursor.moveToFirst()) {
                long recID = cursor.getLong(1);
                String recTitle = cursor.getString(2);
                // Description is split into "Course::Description::1|0" where 1|0 is 0=Individual & 1=Group
                String[] attr = cursor.getString(3).split(mRegExpr, 3);
                String recCourseName = attr[0];
                String recDescription = attr[1];
                boolean recIsGroup = (attr[2] == "1") ? true : false;
                long recStartInMillis = cursor.getLong(4);
                long recEndInMillis = cursor.getLong(5);

                // read event data
                eventRec = new SHUBORecord(recID, recTitle, recDescription, recCourseName,
                                           recIsGroup, recStartInMillis, recEndInMillis);
            } else {
                Log.d(TAG_GetEvent, "Failed to find Event: " + EventID);
            }
        } else {
            Log.d(TAG_GetEvent, "Failed no permission to add record ID: " + EventID);
        }

        return eventRec;
    }


    @SuppressLint("LongLogTag")
    public ArrayList<Long> GetAllEventIDs() {
        ArrayList<Long> eventIDs = null;

        if (mContext.GetMainActivity().mCalPermissionGranted) {
            eventIDs = new ArrayList<Long>();

            String[] proj = new String[]{CalendarContract.Events._ID};

            Cursor cursor = mCr.query(CalendarContract.Events.CONTENT_URI,
                    proj,
                    null,
                    null,
                    null);

            int totalEvents = cursor.getCount();
            cursor.moveToFirst();
            for (int i = 0; i < totalEvents; i++) {
                eventIDs.add(cursor.getLong(1));
                cursor.moveToNext();
            }

            if (eventIDs.size() == 0) {
                Log.d(TAG_GetAllEventIDs, "Failed to find Events");
            } else {
                Log.d(TAG_GetAllEventIDs, "Success found " + eventIDs.size() + " Event IDs");
            }
        } else {
            Log.d(TAG_GetAllEventIDs, "Failed no permission to find record IDs");
        }

        return eventIDs;
    }


    private long getCalendarId() {
        String[] projection = new String[]{CalendarContract.Calendars._ID};
        String selection = CalendarContract.Calendars.ACCOUNT_NAME + " = ? AND "
                + CalendarContract.Calendars.ACCOUNT_TYPE + " = ? ";

        // use the same values as above:
        String[] selArgs = new String[]{SHUBO_ACCOUNT_NAME,
                CalendarContract.ACCOUNT_TYPE_LOCAL};
        Cursor cursor = mCr.query(CalendarContract.Calendars.CONTENT_URI,
                projection,
                selection,
                selArgs,
                null);
        if (cursor.moveToFirst()) {
            return cursor.getLong(0);
        }
        return -1;
    }
}
