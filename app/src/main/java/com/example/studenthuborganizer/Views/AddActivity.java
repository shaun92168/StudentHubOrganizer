package com.example.studenthuborganizer.Views;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.widget.RadioButton;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.example.studenthuborganizer.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

public class AddActivity extends AppCompatActivity {

    EditText etAssignName, etDescription, etCourseName;
    Button saveBtn;
    DatePicker datePicker;
    TimePicker timePicker;
    String dateString, timeString;
    Integer year, month, day, hour, minute;
    RadioButton rdGroup;
    boolean isGroupBoolean;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);

//            for (int i = 0; i < chipGroup.getChildCount(); i++){
//                RadioButton chip = (RadioButton) chipGroup.getChildAt(i);
//                Log.i("not checked ", i + " chip = " + chip.getText().toString());
//                if (chip.isChecked()){
//                    Log.i("checked ", i + " chip = " + chip.getText().toString());
//                    chipTextList.set(i, chip.getText().toString());
//                    Log.i("nothing?", chipTextList.get(i));
//                }
//            }
        }

        public void cancelToViewPage(final View v) {
            finish();
        }

        public void addToViewPage(final View v) {
            Intent i = new Intent(this, ViewActivity.class);
            //List<String> chipTextList = new ArrayList<>(10);
            datePicker = (DatePicker) findViewById(R.id.date_picker);
            timePicker = (TimePicker) findViewById(R.id.time_picker);
            timePicker.setIs24HourView(true);
            
            etAssignName = (EditText) findViewById(R.id.etTaskName);
            etDescription = (EditText) findViewById(R.id.etDescription);
            etCourseName = (EditText) findViewById(R.id.etCourseName);
            rdGroup = (RadioButton) findViewById(R.id.rdGroup);
            saveBtn = (Button) findViewById(R.id.btSave);

            //Get chip name from chip group
            //ChipGroup chipGroup = findViewById(R.id.chipGroup);
            //chipGroup.isSingleSelection();
            //chipGroup.setSingleLine(true);
            //Check ifGroup
            isGroupCheck();
            dateFromDatePicker();
            timeFromTimePicker();

            i.putExtra("DATEPICKED", dateString != null ? dateString : "");
            i.putExtra("TIMEPICKED", timeString != null ? timeString : "");
            i.putExtra("ISGROUP", isGroupBoolean);
            i.putExtra("ASSIGNMENTNAME", etAssignName.getText() != null ? etAssignName.getText().toString() : "");
            i.putExtra("DESCRIPTION", etDescription.getText() != null ? etDescription.getText().toString() : "");
            i.putExtra("COURSENAME", etCourseName.getText() != null ? etCourseName.getText().toString() : "");

            setResult(RESULT_OK, i);
            finish();
        }

    private boolean isGroupCheck() {
        return isGroupBoolean = rdGroup.isChecked();
    }

    private void dateFromDatePicker() {
            year = datePicker.getYear();
            month = datePicker.getMonth() + 1;
            day = datePicker.getDayOfMonth();
            //dateString = year.toString()+ "," + month.toString() + "," + day.toString();
            //For Milestone 2 Demo
            dateString = month.toString() + "/" + day.toString();
    }

        private void timeFromTimePicker(){
            if (Build.VERSION.SDK_INT >= 23 ) {
                minute= timePicker.getMinute();
                hour = timePicker.getHour();
            } else {
                minute = timePicker.getCurrentMinute();
                hour = timePicker.getCurrentHour();
            }
            timeString = hour.toString()+ "," + minute.toString();
        }
}
