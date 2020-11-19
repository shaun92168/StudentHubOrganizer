package com.example.studenthuborganizer.Views;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studenthuborganizer.R;

public class AddActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add);
        }

        public void cancelToViewPage(final View v) {
            finish();
        }

        public void addToViewPage(final View v) {
            Intent i = new Intent();
            setResult(RESULT_OK, i);
            finish();
        }
}
