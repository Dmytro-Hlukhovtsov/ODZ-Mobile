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
import com.example.odz.models.FilmItem;
import com.example.odz.models.FilterModel;
import com.example.odz.services.FilmsRepository;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {
    private static String ITEM_KEY = "film";
    private List<FilmItem> mFilmList = Collections.emptyList();

    public FilmListAdapter() {}

    @NonNull
    @Override
    public FilmListAdapter.FilmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmViewHolder holder, int position) {
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        FilmItem filmItem = mFilmList.get(position);
        holder.setFilm(filmItem);
        Button addToLibButton = holder.itemView.findViewById(R.id.addToLibBtn);


        if(filmItem.getInDB()){
            addToLibButton.setText("Already in library");
            addToLibButton.setBackgroundResource(R.color.color_lib_btn_in_db);
            Log.i(FilmListAdapter.class.getCanonicalName(), filmItem.getId());
        }

        holder.filmTitle.setText(filmItem.getlTitleText());
        holder.filmYear.setText(filmItem.getReleaseYear());
        holder.filmGenre.setText(filmItem.getGenres());
        holder.filmRate.setText("IMDb: "+ filmItem.getRatingsSummary());


        Glide.with(holder.itemView.getContext())
                .load(filmItem.getPrimaryImage())
                .error(R.drawable.poster_error)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .into(holder.filmPoster);




        addToLibButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        return filmsRepository.checkInDB(filmItem.getId());
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        if (result != 0) {
                            Toast.makeText(v.getContext(), "Record already exists", Toast.LENGTH_SHORT).show();

                        } else {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    filmsRepository.insertFilm(FilmConverter.convert(filmItem));
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    Toast.makeText(v.getContext(), "Record Added", Toast.LENGTH_SHORT).show();
                                    addToLibButton.setText("Already in library");
                                    addToLibButton.setBackgroundResource(R.color.color_lib_btn_in_db);
                                }
                            }.execute();
                        }
                    }
                }.execute();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mFilmList.size();
    }

    class FilmViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView filmTitle, filmRate, filmYear, filmDuration, filmGenre;
        private ImageView filmPoster;
        private FilmItem film;
        public void setFilm(FilmItem film) {
            this.film = film;
        }
        public FilmViewHolder(@NonNull View itemView) {
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
            bundle.putString("filmId", film.getId());
            bundle.putString("filmTitle", film.getlTitleText());
            bundle.putString("filmImage", film.getPrimaryImage());
            bundle.putString("filmRate", film.getRatingsSummary());
            bundle.putString("filmYear", film.getReleaseYear());
            bundle.putString("filmDuration", film.getRuntime());
            bundle.putString("filmGenres", film.getGenres());
            bundle.putString("filmPlot", film.getPlot());
            bundle.putBoolean("inDB", film.getInDB());


            Navigation.findNavController(view).navigate(R.id.action_mainFragment_to_detailsFragment, bundle);
        }
    }

    public void setmFilmList(List<FilmItem> list){
        this.mFilmList = list;
    }
    public void initDataFilms(FilterModel filterModel){
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        filmsRepository.getFilmList(filterModel)
                .subscribeOn(Schedulers.io()) // Specify the scheduler for background execution
                .observeOn(AndroidSchedulers.mainThread()) // Specify the scheduler for UI updates
                .subscribe(new Observer<List<FilmItem>>() {
                    @Override
                    public void onSubscribe(Disposable d) {}

                    @Override
                    public void onNext(List<FilmItem> filmItems) {
                        setmFilmList(filmItems);
                        notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("FilmListAdapter", e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }



}
