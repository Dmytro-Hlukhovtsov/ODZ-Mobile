package com.example.odz.adapters;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.odz.FilmConverter;
import com.example.odz.R;
import com.example.odz.db.entities.Film;
import com.example.odz.services.FilmsRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

public class LibraryFilmAdapter extends RecyclerView.Adapter<LibraryFilmAdapter.LibraryFilmViewHolder>{

    private List<Film> mFilmList = Collections.emptyList();
    public LibraryFilmAdapter(){}

    @NonNull
    @Override
    public LibraryFilmAdapter.LibraryFilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
        return new LibraryFilmAdapter.LibraryFilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LibraryFilmAdapter.LibraryFilmViewHolder holder, int position) {
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        Film film = mFilmList.get(position);
        holder.setFilm(film);
        Button removeFromLibButton = holder.itemView.findViewById(R.id.addToLibBtn);
        removeFromLibButton.setText("Remove from library");
        removeFromLibButton.setBackgroundResource(R.color.color_lib_btn_to_remove);


        holder.filmTitle.setText(film.getTitle());
        holder.filmYear.setText(film.getYear());
        holder.filmGenre.setText(film.getGenres());
        holder.filmRate.setText("IMDb: "+ film.getRate());


        Glide.with(holder.itemView.getContext())
                .load(film.getUrl())
                .error(R.drawable.poster_error)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.filmPoster);

        removeFromLibButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... voids) {
                        filmsRepository.deleteFilm(film);
                        return null;
                    }

                    @Override
                    protected void onPostExecute(Void aVoid) {
                        Toast.makeText(v.getContext(), "Record Deleted", Toast.LENGTH_SHORT).show();
                        initLibraryDataFilms();

                    }
                }.execute();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilmList.size();
    }

    public static class LibraryFilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Film film;
        private TextView filmTitle, filmRate, filmYear, filmDuration, filmGenre;
        private ImageView filmPoster;

        public void setFilm(Film film) {
            this.film = film;
        }
        public LibraryFilmViewHolder(@NonNull View itemView) {
            super(itemView);
            filmTitle = itemView.findViewById(R.id.filmTitle);
            filmYear = itemView.findViewById(R.id.filmYear);
            filmGenre = itemView.findViewById(R.id.filmGenres);
            filmRate = itemView.findViewById(R.id.filmRate);
            filmPoster = itemView.findViewById(R.id.filmImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            bundle.putString("filmId", film.getImdbId());
            bundle.putString("filmTitle", film.getTitle());
            bundle.putString("filmImage", film.getUrl());
            bundle.putString("filmRate", film.getRate());
            bundle.putString("filmYear", film.getYear());
            bundle.putString("filmDuration", film.getDuration());
            bundle.putString("filmGenres", film.getGenres());
            bundle.putString("filmPlot", film.getPlot());
            bundle.putBoolean("inDB", true);


            Navigation.findNavController(view).navigate(R.id.action_libraryFragment_to_detailsFragment, bundle);

        }
    }

    public void setmFilmList(List<Film> list){
        this.mFilmList = list;
    }

    public void initLibraryDataFilms(){
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        filmsRepository.loadFilms(null)
                .subscribe(new SingleObserver<List<Film>>() {
                    @Override
                    public void onSubscribe(Disposable d) { }

                    @Override
                    public void onSuccess(List<Film> films) {
                        setmFilmList(films);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("LibraryFilmAdapter", e.toString());
                    }
                });

    }


}
