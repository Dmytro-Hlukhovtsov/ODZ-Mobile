package com.example.odz.services;


import com.example.odz.models.FilmItem;
import com.example.odz.models.SearchModel;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface FilmAPI {
    String API_KEY = "?apikey=67c8e857";



    @GET("/titles")
    Observable<SearchModel> getShortFilmsDescription(
            @Query("list") String list,
            @Query("limit") int limit,
            @Query("info") String info);


    /**
     * Getting full film description by imdbID
     * @param imdbID id for the search
     * @return FilmItem
     */
    @GET(API_KEY)
    Observable<FilmItem> getLongFilmDescription(@Query("i") String imdbID);




}
