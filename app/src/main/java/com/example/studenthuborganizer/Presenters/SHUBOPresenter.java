package com.example.studenthuborganizer.Presenters;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.studenthuborganizer.Models.SHUBUModel;
import com.example.studenthuborganizer.Views.MainActivity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SHUBOPresenter {
    MainActivity mActivityContext;
    SHUBUModel model;

    // Constructor to receive the Activity context and allow association
    public SHUBOPresenter(MainActivity context) {
        this.mActivityContext = context;
        model = new SHUBUModel(this);
    }


    public Context GetAppContext() {
        return mActivityContext.getApplicationContext();
    }


    public void StartInitial() {
        // Called by MainActivity to start any intial synchronization before any methods are called

        // Add any code required
    }


    public void OnActivityResultHandler(int requestCode, int resultCode, @Nullable Intent data) {
        //
        // TODO : Port all OnActivityResultHandler code from Views level to here. Views redirects to here
        //
        }
}
