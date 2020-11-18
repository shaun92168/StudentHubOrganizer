package com.example.studenthuborganizer.Views;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.studenthuborganizer.Presenters.SHUBOPresenter;
import com.example.studenthuborganizer.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    SHUBOPresenter presenter;

    public boolean mCalPermissionGranted = false;

    private static final int PERMISSION_CODE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new SHUBOPresenter(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        askPermission();
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

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

        presenter.OnActivityResultHandler(requestCode, resultCode, data);
    }
}