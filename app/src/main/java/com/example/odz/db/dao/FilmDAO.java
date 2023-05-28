package com.example.odz.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.odz.db.entities.Film;

import java.util.List;

import io.reactivex.Flowable;


/**
 * Film DAO interface
 */
@Dao
public interface  FilmDAO {

    /**
     * Selecting all films from database
     * @return Flowable list of films
     */
    @Query("SELECT * FROM films")
    Flowable<List<Film>> loadAllFilms();

    @Query("SELECT * FROM films WHERE title LIKE :keyword")
    Flowable<List<Film>> loadAllFilmsWithKeyword(String keyword);

    /**
     * Selecting film by imdbId
     * @param id it;s id for the search
     * @return Flowable Film entity
     */
    @Query("SELECT * FROM films WHERE imdbId LIKE :id")
    Flowable<Film> loadFilmbyId(String id);

    @Query("SELECT count(*) FROM films WHERE imdbId LIKE :id")
    int checkFilmById(String id);



    /**
     * Inserting film to database
     *
     * @param film it's film for insert
     * @return
     */
    @Insert
    long insertFilm(Film film);

    /**
     * Deleting film from database
     * @param film it's entity film for deleting
     */
    @Delete
    void deleteFilm(Film film);

    /**
     * Updating films in database
     * @param films for update
     */
    @Update
    void updateFilms(List<Film> films);

    /**
     * Updating film in database
     * @param film for update
     */
    @Update
    void updateFilm(Film film);




}
