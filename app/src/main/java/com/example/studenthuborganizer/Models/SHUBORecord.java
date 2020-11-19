package com.example.studenthuborganizer.Models;

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

    public SHUBORecord(){
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

    public String GetDateTimeFormat(boolean StartTime) {
        Calendar oTime = (StartTime == true) ? beginTime : endTime;
        String sDate =   oTime.get(Calendar.YEAR) + "-"
                       + oTime.get(Calendar.MONTH)  + "-"
                       + oTime.get(Calendar.DAY_OF_MONTH)  + " "
                       + oTime.get(Calendar.HOUR_OF_DAY)  + ":"
                       + oTime.get(Calendar.MINUTE);

        return sDate;
    }
}
