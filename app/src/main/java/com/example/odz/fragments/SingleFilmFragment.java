package com.example.odz.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.odz.R;
import com.example.odz.db.entities.Film;
import com.example.odz.services.FilmsRepository;

public class SingleFilmFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.film_details_fragment, container, false);
        FilmsRepository filmsRepository = FilmsRepository.getInstance();
        Film filmItem = null;
        Bundle args = getArguments();
        TextView title = view.findViewById(R.id.descTitle);
        ImageView image = view.findViewById(R.id.descImage);
        TextView duration = view.findViewById(R.id.descDuration);
        TextView year = view.findViewById(R.id.descYear);
        TextView rate = view.findViewById(R.id.descRate);
        TextView genres = view.findViewById(R.id.descGenres);
        TextView plot = view.findViewById(R.id.descPlot);
        Button addButton = view.findViewById(R.id.addToLib);

        if( args != null){
            filmItem = new Film(
                    args.getString("filmId"),
                    args.getString("filmTitle"),
                    args.getString("filmYear"),
                    args.getString("filmImage"),
                    args.getString("filmDuration"),
                    args.getString("filmGenres"),
                    args.getString("filmPlot"),
                    args.getString("filmRate")
            );
            title.setText(args.getString("filmTitle"));
            duration.setText(args.getString("filmDuration"));
            year.setText(args.getString("filmYear"));
            rate.setText("IMDb: " + args.getString("filmRate"));
            genres.setText(args.getString("filmGenres"));
            plot.setText(args.getString("filmPlot"));

            Glide.with(this)
                    .load(args.getString("filmImage"))
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .into(image);

            if(args.getBoolean("inDB")){
                addButton.setText("Already in library");
                addButton.setBackgroundResource(R.color.color_lib_btn_in_db);
            }
        }


        Film finalFilmItem = filmItem;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Void, Void, Integer>() {
                    @Override
                    protected Integer doInBackground(Void... voids) {
                        return filmsRepository.checkInDB(args.getString("filmId"));
                    }

                    @Override
                    protected void onPostExecute(Integer result) {
                        if (result != 0) {
                            Toast.makeText(v.getContext(), "Record already exists", Toast.LENGTH_SHORT).show();

                        } else {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    filmsRepository.insertFilm(finalFilmItem);
                                    return null;
                                }

                                @Override
                                protected void onPostExecute(Void aVoid) {
                                    Toast.makeText(v.getContext(), "Record Added", Toast.LENGTH_SHORT).show();
                                    Button btn = v.findViewById(R.id.addToLib);
                                    btn.setText("Already in library");
                                    btn.setBackgroundResource(R.color.color_lib_btn_in_db);
                                }
                            }.execute();
                        }
                    }
                }.execute();
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
