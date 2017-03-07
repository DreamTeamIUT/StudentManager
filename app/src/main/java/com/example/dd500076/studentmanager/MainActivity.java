package com.example.dd500076.studentmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends SuperActivity {

    private static final String TAG = "MainActivity";

    private static final int REQUEST_ADD = 665;

    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddUser.class);
                startActivityForResult(i, REQUEST_ADD);
            }
        });

        /*
        ArrayList<User> arrayOfUsers = new ArrayList<User>();

        User user1 = new User("500098", "nicolas", "romain", "R&T", 2016);
        User user2 = new User("500064", "villena", "guillaume", "R&T", 2016);
        User user3 = new User("500076", "delaporte", "dylan", "R&T", 2016);
        User user4 = new User("503084", "fedhaoui", "thibault", "R&T", 2016);
        arrayOfUsers.add(user1);
        arrayOfUsers.add(user2);
        arrayOfUsers.add(user3);
        UsersAdapter adapter = new UsersAdapter(this, arrayOfUsers);
        ListView listView = (ListView) findViewById(R.id.listviewMain);
        listView.setAdapter(adapter);
        */

        listView = (ListView) findViewById(R.id.listviewMain);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemClick: test");

                final User user = (User) view.getTag();

                Log.d(TAG, "onItemClick: " + user.name);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(R.string.properties_student_text)
                        .setItems(R.array.properties_student, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.d(TAG, "onClick : " + which);
                                if (which == 0){
                                    //modifier
                                }
                                else {
                                    //supprimer
                                    AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
                                    alert.setTitle("Warning");
                                    alert.setMessage("Would you like to delete " + user.name);
                                    alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Log.d(TAG, "onClick: del stud " + user.idEtu);
                                            APIManager.getInstance(MainActivity.this).delStudent(user.idEtu);
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

                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        APIManager.getInstance(this).getStudentList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK)
            APIManager.getInstance(this).getStudentList();
    }

    @Override
    public void onConnect(boolean connected, String token) {
        Log.d(TAG, "onConnect: " + connected);
    }

    @Override
    public void onDeleteStudent(boolean deleted) {
        Log.d(TAG, "onDeleteStudent: azertyrse");
        APIManager.getInstance(this).getStudentList();
    }

    @Override
    public void onStudentList(ArrayList<User> users) {
        UsersAdapter adapter = new UsersAdapter(this, users);
        ListView listView = (ListView) findViewById(R.id.listviewMain);
        listView.setAdapter(adapter);
    }
}
