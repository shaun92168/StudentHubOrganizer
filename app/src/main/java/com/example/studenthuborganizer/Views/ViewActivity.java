package com.example.studenthuborganizer.Views;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthuborganizer.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);

        List<String[]> myList = new ArrayList<>();
        myList.add(new String[]{"11/18", "Assignment 1", "MATH7908"});
        myList.add(new String[]{"11/19", "Assignment 2", "MATH7908"});
        myList.add(new String[]{"11/20", "Assignment 3", "COMP7082"});

        for (int i = 0; i < myList.size(); i++) {
            CheckBox dueDate = new CheckBox(this);
            TextView assignmentName = new TextView(this);
            TextView courseName = new TextView(this);
            ChipGroup chipG = new ChipGroup(this);

            dueDate.setId(0);
            //assignmentName.setId(i);
            //courseName.setId(i);
            //chipG.setId(i);

            dueDate.setText(myList.get(i)[0]);
            assignmentName.setText(myList.get(i)[1]);
            assignmentName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            assignmentName.setTypeface(null, Typeface.BOLD);
            assignmentName.setTextColor(Color.parseColor("#000000"));
            assignmentName.setPadding(0, 20, 0, 0);
            courseName.setText(myList.get(i)[2]);
            courseName.setPadding(0, 20, 0, 0);

            chipG.setSingleLine(true);
            chipG.setLayoutParams(new ChipGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150));
            chipG.setChipSpacingHorizontal(20);
            chipG.addView(dueDate, new ViewGroup.LayoutParams(220, 100));
            chipG.addView(assignmentName, new ViewGroup.LayoutParams(500, 100));
            chipG.addView(courseName, new ViewGroup.LayoutParams(300, 100));

            LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout);
            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            ll.addView(chipG, llp);
        }

    }

    public void addNewRecord(final View v) {
        Intent myIntent = new Intent(ViewActivity.this, AddActivity.class);
        ViewActivity.this.startActivity(myIntent);
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
