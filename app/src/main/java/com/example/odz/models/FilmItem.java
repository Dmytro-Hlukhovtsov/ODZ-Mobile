package com.example.odz.models;


import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class FilmItem {
    @SerializedName("id")
    private String id;

    @SerializedName("ratingsSummary")
    private RatingsSummary ratingsSummary;

    @SerializedName("primaryImage")
    private FilmImage primaryImage;

    @SerializedName("genres")
    private Genres genres;

    @SerializedName("titleText")
    private TitleText titleText;

    @SerializedName("releaseYear")
    private  ReleaseYear releaseYear;

    @SerializedName("runtime")
    private Runtime runtime;

    @SerializedName("plot")
    private Plot plot;
    @SerializedName("inDB")
    private Boolean inDB = false;

    // Getters and setters for the fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getRatingsSummary() {
        return ratingsSummary.getAggregateRating();
    }

    public void setRatingsSummary(RatingsSummary ratingsSummary) {
        this.ratingsSummary = ratingsSummary;
    }

    public String getPrimaryImage() {
        return primaryImage.getUrl();
    }

    public void setPrimaryImage(FilmImage primaryImage) {
        this.primaryImage = primaryImage;
    }

    public String getGenres() {
        return genres.getGenres();
    }

    public void setGenres(Genres genres) {
        this.genres = genres;
    }

    public String getlTitleText() {
        return titleText.getText();
    }

    public void setTitleText(TitleText titleText) {
        this.titleText = titleText;
    }

    public String getReleaseYear() {
        return String.valueOf(releaseYear.getYear());
    }

    public void setReleaseYear(ReleaseYear releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getRuntime() {
        return runtime.getFormattedTime();
    }

    public void setRuntime(Runtime runtime) {
        this.runtime = runtime;
    }

    public String getPlot() {
        return plot.getPlotText();
    }

    public void setPlot(Plot plot) {
        this.plot = plot;
    }

    public Boolean getInDB() {
        return this.inDB;
    }

    public void setInDB(Boolean inDB) {
        this.inDB = inDB;
    }

    public static class RatingsSummary {
        @SerializedName("aggregateRating")
        private double aggregateRating;

        public String getAggregateRating() {
            return String.valueOf(aggregateRating);
        }
    }

    public static class FilmImage {
        @SerializedName("url")
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public static class Genres {
        @SerializedName("genres")
        private List<Genre> genres;

        public String getGenres() {
            StringBuilder sb = new StringBuilder();
            for (Genre genre : this.genres) {
                sb.append(genre.getText()).append(", ");
            }

            if (sb.length() > 0) {
                sb.setLength(sb.length() - 2);
            }

            String listAsString = sb.toString();
            return listAsString;
        }

        public void setGenres(Genre genre) {
            this.genres.add(genre);
        }


        public static class Genre {
            @SerializedName("text")
            private String text;

            public void setText(String text) {
                this.text = text;
            }
            public String getText() {
                return text;
            }
        }
    }

    public static class TitleText {
        @SerializedName("text")
        private String text;

        public void setText(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }
    }

    public static class ReleaseYear {

        @SerializedName("year")
        private int year;

        public int getYear() {
            return year;
        }

        public void setYear(int year) {
            this.year = year;
        }
    }

    public static class Runtime {
        @SerializedName("seconds")
        private int seconds;

        // Getter and setter for the field
        public int getSeconds() {
            return seconds;
        }

        public void setSeconds(int seconds) {
            this.seconds = seconds;
        }

        @SuppressLint("DefaultLocale")
        public String getFormattedTime() {
            int hours = seconds / 3600;
            int minutes = (seconds % 3600) / 60;

            return String.format("%dh %dm", hours, minutes);
        }
    }

    public static class Plot {
        @SerializedName("plotText")
        private PlotText plotText;

        // Getters and setters for the field
        public String getPlotText() {
            return plotText.getPlainText();
        }

        public void setPlotText(PlotText plotText) {
            this.plotText = plotText;
        }

        public static class PlotText {
            @SerializedName("plainText")
            private String plainText;

            public String getPlainText() {
                return plainText;
            }

            public void setPlainText(String plainText) {
                this.plainText = plainText;
            }
        }
    }

    public FilmItem() {}

    public FilmItem(String id, RatingsSummary ratingsSummary, FilmImage primaryImage, Genres genres, TitleText titleText, ReleaseYear releaseYear, Runtime runtime, Plot plot) {
        this.id = id;
        this.ratingsSummary = ratingsSummary;
        this.primaryImage = primaryImage;
        this.genres = genres;
        this.titleText = titleText;
        this.releaseYear = releaseYear;
        this.runtime = runtime;
        this.plot = plot;
        this.inDB = false;
    }

    @Override
    public String toString() {
        return "FilmItem{" +
                "id='" + id + '\'' +
                ", ratingsSummary=" + getRatingsSummary() +
                ", primaryImage=" + getPrimaryImage() +
                ", genres=" + getGenres() +
                ", titleText=" + getlTitleText() +
                ", releaseYear=" + getReleaseYear() +
                ", runtime=" + getRuntime() +
                ", plot=" + getPlot() +
                ", inDB=" + getInDB() +
                '}';
    }


}

