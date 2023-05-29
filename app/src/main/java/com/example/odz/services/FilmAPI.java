package com.example.odz.services;


import com.example.odz.models.FilmItem;
import com.example.odz.models.SearchModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface FilmAPI {



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

}
