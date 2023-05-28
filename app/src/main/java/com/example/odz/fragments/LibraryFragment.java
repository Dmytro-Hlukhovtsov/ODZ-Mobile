package com.example.odz.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.odz.R;
import com.example.odz.adapters.FilmListAdapter;
import com.example.odz.adapters.LibraryFilmAdapter;

public class LibraryFragment extends Fragment {
    private LibraryFilmAdapter libraryFilmAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.library_fragment, container, false);
        initRecyclerView(view);
        libraryFilmAdapter.initLibraryDataFilms();
        return view;
    }

    private void initRecyclerView(View rootView){
        RecyclerView recyclerView = rootView.findViewById(R.id.libraryItemsContainer);
        if(libraryFilmAdapter == null){
            libraryFilmAdapter = new LibraryFilmAdapter();
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(libraryFilmAdapter);
    }
}
