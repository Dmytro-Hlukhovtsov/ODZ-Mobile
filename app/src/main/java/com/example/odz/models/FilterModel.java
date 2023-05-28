package com.example.odz.models;


import com.example.odz.services.RetrofitService;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilterModel {
    private static FilterModel filterModel;
    private String title;
    private String genre;
    private String startYear;
    private String endYear;

    public FilterModel(String title, String genre, String startYear, String endYear) {
        this.title = title;
        this.genre = genre;
        this.startYear = startYear;
        this.endYear = endYear;
    }
    public FilterModel() {}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public static FilterModel getInstance(String title,String genre,String startYear,String endYear){
        if(filterModel == null) {
            filterModel = new FilterModel(title, genre, startYear, endYear);
        }
        return filterModel;
    }
    @Override
    public String toString() {
        return "FilterModel{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", startYear='" + startYear + '\'' +
                ", endYear='" + endYear + '\'' +
                '}';
    }
}
