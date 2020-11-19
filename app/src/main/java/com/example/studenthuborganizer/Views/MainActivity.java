package com.example.studenthuborganizer.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Bundle;

import com.example.studenthuborganizer.Presenters.SHUBOPresenter;
import com.example.studenthuborganizer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    SHUBOPresenter presenter;

    public boolean mCalPermissionGranted = false;

    private static final int PERMISSION_CODE = 1000;
    private static final int ADD_ACTIVITY_REQUEST_CODE = 2;
    private static final int VIEW_PAGE_REQUEST_CODE = 3;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new SHUBOPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        askPermission();
        setSupportActionBar(toolbar);

        presenter.StartInitial();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private boolean askPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (   checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED
                || checkSelfPermission(Manifest.permission.WRITE_CALENDAR) == PackageManager.PERMISSION_DENIED)
            {
                // Permission not granted, request it
                String[] permission = {
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.WRITE_CALENDAR };
                requestPermissions(permission, PERMISSION_CODE);
            } else {
                // Already granted permission
                mCalPermissionGranted = true;
            }
        }

        return mCalPermissionGranted;
    }


    // Handling permission result
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults){
        // Called when user press allow or deny
        switch (requestCode){
            case PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    mCalPermissionGranted = true;
                } else {
                    Toast.makeText(MainActivity.this, "Calendar Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            }
        }
        if (requestCode == VIEW_PAGE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

            }
        }
        presenter.OnActivityResultHandler(requestCode, resultCode, data);
    }

    public void addRecord(final View view) {
        Intent intent = new Intent(this, AddActivity.class);

        // Do any initialization of the Add Intent controls here
        startActivityForResult(intent, ADD_ACTIVITY_REQUEST_CODE);
    }

    public void viewRecords(final View view) {
        Intent intent = new Intent(this, ViewActivity.class);

        // Do any initialization of the Add Intent controls here
        startActivityForResult(intent, VIEW_PAGE_REQUEST_CODE);
    }
}