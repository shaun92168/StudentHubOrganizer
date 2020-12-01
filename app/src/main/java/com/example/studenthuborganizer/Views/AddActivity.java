package com.example.studenthuborganizer.Views;

import android.app.AlertDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.widget.RadioButton;
import android.widget.TimePicker;

import com.example.studenthuborganizer.Models.SHUBORecord;
import com.example.studenthuborganizer.Presenters.SHUBOPresenter;
import com.example.studenthuborganizer.R;


public class AddActivity extends AppCompatActivity {

    EditText etAssignName, etDescription, etCourseName;
    Button saveBtn;
    DatePicker datePicker;
    TimePicker timePicker;
    long dateTimeInMilliS;
    RadioButton rdGroup;
    boolean isGroupBoolean;

    private SHUBOPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mPresenter = MainActivity.mPresenter;
        mPresenter.SetAddActivity(this);

        datePicker = (DatePicker) findViewById(R.id.date_picker);
        timePicker = (TimePicker) findViewById(R.id.time_picker);
        etAssignName = (EditText) findViewById(R.id.etTaskName);
        etDescription = (EditText) findViewById(R.id.etDescription);
        etCourseName = (EditText) findViewById(R.id.etCourseName);
        rdGroup = (RadioButton) findViewById(R.id.rdGroup);
        saveBtn = (Button) findViewById(R.id.btSave);

        // In case the first time we are activated is for a record edit
        Long RecIdToEdit = mPresenter.GetForEditRec();
        if (RecIdToEdit != -1) {
            SetupForEdit(RecIdToEdit);
        }
    }

    public void cancelToViewPage(final View v) {
        finish();
    }

    public void addToViewPage(final View v) {
        Intent i = new Intent(this, ViewActivity.class);
        i.putExtra("RecID", (Long)saveBtn.getTag());
        timePicker.setIs24HourView(true);
        isGroupCheck();

        i.putExtra("DATETIMEPICKEDINMILLIS", buildTimeInMilliS());
        i.putExtra("ISGROUP", isGroupBoolean);
        i.putExtra("ASSIGNMENTNAME", etAssignName.getText() != null ? etAssignName.getText().toString() : "");
        i.putExtra("DESCRIPTION", etDescription.getText() != null ? etDescription.getText().toString() : "");
        i.putExtra("COURSENAME", etCourseName.getText() != null ? etCourseName.getText().toString() : "");

        // Clean up before leaving to restore anything in case updating in stead of adding
        saveBtn.setText("Save");
        saveBtn.setTag(-1L);

        // Mark success and finsh the OnClick call
        setResult(RESULT_OK, i);
        finish();
    }

    public void SetupForEdit(Long RecIDTooUseForEdit) {
        SHUBORecord RecToUseForEdit = mPresenter.GetRecord(RecIDTooUseForEdit);
        saveBtn.setText("Update");
        saveBtn.setTag(RecToUseForEdit.RecordID);

        datePicker.updateDate(RecToUseForEdit.GetDateYear(true),
                              RecToUseForEdit.GetDateMonth(true),
                              RecToUseForEdit.GetDateDay(true));
        timePicker.setHour(RecToUseForEdit.GetTimeHour24(true));
        timePicker.setMinute(RecToUseForEdit.GetTimeMinute(true));
        etAssignName.setText(RecToUseForEdit.Title);
        etDescription.setText(RecToUseForEdit.Description);
        etCourseName.setText(RecToUseForEdit.CourseName);
        rdGroup.setChecked(RecToUseForEdit.IsGroup);
    }

    private boolean isGroupCheck() {
        return isGroupBoolean = rdGroup.isChecked();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Long buildTimeInMilliS() {
        Calendar tmpCal = Calendar.getInstance();
        tmpCal.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(),
                   timePicker.getHour(), timePicker.getMinute());
        dateTimeInMilliS = tmpCal.getTimeInMillis();

        return dateTimeInMilliS;
    }
}
