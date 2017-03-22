package com.example.dd500076.studentmanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class StudentActivity extends SuperActivity {

    private final String TAG = "StudentActivity";

    private Bundle bundle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        bundle = getIntent().getExtras();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(StudentActivity.this);
                alert.setTitle("Warning");
                alert.setMessage("Would you like to delete " + bundle.getString("studentSurname") + " ?");
                alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (APIManager.getInstance(StudentActivity.this).isConnected())
                            APIManager.getInstance(StudentActivity.this).delStudent(bundle.getString("studentId", "0"));
                        else {
                            Intent i = new Intent(StudentActivity.this, LoginActivity.class);
                            i.putExtra("idEtu", bundle.getString("studentId", "0"));

                            StudentActivity.this.startActivityForResult(i, MainActivity.REQUEST_DEL);
                        }
                    }
                });
                alert.setNegativeButton("No", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });

        Log.d(TAG, "onCreate: " + bundle.getString("studentId"));

        setTitle(bundle.getString("studentSurname", "--"));

        ((TextView) findViewById(R.id.student_info_name_text)).setText(bundle.getString("studentName", "--"));
        ((TextView) findViewById(R.id.student_info_surname_text)).setText(bundle.getString("studentSurname", "--"));
        ((TextView) findViewById(R.id.student_info_studies_text)).setText(bundle.getString("studentStudies", "--"));
        ((TextView) findViewById(R.id.student_info_year_text)).setText(String.valueOf(bundle.getInt("studentYear", 0)));
    }

    @Override
    public void onDeleteStudent(boolean deleted) {
        Log.d(TAG, "onDeleteStudent: yes");

        setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult: " + requestCode + " " + resultCode + " " + MainActivity.REQUEST_DEL + " " + RESULT_OK);

        if (requestCode == MainActivity.REQUEST_DEL && resultCode == RESULT_OK)
            APIManager.getInstance(StudentActivity.this).delStudent(bundle.getString("studentId", "0"));
    }
}
