package com.example.weathergo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
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
        humidityTxt = (TextView) view.findViewById(R.id.humidityTxt);
        windSpeedTxt = (TextView) view.findViewById(R.id.windSpeedTxt);
        cloudTxt = (TextView) view.findViewById(R.id.cloudTxt);
        // Inflate the layout for this fragment
        getWeatherData();
        // return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    public void getWeatherData() {
        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = "hanoi";
        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "https://api.openweathermap.org/data/2.5/weather?q=hanoi&appid=3e196e42d16c6b34d661461bffccea60";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                JSONObject mainObject = response.getJSONObject("main");
                JSONObject windObject = response.getJSONObject("wind");
                JSONObject cloudObject = response.getJSONObject("clouds");
                double temperature = mainObject.getDouble("temp");
                double tempCel = (int) temperature - 273.15;
                String tempCelText = String.valueOf((int) tempCel);
                mainTempTxt.setText(tempCelText);

                double realFeelTemp = mainObject.getDouble("feels_like");
                double realFeelTempCel = (int) realFeelTemp - 273.15;
                String realFeelTempCelText = String.valueOf((int) realFeelTempCel);
                realFeelTxt.setText(realFeelTempCelText+"°C");

                double humidity = mainObject.getDouble("humidity");
                String humidityText = String.valueOf((int) humidity);
                humidityTxt.setText(humidityText+"%");

                double windSpeed = windObject.getDouble("speed");
                windSpeedTxt.setText(windSpeed+""+"m/s");

                double cloud = cloudObject.getDouble("all");
                String cloudText = String.valueOf((int) cloud);
                cloudTxt.setText(cloudText+"%");
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}