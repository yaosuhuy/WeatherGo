package com.example.weathergo.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weathergo.Adapters.DailyAdapter;
import com.example.weathergo.Domains.Daily;
import com.example.weathergo.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class NextDaysFragment extends Fragment {
    private RecyclerView dailyView;
    private ArrayList<Daily> items;
    private DailyAdapter adapterDaily;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail, container, false);
        View view = inflater.inflate(R.layout.fragment_next_days, container, false);


        dailyView = view.findViewById(R.id.dailyView);

        items = new ArrayList<>();
        initRecyclerView();
        return view;

    }

    private void initRecyclerView() {
        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = "hanoi";
        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "http://api.weatherapi.com/v1/forecast.json?key=3e863d90628d41b2a6e72023232709&q=Hanoi&days=5&aqi=no&alerts=no";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                Date date = new Date();
                String dateString = String.valueOf(date);
                String hourStringWithMinutes = dateString.substring(11, 16);
                String hourString = dateString.substring(11, 13);
                Log.d("Ngày hiện tại: ", dateString);
                Log.d("Giờ hiện tại: ", hourStringWithMinutes);
                // Lấy mảng "hour" từ JSON
                JSONArray forecastdayArray = response.getJSONObject("forecast").getJSONArray("forecastday");


                for (int i = 1; i < forecastdayArray.length(); i++) {

                    // Chọn phần tử cụ thể, ví dụ: phần tử đầu tiên trong danh sách
                    JSONObject firstForecast = forecastdayArray.getJSONObject(i);

                    Integer maxTempC = firstForecast.getJSONObject("day").getInt("maxtemp_c");
                    Integer minTempC = firstForecast.getJSONObject("day").getInt("mintemp_c");

                    String test = Integer.toString(maxTempC);
                    Log.d("Kết quả", test);

                    String condition = firstForecast.getJSONObject("day").getJSONObject("condition").getString("text");
                    String icon = firstForecast.getJSONObject("day").getJSONObject("condition").getString("icon");

                    Log.d("Kết quả", condition);
                    Log.d("Kết quả", icon);
                    String time = firstForecast.getString("date");
                    Log.d("Kết quả", time);
                    String subTime = time.substring(5,10);


                    // Cai nay de anh xa gia tri voi api (hoi hoi na na if else)
                    HashMap<String, String> weatherMapping = new HashMap<>();

                    // vi du cai nay kieu nhu la "neu gia tri tu api la "clear" thi minh se hien thi len man hinh la "troi quang"
                    weatherMapping.put("Clear", "Trời quang");
                    weatherMapping.put("Rainy", "Mưa");
                    weatherMapping.put("Cloudy", "Nhiều mây");
                    weatherMapping.put("Partly cloudy", "Mây rải rác");
                    weatherMapping.put("Sunny", "Nắng");
                    weatherMapping.put("Mist", "Sương mù");
                    weatherMapping.put("Moderate rain", "Mưa vừa");
                    weatherMapping.put("Light rain", "Mưa nhỏ");
                    weatherMapping.put("Overcast", "Âm u");
                    weatherMapping.put("Patchy rain possible", "Có thể mưa");

                    String displayValue = weatherMapping.get(condition);




                    items.add(new Daily(subTime, icon, displayValue, minTempC, maxTempC));


                }

                adapterDaily = new DailyAdapter(items, requireContext());
                dailyView.setAdapter(adapterDaily);
                dailyView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);

    }


}