package com.example.odz.adapters;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class YearPickerAdapter extends ArrayAdapter<String> {
    public YearPickerAdapter(Context context, List<String> years) {
        super(context, android.R.layout.simple_spinner_item, years);
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }
}
