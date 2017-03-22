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

        this.firstStart = true;

        this.fastScrollRecyclerView = (FastScrollRecyclerView) findViewById(R.id.recycler_view);
        this.fastScrollRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, AddUser.class);
                startActivityForResult(i, REQUEST_ADD);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        APIManager.getInstance(this).getStudentList();
        sortAtoZ();
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

            sortAtoZ();
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

    private void sortAtoZ() {
        Collections.sort(users, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.name.compareToIgnoreCase(o2.name);
            }
        });
    }

    private void setAdapterRecyclerView(ArrayList<User> users) {
        this.fastScrollRecyclerView.setAdapter(new UserRecyclerAdapter(users, this.fastScrollRecyclerView));
    }
}
