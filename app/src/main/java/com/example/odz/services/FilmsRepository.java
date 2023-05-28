package com.example.odz.services;


import android.annotation.SuppressLint;
import android.util.Log;

import com.example.odz.db.AppDatabase;
import com.example.odz.db.dao.FilmDAO;
import com.example.odz.db.entities.Film;
import com.example.odz.models.FilmItem;
import com.example.odz.models.FilterModel;
import com.example.odz.models.SearchModel;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FilmsRepository {
    private static final String TAG = FilmsRepository.class.getCanonicalName();
    private static FilmsRepository filmsRepository;
    private FilmAPI filmAPI;
    private FilmDAO filmDao;
    private Disposable dbDisp;

    /**
     * Getting instance of film repository
     * @return FilmRepository instance
     */
    public static FilmsRepository getInstance() {
        if (filmsRepository == null) {
            filmsRepository = new FilmsRepository();
        }
        return filmsRepository;
    }

    private FilmsRepository() {
        filmAPI = RetrofitService.getInstance().createFilmService();
        filmDao = AppDatabase.db.filmDAO();
    }


    public int checkInDB(String id){
        return filmDao.checkFilmById(id);
    }
    /**
     * Loading film list from database
     * @return Flowable FilmList
     */
    public Single<List<Film>> loadFilms(String keyword) {
        Flowable<List<Film>> dbFilmsFlowable;
        if (keyword != null) {
            dbFilmsFlowable = filmDao.loadAllFilmsWithKeyword("%" + keyword + "%");
        } else {
            dbFilmsFlowable = filmDao.loadAllFilms();
        }

        return dbFilmsFlowable
                .firstOrError()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }



    @SuppressLint("CheckResult")
    public Observable<List<FilmItem>> getFilmList(FilterModel filterModel) {
        Observable<SearchModel> apiAllFilms;
        if(filterModel != null){

            if(filterModel.getTitle() != null){
                apiAllFilms = filmAPI.getFilmsListByTitle(filterModel.getTitle(), "false", "base_info", 25, filterModel.getStartYear(), filterModel.getEndYear(), filterModel.getGenre());
                Log.i("Title of Film", filterModel.getTitle());
            }
            else{
                apiAllFilms = filmAPI.getFilmsList("top_rated_250", 25, "base_info", filterModel.getGenre(), filterModel.getStartYear(), filterModel.getEndYear());
            }
        }
        else{
            apiAllFilms = filmAPI.getFilmsList("top_rated_250", 25, "base_info", null, null, null);
        }

        return apiAllFilms.flatMap(searchModel ->
                loadFilms(null)
                        .toObservable()
                        .map(films -> findCommonObjects(searchModel.getResults(), films))
        );
    }

    public List<FilmItem> findCommonObjects(List<FilmItem> apiItems, List<Film> dbItems) {
        for (FilmItem object1 : apiItems) {
            for (Film object2 : dbItems) {
                if (object1.getId() != null && object2.getImdbId() != null && object1.getId().equals(object2.getImdbId())) {
                    object1.setInDB(true);
                    Log.i(TAG, "You are here" + object1.toString());
                }
            }
        }
        return apiItems;
    }

    /**
     * Wrapping method for inserting films to db
     * @param film for insert
     */
    public long insertFilm(Film film) {
        return filmDao.insertFilm(film);
    }

    public void deleteFilm(Film film) { filmDao.deleteFilm(film); }

}
