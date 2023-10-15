package com.example.weathergo.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.weathergo.Domains.Hourly;
import com.example.weathergo.R;

import java.util.ArrayList;

public class DetailFragment extends Fragment {

    private RecyclerView.Adapter adapterHourly;
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false);

        // initRecyclerView();
    }

    private void initRecyclerView() {
        ArrayList<Hourly> items = new ArrayList<>();

        items.add(new Hourly("", 23, ""));
        items.add(new Hourly("", 23, ""));
        items.add(new Hourly("", 23, ""));
        items.add(new Hourly("", 23, ""));
        items.add(new Hourly("", 23, ""));

    }
}