package com.example.dd500076.studentmanager;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.l4digital.fastscroll.FastScrollRecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends SuperActivity implements SearchView.OnQueryTextListener {

    private static final String TAG = "MainActivity";

    public static final int REQUEST_ADD = 665;
    public static final int REQUEST_DEL = 884;

    private FastScrollRecyclerView fastScrollRecyclerView;
    private ArrayList <User> users;

    private Boolean firstStart;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstStart = true;

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

        this.fastScrollRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler_view);
        this.fastScrollRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        /*
        this.fastScrollRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                            if (APIManager.getInstance(MainActivity.this).isConnected()) {
                                                Log.d(TAG, "onClick: del stud " + user.idEtu);
                                                APIManager.getInstance(MainActivity.this).delStudent(user.idEtu);
                                            }else{
                                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                                i.putExtra("idEtu",user.idEtu);
                                                MainActivity.this.startActivityForResult(i, REQUEST_DEL);
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

                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        */

        /*
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
                                            if (APIManager.getInstance(MainActivity.this).isConnected()) {
                                                Log.d(TAG, "onClick: del stud " + user.idEtu);
                                                APIManager.getInstance(MainActivity.this).delStudent(user.idEtu);
                                            }else{
                                                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                                                i.putExtra("idEtu",user.idEtu);
                                                MainActivity.this.startActivityForResult(i, REQUEST_DEL);
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

                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        */
    }

    @Override
    protected void onResume() {
        super.onResume();

        APIManager.getInstance(this).getStudentList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);

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
        if (id == R.id.A_Z){
            item.setChecked(true);
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.name.compareToIgnoreCase(o2.name);
                }
            });
        }
        if (id == R.id.Z_A){
            item.setChecked(true);
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o2.name.compareToIgnoreCase(o1.name);
                }
            });
        }
        if (id == R.id.Year){
            item.setChecked(true);
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return Integer.compare(o1.year,o2.year);
                }
            });
        }
        if (id == R.id.Class){
            item.setChecked(true);
            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.studies.compareToIgnoreCase(o2.studies);
                }
            });
        }

        setAdapterRecyclerView(this.users);

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ADD && resultCode == RESULT_OK)
            APIManager.getInstance(this).getStudentList();
        if(requestCode == REQUEST_DEL && resultCode == RESULT_OK){
            APIManager.getInstance(this).delStudent(data.getStringExtra("idEtu"));
            APIManager.getInstance(this).getStudentList();
        }
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
        this.users = users;
        setAdapterRecyclerView(this.users);

        if (firstStart) {
            firstStart = false;

            Collections.sort(users, new Comparator<User>() {
                @Override
                public int compare(User o1, User o2) {
                    return o1.name.compareToIgnoreCase(o2.name);
                }
            });
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.d(TAG, "onQueryTextSubmit: " + query);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.d(TAG, "onQueryTextChange: " + newText);

        if (!newText.equals("")) {
            ArrayList<User> tempUsers = new ArrayList<>();

            for (User user : this.users) {
                if (user.name.toLowerCase().contains(newText.toLowerCase()) || user.surname.toLowerCase().contains(newText.toLowerCase()))
                    tempUsers.add(user);
            }

            setAdapterRecyclerView(tempUsers);
        } else
            setAdapterRecyclerView(this.users);


        return false;
    }

    private void setAdapterRecyclerView(ArrayList<User> users) {
        this.fastScrollRecyclerView.setAdapter(new UserRecyclerAdapter(users, this.fastScrollRecyclerView));
    }
}
