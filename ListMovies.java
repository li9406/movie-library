package com.example.movielibrary;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListMovies extends AppCompatActivity {
    EditText editTitleMovie;
    EditText editYearMovie;
    EditText editCountryMovie;
    EditText editGenreMovie;
    EditText editCostMovie;
    EditText editKeywordsMovie;

    // RecycleView
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;   // responsible to maintain the layout of the RecycleView -> how the cards are placed
    RecyclerView.Adapter rvAdapter;    // adapt the ArrayList
    ArrayList<Item> movieData;

    public ListMovies(ArrayList<Item> movieData) {
        this.movieData = movieData;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // make RecycleView to bind with its id
        recyclerView = findViewById(R.id.recycleViewItems);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        rvAdapter = new MyReceiverViewAdapter(movieData);    // adapt the movie list to it
        recyclerView.setAdapter(rvAdapter);    // put the adapter into the RecycleView -> able to display the list

    }

    public void addMovieData() {
        Item item = new Item(
                editTitleMovie.getText().toString(),
                editYearMovie.getText().toString(),
                editCountryMovie.getText().toString(),
                editGenreMovie.getText().toString(),
                Double.parseDouble(editCostMovie.getText().toString()),
                editKeywordsMovie.getText().toString());

        movieData.add(item);

        rvAdapter.notifyDataSetChanged();
    }
}
