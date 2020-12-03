package com.example.studenthuborganizer.Views;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
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

import com.example.studenthuborganizer.Models.SHUBORecord;
import com.example.studenthuborganizer.Presenters.SHUBOPresenter;
import com.example.studenthuborganizer.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.w3c.dom.Text;

public class ViewActivity extends AppCompatActivity {
    public Boolean isGroup;
    private SHUBOPresenter mPresenter;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_page);

        mPresenter = MainActivity.mPresenter;
        mPresenter.SetViewActivity(this);

        // Populate the view with every record we have saved.
        ArrayList<Long> RecIds = mPresenter.GetRecordIDs();
        for (int i = 0; i < RecIds.size(); i++) {
            createTask(RecIds.get(i));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.OnActivityResultHandler(requestCode, resultCode, data);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void createTask(Long ARecID) {
        CheckBox cBox = new CheckBox(this);
        TextView assignmentName = new TextView(this);
        TextView courseName = new TextView(this);
        ChipGroup chipG = new ChipGroup(this);

        // Get the record that matches the ID passed in and use it to populate the view
        SHUBORecord ARec = mPresenter.GetRecord(ARecID);
        chipG.setTag(ARec.RecordID);

        cBox.setId(0);
        //assignmentName.setId(i);
        //courseName.setId(i);
        //chipG.setId(i);

        //dueDate.setText(myList.get(i)[0]);
        assignmentName.setText(ARec.Title);
        //assignmentName.setText(myList.get(i)[1]);
        assignmentName.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        assignmentName.setTypeface(null, Typeface.BOLD);
        assignmentName.setTextColor(Color.parseColor("#000000"));
        assignmentName.setPadding(0, 20, 0, 0);
        //courseName.setText(myList.get(i)[2]);
        courseName.setText(ARec.CourseName);
        courseName.setPadding(0, 20, 0, 0);
        cBox.setText(ARec.GetDateLocal(true, this));
        courseName.setPadding(0, 20, 0, 0);
        assignmentName.setTextColor(Color.parseColor("#555555"));

        chipG.setSingleLine(true);
        chipG.setLayoutParams(new ChipGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250));
        chipG.setChipSpacingHorizontal(20);
        chipG.addView(cBox, new ViewGroup.LayoutParams(340, 100));
        chipG.addView(assignmentName, new ViewGroup.LayoutParams(400, 100));
        chipG.addView(courseName, new ViewGroup.LayoutParams(350, 100));

        LinearLayout ll = (LinearLayout) findViewById(R.id.LinearLayout);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        ll.addView(chipG, llp);
    }

    public void EditRecord(final View v) {
        LinearLayout ll = (LinearLayout)findViewById(R.id.LinearLayout);
        for (int i = 0; i < ll.getChildCount(); i++) {
            View cg = ll.getChildAt(i);
            CheckBox cb = cg.findViewById(0);
            if (cb.isChecked()) {
                Intent myIntent = new Intent(ViewActivity.this, AddActivity.class);
                Long RecToEditID = (Long)cg.getTag();
                myIntent.putExtra("RecID", (Long)RecToEditID);
                mPresenter.SetForEdit(RecToEditID);
                startActivityForResult(myIntent, mPresenter.EDIT_ACTIVITY_REQUEST_CODE);

                // We only support one edit right now so exit
                break;
            }
        }
    }

    public void addNewRecord(final View v) {
        Intent myIntent = new Intent(ViewActivity.this, AddActivity.class);
        startActivityForResult(myIntent, mPresenter.ADD_ACTIVITY_REQUEST_CODE);
        //ViewActivity.this.startActivity(myIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
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
            Long RecToDelID = (Long)ll.getChildAt(recordsToRemove.get(i)).getTag();
            ll.removeViewAt(recordsToRemove.get(i));
            mPresenter.DeleteRecord(RecToDelID);
        }
    }
}
