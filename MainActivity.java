package com.example.movielibrary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
//import android.widget.Toolbar;    // no longer maintain
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    EditText editTitleMovie;
    EditText editYearMovie;
    EditText editCountryMovie;
    EditText editGenreMovie;
    EditText editCostMovie;
    EditText editKeywordsMovie;

    ArrayList<String> movieList = new ArrayList<String>();
    ArrayAdapter myAdapter;

    DrawerLayout drawer;

    // RecycleView
    //RecyclerView recyclerView;
    //RecyclerView.LayoutManager layoutManager;   // responsible to maintain the layout of the RecycleView -> how the cards are placed
    //RecyclerView.Adapter rvAdapter;    // adapt the ArrayList
    ArrayList<Item> movieData = new ArrayList<Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Lifecycle callbacks","onCreate");

        // show the main xml
        setContentView(R.layout.drawer);

        // show the title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // list view
        ListView listView = findViewById(R.id.movieList);
        myAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, movieList);
        // set adapter to list view
        listView.setAdapter(myAdapter);

        drawer = findViewById(R.id.dl);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        );

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nv);
        navigationView.setNavigationItemSelectedListener(new MyNavigationListener());


        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // add the new movie into list
                showMessage(view);

                // notify the change in the list
                myAdapter.notifyDataSetChanged();    // update the list view
            }
        });

        editTitleMovie = findViewById(R.id.editTitle);
        editYearMovie = findViewById(R.id.editYear);
        editCountryMovie = findViewById(R.id.editCountry);
        editGenreMovie = findViewById(R.id.editGenre);
        editCostMovie = findViewById(R.id.editCost);
        editKeywordsMovie = findViewById(R.id.editKeywords);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS}, 0);

        SMSReceiver smsReceiver = new SMSReceiver();
        IntentFilter intentFilter = new IntentFilter("MySMS");
        registerReceiver(myReceiver, intentFilter);

        /*
        // make RecycleView to bind with its id
        recyclerView = findViewById(R.id.recycleViewItems);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rvAdapter = new MyReceiverViewAdapter(movieData);    // adapt the movie list to it
        recyclerView.setAdapter(rvAdapter);    // put the adapter into the RecycleView -> able to display the list
        */
    }

    /*
    View.OnClickListener AddMovieListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            TextView text = findViewById(R.id.movieList);
            text.setText("Add movie clicked");
        }
    };

     */

    // Lifecycle callbacks
    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Lifecycle callbacks","onStart");

        SharedPreferences sharedPref = getSharedPreferences("file", MODE_PRIVATE);

        String temp1 = sharedPref.getString("KeyTitle", "");
        editTitleMovie.setText(temp1);
        temp1 = sharedPref.getString("KeyYear", "1888");
        editYearMovie.setText(temp1);
        temp1 = sharedPref.getString("KeyCountry", "");
        editCountryMovie.setText(temp1);
        temp1 = sharedPref.getString("KeyGenre", "");
        editGenreMovie.setText(temp1);
        editCostMovie.setText(sharedPref.getString("KeyCost", "0"));
        temp1 = sharedPref.getString("KeyKeywords", "");
        editKeywordsMovie.setText(temp1);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Lifecycle callbacks","onResume");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Lifecycle callbacks","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Lifecycle callbacks","onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i("Lifecycle callbacks","onDestroy");
    }

    // save and restore view data
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        // change to all lowercase
        String newText = editGenreMovie.getText().toString().toLowerCase();
        editGenreMovie.setText(newText);
        super.onSaveInstanceState(outState);
        Log.i("Lifecycle callbacks","onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        // change to all uppercase
        String newText = editTitleMovie.getText().toString().toUpperCase();
        editTitleMovie.setText(newText);
        Log.i("Lifecycle callbacks","onRestoreInstanceState");
    }

    @Override
    // avoid activity to destroy
    public void onBackPressed(){
        //super.onBackPressed()
        Log.i("Lifecycle callbacks", "onBackPressed");
    }

    public void addMovie() {
        // add Movie to the list
        movieList.add(editTitleMovie.getText().toString() + " | " + editYearMovie.getText().toString());

        // notify the change in the list
        myAdapter.notifyDataSetChanged();    // update the list view

        // save data into shared preferences => persistent data
        SharedPreferences sharedPref = getSharedPreferences("file", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("KeyTitle", editTitleMovie.getText().toString());
        editor.putString("KeyYear", editYearMovie.getText().toString());
        editor.putString("KeyCountry", editCountryMovie.getText().toString());
        editor.putString("KeyGenre", editGenreMovie.getText().toString());
        editor.putString("KeyCost", editCostMovie.getText().toString());
        editor.putString("KeyKeywords", editKeywordsMovie.getText().toString());

        // save the data into storage
        editor.commit();
    }

    /*
    public void addMovieData() {
        Item item = new Item(
                editTitleMovie.getText().toString(),
                editYearMovie.getText().toString(),
                editCountryMovie.getText().toString(),
                editGenreMovie.getText().toString(),
                Double.parseDouble(editCostMovie.getText().toString()),
                editKeywordsMovie.getText().toString());

        movieData.add(item);

        //rvAdapter.notifyDataSetChanged();
    }
     */



    // pop out message when add movie
    public void showMessage(View view) {
        addMovie();
        // message
        String name = editTitleMovie.getText().toString();
        String text = String.format("Movie - %s - has added successfully", name);
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            editTitleMovie.setText(intent.getStringExtra("KEY1"));
            editYearMovie.setText(intent.getStringExtra("KEY2"));
            editCountryMovie.setText(intent.getStringExtra("KEY3"));
            editGenreMovie.setText(intent.getStringExtra("KEY4"));
            editCostMovie.setText(intent.getStringExtra("KEY5"));
            int cost = Integer.parseInt(editCostMovie.getText().toString());
            int extraCost = Integer.parseInt(intent.getStringExtra("KEY7"));
            editCostMovie.setText(""+(cost+extraCost));
            editKeywordsMovie.setText(intent.getStringExtra("KEY6"));
        }
    };

    /*
    // double the cost of the movie
    public void doubleCost(View view){
        // load the cost from shared preference
        SharedPreferences sharedPref = getSharedPreferences("file", MODE_PRIVATE);
        String costText = sharedPref.getString("KeyCost", "0");
        // change to number
        Double costOriginal = Double.parseDouble(costText);
        // double the cost
        Double newCost = costOriginal * 2;
        String newCostStr = "" + newCost;
        // save it back to the shared preference
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("KeyCost", newCostStr);
        editor.commit();
        // show it on EditText
        editCostMovie.setText(newCostStr);
    }
     */

    // clear the content of all fields
    public void resetAll(View view){
        editTitleMovie.setText("");
        editYearMovie.setText("");
        editCostMovie.setText("");
        editCountryMovie.setText("");
        editGenreMovie.setText("");
        editCostMovie.setText("");
        editKeywordsMovie.setText("");
    }

    // clear the shared preference
    public void clearSharedPref(View view){
        SharedPreferences sharedPref = getSharedPreferences("file", MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear().commit();
    }

    class MyNavigationListener implements NavigationView.OnNavigationItemSelectedListener {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            // get the id of the selected item
            int id = item.getItemId();

            if (id == R.id.menuAddMovie) {
                // Add a new Movie to the list
                addMovie();
                ListMovies listMovies = new ListMovies(movieData);
                listMovies.addMovieData();
                // Show message when Movie added successfully
                String name = editTitleMovie.getText().toString();
                String text = String.format("Movie - %s - has added successfully", name);
                Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
            }
            else if (id == R.id.menuRemoveLast) {
                // Remove last Movie added to the list
                movieList.remove(movieList.size()-1);
                // notify the change in the list
                myAdapter.notifyDataSetChanged();    // update the list view
            }
            else if (id == R.id.menuRemoveAll) {
                // Remove all the Movies
                movieList.clear();
                // notify the change in the list
                myAdapter.notifyDataSetChanged();    // update the list view
            }
            else if (id == R.id.menuCloseApp) {
                // close the application
                finish();
            }
            else if (id == R.id.menuListAll) {
                setContentView(R.layout.recycle_view);
            }

            // close the drawer
            drawer.closeDrawers();
            // tell the OS
            return true;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // inflate the option menu to toolbar
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // get the id of the selected item
        int id = item.getItemId();

        if (id == R.id.clearFields) {
            // clear all the fields
            editTitleMovie.setText("");
            editYearMovie.setText("");
            editCostMovie.setText("");
            editCountryMovie.setText("");
            editGenreMovie.setText("");
            editCostMovie.setText("");
            editKeywordsMovie.setText("");
        }
        else if (id == R.id.countTotalMovies) {
            // count the total number of movies in the list
            int count = movieList.size();
            // show the total number of movies using Toast
            String text = String.format("Total movies: %s", count);
            Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}
