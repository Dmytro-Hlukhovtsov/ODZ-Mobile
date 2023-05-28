package com.example.odz;


import com.example.odz.db.entities.Film;
import com.example.odz.models.FilmItem;

/**
 * Class to convert between model and entity
 */

public class FilmConverter {

    /**
     * Convert FilmItem model to Film entity
     * @param value FilmItem model
     * @return Film entity
     */
    public static Film convert(FilmItem value) {
        return new Film(
                value.getId(),
                value.getlTitleText(),
                value.getReleaseYear(),
                value.getPrimaryImage(),
                value.getRuntime(),
                value.getGenres(),
                value.getPlot(),
                value.getRatingsSummary()
        );
    }

}
