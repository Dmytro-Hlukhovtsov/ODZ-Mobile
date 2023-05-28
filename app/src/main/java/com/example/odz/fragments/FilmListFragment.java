package com.example.odz.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
//import com.example.odz.binding.
import com.example.odz.R;
import com.example.odz.adapters.FilmListAdapter;
import com.example.odz.adapters.YearPickerAdapter;
import com.example.odz.models.FilterModel;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;


public class FilmListFragment extends Fragment {
    private Spinner spinnerStartYearPicker, spinnerEndYearPicker;
    private LinearLayout filterItems;
    private FilmListAdapter filmListAdapter;
    private FilterModel filterModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.main_fragment, container, false);

        List<String> years = new ArrayList<>();
        years.add("-");
        for (int i = Year.now().getValue(); i >= 1900; i--) {
            years.add(String.valueOf(i));
        }
        spinnerStartYearPicker = view.findViewById(R.id.filterStartYear);
        spinnerEndYearPicker = view.findViewById(R.id.filterEndYear);
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
        initRecyclerView(view);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button searchButton = view.findViewById(R.id.searchButton);
        Button filterApplyButton = view.findViewById(R.id.applyFilters);
        TextView titleFilter = view.findViewById(R.id.filterFilmTitle);
        TextView genreFilter = view.findViewById(R.id.filterFilmGenre);

        filterApplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String filterTitle = null;
                String filterGenre = null;
                String filterStartYear = null;
                String filterEndYear = null;

                if(!titleFilter.getText().toString().equals(""))
                    filterTitle = String.valueOf(titleFilter.getText());
                if(!genreFilter.getText().toString().equals("")){
                    filterGenre = String.valueOf(genreFilter.getText());
                }
                Log.i("Spinner", spinnerStartYearPicker.getSelectedItem().toString());
                if(!spinnerStartYearPicker.getSelectedItem().toString().equals("-")){
                    filterStartYear = spinnerStartYearPicker.getSelectedItem().toString();
                }
                if(!spinnerEndYearPicker.getSelectedItem().toString().equals("-")){
                    filterEndYear = spinnerEndYearPicker.getSelectedItem().toString();
                }

                setFilterModel(new FilterModel(filterTitle, filterGenre, filterStartYear, filterEndYear));

                Log.i("FilterModel", filterModel.toString());
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filmListAdapter.initDataFilms(filterModel);
            }
        });
    }

    private void initRecyclerView(View rootView){
        RecyclerView recyclerView = rootView.findViewById(R.id.itemsContainer);
        if(filmListAdapter == null){
            filmListAdapter = new FilmListAdapter();
        }
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(filmListAdapter);
    }

    private void setFilterModel(FilterModel filterModel){
        this.filterModel = filterModel;
    }
}