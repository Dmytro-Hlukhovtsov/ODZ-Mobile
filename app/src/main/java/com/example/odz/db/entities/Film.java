package com.example.odz.db.entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


/**
 * Film entity
 */
@Entity(tableName = "films")
public class Film {

    @PrimaryKey
    @NonNull
    public String imdbId;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "poster_url")
    public String url;

    @ColumnInfo(name = "duration")
    public String duration;

    @ColumnInfo(name = "genres")
    public String genres;

    @ColumnInfo(name = "plot")
    public String plot;

    @ColumnInfo(name = "rate")
    public String rate;


    @NonNull
    public String getImdbId() {
        return imdbId;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getUrl() {
        return url;
    }

    public String getDuration() {
        return duration;
    }

    public String getGenres() {
        return genres;
    }

    public String getPlot() {
        return plot;
    }

    public String getRate() {
        return rate;
    }

    public Film(@NonNull String imdbId, String title, String year, String url, String duration, String genres, String plot, String rate) {
        this.imdbId = imdbId;
        this.title = title;
        this.year = year;
        this.url = url;
        this.duration = duration;
        this.genres = genres;
        this.plot = plot;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Film{" +
                "imdbId='" + imdbId + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", url='" + url + '\'' +
                ", duration='" + duration + '\'' +
                ", genres='" + genres + '\'' +
                ", plot='" + plot + '\'' +
                ", plot='" + rate + '\'' +
                '}';
    }
}
