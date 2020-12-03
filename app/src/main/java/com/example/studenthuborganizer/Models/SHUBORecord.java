package com.example.studenthuborganizer.Models;

import android.content.Context;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

// Use to transfer or read data between the Model layer and presentation and view.
// To use:
//      The Title Description, CourseName, IsGroup must be set with constructor
//      or as individual properties.
//
//      Using the public methods StartDate and EndDate (if over several days or time period)
//      must be set OR you can set a single day by using the method SetOneDayDate. If one of these
//      methods are called the date will be today's date.
//

public class SHUBORecord {
    public long RecordID;
    public String Title;
    public String Description;
    public String CourseName;
    public boolean IsGroup;

    private Calendar beginTime = Calendar.getInstance();
    private Calendar endTime = Calendar.getInstance();

    public SHUBORecord() {
        RecordID = -1;
        Title = "New Event";
        Description = "";
        CourseName = "";
        IsGroup = false;
    }

    public SHUBORecord(long recID, String recTitle, String recDescription,
                       String recCourseName, boolean recIsGroup){
        RecordID = recID;
        Title = recTitle;
        Description = recDescription;
        CourseName = recCourseName;
        IsGroup = recIsGroup;
    }
    public SHUBORecord(long recID, String recTitle, String recDescription,
                       String recCourseName, boolean recIsGroup,
                       long startInMillis, long endInMillis) {
        RecordID = recID;
        Title = recTitle;
        Description = recDescription;
        CourseName = recCourseName;
        IsGroup = recIsGroup;
        beginTime.setTimeInMillis(startInMillis);
        endTime.setTimeInMillis(endInMillis);
    }

    public void SetStartDate(int year, int month, int date, int hourOfDay, int minute) {
        beginTime.set(year, month, date, hourOfDay, minute);
    }
    public void SetStartDate(long timeInMillis) {
        beginTime.setTimeInMillis(timeInMillis);
    }

    public void SetEndDate(int year, int month, int date, int hourOfDay, int minute) {
        endTime.set(year, month, date, hourOfDay, minute);
    }
    public void SetEndDate(long timeInMillis) {
        endTime.setTimeInMillis(timeInMillis);
    }

    public void SetAllDayDate(int year, int month, int date) {
        beginTime.set(year, month, date, 0, 0);
        endTime = beginTime;
    }
    public void SetAllDayDate(long timeInMillis) {
        beginTime.setTimeInMillis(timeInMillis);
        beginTime.set(Calendar.HOUR_OF_DAY, 0);
        beginTime.set(Calendar.MINUTE, 0);
        endTime = beginTime;
        endTime.set(Calendar.HOUR_OF_DAY, 23);
        endTime.set(Calendar.MINUTE, 59);
    }

    public long GetStartDate() {
        return beginTime.getTimeInMillis();
    }

    public long GetEndDate() {
        return endTime.getTimeInMillis();
    }

    public String GetDateLocal(boolean StartDate, Context context) {
        Calendar oTime = (StartDate == true) ? beginTime : endTime;
        DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(context);
        return dateFormat.format(oTime.getTime());
    }

    public String GetTimeLocal(boolean StartTime, Context context) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        DateFormat dateFormat = android.text.format.DateFormat.getTimeFormat(context);
        return dateFormat.format(oTime.getTime());
    }

    public String GetDateTimeFormat(boolean StartTime, Context context) {
        return GetDateLocal(StartTime, context) + " " + GetTimeLocal(StartTime, context);
    }

    public int GetDateYear(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        return oTime.get(Calendar.YEAR);
    }

    public int GetDateMonth(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        return oTime.get(Calendar.MONTH);
    }

    public int GetDateDay(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        return oTime.get(Calendar.DAY_OF_MONTH);
    }

    public int GetTimeHour24(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        return oTime.get(Calendar.HOUR_OF_DAY);
    }

    public int GetTimeMinute(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        return oTime.get(Calendar.MINUTE);
    }

    public String GetTitle() {
        return this.Title;
    }

    public String GetDescription() {
        return this.Description;
    }

    public String GetCourseName() {
        return this.CourseName;
    }
}
