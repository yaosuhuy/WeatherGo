package com.example.weathergo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weathergo.Adapters.HourlyAdapters;
import com.example.weathergo.Domains.Hourly;
import com.example.weathergo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class DetailFragment extends Fragment {
    TextView windDirectionTxt, visualTxt, uvTxt, gustTxt, precipTxt, pressureTxt;
    private RecyclerView hourlyView;
    private ArrayList<Hourly> items;
    private HourlyAdapters adapterHourly;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        windDirectionTxt = (TextView) view.findViewById(R.id.windDirectTxt);
        visualTxt = (TextView) view.findViewById(R.id.visTxt);
        uvTxt = (TextView) view.findViewById(R.id.uvTxt);
        gustTxt = (TextView) view.findViewById(R.id.gustTxt);
        precipTxt = (TextView) view.findViewById(R.id.precipTxt);
        pressureTxt = (TextView) view.findViewById(R.id.pressureTxt);

        hourlyView = view.findViewById(R.id.hourlyView);

        items = new ArrayList<>();
        initRecyclerView();
        getWeatherData();
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

    public void getWeatherData() {
        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = "hanoi";
        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "https://api.weatherapi.com/v1/current.json?key=3e863d90628d41b2a6e72023232709&q=Hanoi&aqi=no";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                // nhiet do o khung chinh
                JSONObject currentObject = response.getJSONObject("current");
                String windDirection = currentObject.getString("wind_dir");
                windDirectionTxt.setText(windDirection);

                // doan nay de lay tinh trang thoi tiet
                JSONObject conditionObject = currentObject.getJSONObject("condition");
                String textCondition = conditionObject.getString("text");

                // Cai nay de anh xa gia tri voi api (hoi hoi na na if else)
                HashMap<String, String> weatherMapping = new HashMap<>();

                // vi du cai nay kieu nhu la "neu gia tri tu api la "clear" thi minh se hien thi len man hinh la "troi quang"
                weatherMapping.put("Clear", "Trời quang");
                weatherMapping.put("Rainy", "Mưa");
                weatherMapping.put("Cloudy", "Nhiều mây");
                weatherMapping.put("Partly cloudy", "Mây rải rác");
                weatherMapping.put("Sunny", "Nắng");
                weatherMapping.put("Mist", "Sương mù");

                String displayValue = weatherMapping.get(textCondition);
//                conditionTxt.setText(displayValue);
//                switch (displayValue) {
//                    case "Mây rải rác":
//                        Glide.with(this).load(R.drawable.cloudy_main_frame).into(conditionGif);
//                        Log.d("Result", "Load gif thành công");
//                        break;
//                    case "Trời quang":
//                        Glide.with(this).load(R.drawable.clear_mainframe).into(conditionGif);
//                        Log.d("Result", "Load gif thành công");
//                        break;
//                    case "Nắng":
//                        Glide.with(this).load(R.drawable.sunnygif_mainframe).into(conditionGif);
//                        break;
//                    case "Mưa":
//                        Glide.with(this).load(R.drawable.rain_condition_mainframe).into(conditionGif);
//                        break;
//                    case "Sương mù":
//                        Glide.with(this).load(R.drawable.mist_mainframe).into(conditionGif);
//                        break;
//                }
//
//
//                // nhiet do cam nhan
//                int realFeelTemp = currentObject.getInt("feelslike_c");
//                realFeelTxt.setText(realFeelTemp + "" + "°C");
//
//                // do am
//                int humidity = currentObject.getInt("humidity");
//                humidityTxt.setText(humidity + "" + "%");
//
//                // toc do gio
//                double windSpeed = currentObject.getDouble("wind_kph");
//                windSpeedTxt.setText(windSpeed + "" + "km/h");
//
//                // luong may
//                int cloud = currentObject.getInt("cloud");
//                cloudTxt.setText(cloud + "" + "%");


            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}