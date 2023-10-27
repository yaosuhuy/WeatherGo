package com.example.weathergo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weathergo.Adapters.HourlyAdapters;
import com.example.weathergo.Domains.Hourly;
import com.example.weathergo.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class DetailFragment extends Fragment {
    private RecyclerView hourlyView;
    private ArrayList<Hourly> items;
    private HourlyAdapters adapterHourly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        hourlyView = view.findViewById(R.id.hourlyView);

        items = new ArrayList<>();
        initRecyclerView();
        return view;

    }

    private void initRecyclerView() {
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "sunny_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));
        items.add(new Hourly("Bây giờ", 2323, "rain_status"));


        adapterHourly = new HourlyAdapters(items, requireContext());
        hourlyView.setAdapter(adapterHourly);
        hourlyView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
    }
}