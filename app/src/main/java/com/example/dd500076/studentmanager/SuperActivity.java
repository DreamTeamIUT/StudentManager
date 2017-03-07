package com.example.dd500076.studentmanager;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by ft503084 on 07/03/17.
 */

public class SuperActivity extends AppCompatActivity implements RequestMessageInterface {
    private static final String TAG = "SuperActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume: yes");

        APIManager.getInstance(this).registerEvents(this);
    }

    @Override
    public void onConnect(boolean connected, String token) {

    }

    @Override
    public void onStudentList(ArrayList<User> users) {

    }

    @Override
    public void onDeleteStudent(boolean deleted) {

    }

    @Override
    public void onAddStudent(boolean added) {

    }
}
