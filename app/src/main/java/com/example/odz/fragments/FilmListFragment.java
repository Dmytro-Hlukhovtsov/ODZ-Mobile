package com.example.odz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
//import com.example.odz.binding.
import com.example.odz.R;
import com.example.odz.adapters.FilmListAdapter;
import com.example.odz.adapters.YearPickerAdapter;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class FilmListFragment extends Fragment {
    private Spinner spinnerStartYearPicker, spinnerEndYearPicker;
    private LinearLayout filterItems;
    private FilmListAdapter filmListAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        initRecyclerView(view);
        List<String> years = new ArrayList<>();
        years.add("-");
        for (int i = 1900; i <= Year.now().getValue(); i++) {
            years.add(String.valueOf(i));
        }
        spinnerStartYearPicker = view.findViewById(R.id.startYear);
        spinnerEndYearPicker = view.findViewById(R.id.endYear);
        YearPickerAdapter adapter = new YearPickerAdapter(requireContext(), years);
        spinnerStartYearPicker.setAdapter(adapter);
        spinnerEndYearPicker.setAdapter(adapter);

        filterItems = view.findViewById(R.id.filterItems);
        Button filterOpenButton = view.findViewById(R.id.showFilters);
        filterOpenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer visibility = filterItems.getVisibility();
                if(visibility == View.INVISIBLE){
                    filterItems.setVisibility(View.VISIBLE);
                    filterItems.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                }
                else{
                    filterItems.setVisibility(View.INVISIBLE);
                    filterItems.getLayoutParams().height = 0;
                }
                filterItems.requestLayout();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button searchButton = view.findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filmListAdapter.initDataFilms();
            }
        });
    }

    private void initRecyclerView(View rootView){
        RecyclerView recyclerView = rootView.findViewById(R.id.itemsContainer);
        if(filmListAdapter == null){
            filmListAdapter = new FilmListAdapter();
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(filmListAdapter);
        }

    }
}