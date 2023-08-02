package com.example.movielibrary;

import android.widget.EditText;

public class Item {
    private String title;
    private String year;
    private String country;
    private String genre;
    private double cost;
    private String keywords;

    public Item(String title, String year, String country, String genre, double cost, String keywords) {
        this.title = title;
        this.year = year;
        this.country = country;
        this.genre = genre;
        this.cost = cost;
        this.keywords = keywords;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getCountry() {
        return country;
    }

    public String getGenre() {
        return genre;
    }

    public double getCost() {
        return cost;
    }

    public String getKeywords() {
        return keywords;
    }

    public String description() {
        return  "Title:    " + getTitle() +
                "\nYear:     " + getTitle() +
                "\nCountry:  " + getCountry() +
                "\nGenre:    " + getGenre() +
                "\nCost:     " + getCost() +
                "\nKeywords: " + getKeywords();

    }
}