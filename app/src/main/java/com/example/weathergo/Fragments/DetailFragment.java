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

import java.util.ArrayList;

public class DetailFragment extends Fragment {
    RecyclerView hourlyView;
    private RecyclerView.Adapter adapterHourly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        hourlyView = view.findViewById(R.id.hourlyView);
        initRecyclerView();
        return view;

    }

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("Bây giờ", 23, "sunny_status"));
        items.add(new Hourly("Bây giờ", 24, "sunny_status"));
        items.add(new Hourly("Bây giờ", 25, "sunny_status"));
        items.add(new Hourly("Bây giờ", 26, "sunny_status"));
        items.add(new Hourly("Bây giờ", 27, "sunny_status"));


        hourlyView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        adapterHourly = new HourlyAdapters(items, getContext());
        hourlyView.setAdapter(adapterHourly);
    }

}