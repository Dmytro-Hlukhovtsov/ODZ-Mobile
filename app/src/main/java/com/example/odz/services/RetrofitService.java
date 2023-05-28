package com.example.odz.services;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Service class for work with API
 */
public class RetrofitService {
    private static RetrofitService retrofitService;

    private static final String BASE_URL = "https://moviesdatabase.p.rapidapi.com/";
    private static final String API_KEY = "3d528c7818msh99afd7ad13279d6p10e7b4jsnf235941d3444";
    private RxJava2CallAdapterFactory rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io());

    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(chain -> chain.proceed(chain.request().newBuilder()
                    .addHeader("X-RapidAPI-Host", "moviesdatabase.p.rapidapi.com")
                    .addHeader("X-RapidAPI-Key", API_KEY)
                    .build()))
            .build();
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(rxAdapter)
            .build();


    /**
     * Creating film service
     * @return created FilmServiceAPI
     */
    public FilmAPI createFilmService() {
        return retrofit.create(FilmAPI.class);
    }

    /**
     * Getting instance of service
     * @return instance
     */
    public static RetrofitService getInstance() {
        if(retrofitService == null) {
            retrofitService = new RetrofitService();
        }
        return retrofitService;
    }

}
