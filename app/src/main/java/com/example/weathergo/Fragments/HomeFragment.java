package com.example.weathergo.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weathergo.R;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {
    TextView realFeelTxt, cloudTxt, windSpeedTxt, humidityTxt, mainTempTxt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // trong fragment ko findview by id duoc, phai lam the nay
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        realFeelTxt = (TextView) view.findViewById(R.id.realFeelTxt);
        mainTempTxt = (TextView) view.findViewById(R.id.mainTempTxt);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
        // return view;
    }

    public void get(View v) {
        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = "hanoi";
        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=3e196e42d16c6b34d661461bffccea60";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null , response -> {
            try {
                JSONObject object = response.getJSONObject("main");
                String temperature = object.getString("temp");
                mainTempTxt.setText(temperature);
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}