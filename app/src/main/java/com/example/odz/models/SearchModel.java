package com.example.odz.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchModel {
    @SerializedName("page")
    private int page;

    @SerializedName("next")
    private String next;

    @SerializedName("entries")
    private int entries;

    @SerializedName("results")
    private List<FilmItem> results;

    // Getters and setters for the fields

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public int getEntries() {
        return entries;
    }

    public void setEntries(int entries) {
        this.entries = entries;
    }

    public List<FilmItem> getResults() {
        return results;
    }

    public void setResults(List<FilmItem> results) {
        this.results = results;
    }

    public SearchModel() {}

    public SearchModel(int page, String next, int entries, List<FilmItem> results) {
        this.page = page;
        this.next = next;
        this.entries = entries;
        this.results = results;
    }

}
