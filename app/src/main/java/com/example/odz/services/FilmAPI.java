package com.example.odz.services;


import com.example.odz.models.FilmItem;
import com.example.odz.models.SearchModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface FilmAPI {
    String API_KEY = "?apikey=67c8e857";



    @GET("/titles")
    Observable<SearchModel> getFilmsList(
            @Query("list") String list,
            @Query("limit") int limit,
            @Query("info") String info,
            @Query("genre") String genre,
            @Query("startYear") String startYear,
            @Query("endYear") String endYear)
            ;

    @GET("/titles/search/title/{title}")
    Observable<SearchModel> getFilmsListByTitle(
            @Path("title") String title,
            @Query("exact") String exact,
            @Query("info") String info,
            @Query("limit") int limit,
            @Query("startYear") String startYear,
            @Query("endYear") String endYear,
            @Query("genre") String genre)
            ;


    /**
     * Getting full film description by imdbID
     * @param imdbID id for the search
     * @return FilmItem
     */
    @GET(API_KEY)
    Observable<FilmItem> getLongFilmDescription(@Query("i") String imdbID);




}
