package com.example.studenthuborganizer.Views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Console;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthuborganizer.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity {
    private static final int ADD_ACTIVITY_REQUEST_CODE = 11;
    public String datePickedString, timePickedString, assignNameString, descriptionString, courseNameString;
    public Boolean isGroup;
    public List<String[]> myList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);
        myList = new ArrayList<>();
        myList.add(new String[]{"11/19", "Assignment 2", "MATH7908"});
        myList.add(new String[]{"11/20", "Assignment 3", "COMP7082"});

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE) {
            datePickedString = (String) data.getStringExtra("DATEPICKED");
            timePickedString = (String) data.getStringExtra("TIMEPICKED");
            assignNameString = (String) data.getStringExtra("ASSIGNMENTNAME");
            descriptionString = (String) data.getStringExtra("DESCRIPTION");
            courseNameString = (String) data.getStringExtra("COURSENAME");
            isGroup = (boolean) data.getBooleanExtra("ISGROUP", false);

//            Toast.makeText(getApplicationContext(), "Date: "
//              + datePickedString + "\nTime: "
//              + timePickedString
//              + "\n Is Group: " + isGroup
//              + "\n Assignment Name: " + assignNameString
//              + "\n Description: " + descriptionString
//              + "\n Course Name: " + courseNameString,
//              Toast.LENGTH_LONG).show();

            createTask();
        }
    }

    private void createTask() {
        CheckBox cBox = new CheckBox(this);
        TextView assignmentName = new TextView(this);
        TextView courseName = new TextView(this);
        ChipGroup chipG = new ChipGroup(this);

        cBox.setId(0);
        //assignmentName.setId(i);
        //courseName.setId(i);
        //chipG.setId(i);

        //dueDate.setText(myList.get(i)[0]);
        assignmentName.setText(assignNameString);
        //assignmentName.setText(myList.get(i)[1]);
        assignmentName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        assignmentName.setTypeface(null, Typeface.BOLD);
        assignmentName.setTextColor(Color.parseColor("#000000"));
        assignmentName.setPadding(0, 20, 0, 0);
        //courseName.setText(myList.get(i)[2]);
        courseName.setText(courseNameString);
        courseName.setPadding(0, 20, 0, 0);
        cBox.setText(datePickedString);
        courseName.setPadding(0, 20, 0, 0);
        assignmentName.setTextColor(Color.parseColor("#555555"));

        chipG.setSingleLine(true);
        chipG.setLayoutParams(new ChipGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
        chipG.setChipSpacingHorizontal(20);
        chipG.addView(cBox, new ViewGroup.LayoutParams(220, 100));
        chipG.addView(assignmentName, new ViewGroup.LayoutParams(300, 100));
        chipG.addView(courseName, new ViewGroup.LayoutParams(300, 100));

        LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView(chipG, llp);
    }

    public void addNewRecord(final View v) {
        Intent myIntent = new Intent(ViewActivity.this, AddActivity.class);
        startActivityForResult(myIntent, ADD_ACTIVITY_REQUEST_CODE);
        //ViewActivity.this.startActivity(myIntent);
    }

    public void deleteRecords(final View v) {
        List<Integer> recordsToRemove = new ArrayList<>();
        LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View cg = ll.getChildAt(i);
            CheckBox cb = cg.findViewById(0);
            if (cb.isChecked()) {
                recordsToRemove.add(i);
            }
        }

        Collections.sort(recordsToRemove, Collections.reverseOrder());
        for (int i = 0; i < recordsToRemove.size(); i++) {
            ll.removeViewAt(recordsToRemove.get(i));
        }
    }
}
