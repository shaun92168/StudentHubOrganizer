package com.example.studenthuborganizer.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthuborganizer.R;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);

        List<String[]> myList = new ArrayList<>();
        myList.add(new String[]{"11/18", "Assignment 1", "MATH7908"});

        for (int i = 0; i < myList.size(); i++) {
            CheckBox dueDate = (CheckBox)findViewById(R.id.checkBox);
            TextView tv1 = (TextView)findViewById(R.id.assignmentText);
            TextView tv2 = (TextView)findViewById(R.id.courseText);
            dueDate.setText(myList.get(i)[0]);
            tv1.setText(myList.get(i)[1]);
            tv2.setText(myList.get(i)[2]);
        }

    }

    public void addNewRecord(final View v) {
        Intent myIntent = new Intent(ViewActivity.this, AddActivity.class);
        ViewActivity.this.startActivity(myIntent);
    }

    public void deleteRecords(final View v) {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        finish();
    }

    public void addItemToRemove(final View v) {

    }

    public void removeItemToRemove(final View v) {

    }

}
