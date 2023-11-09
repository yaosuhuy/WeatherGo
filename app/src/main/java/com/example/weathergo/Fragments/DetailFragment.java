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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
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
        hourlyView.setBackgroundColor(getResources().getColor(R.color.primary));

        items = new ArrayList<>();
        initRecyclerView();
        getWeatherData();
        return view;

    }

    private void initRecyclerView() {
        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = "hanoi";
        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "https://api.weatherapi.com/v1/forecast.json?key=3e863d90628d41b2a6e72023232709&q=Hanoi&days=1&aqi=no&alerts=no";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                Date date = new Date();
                String dateString = String.valueOf(date);
                String hourStringWithMinutes = dateString.substring(11,16);
                String hourString = dateString.substring(11,13);
                Log.d("Ngày hiện tại: ", dateString);
                Log.d("Giờ hiện tại: ", hourStringWithMinutes);
                // Lấy mảng "hour" từ JSON
                JSONArray hourArray = response.getJSONObject("forecast").getJSONArray("forecastday").getJSONObject(0).getJSONArray("hour");


                for (int i=1; i<hourArray.length();i++){

                    // Chọn phần tử cụ thể, ví dụ: phần tử đầu tiên trong danh sách
                    JSONObject firstHour = hourArray.getJSONObject(i);

                    // Lấy giá trị "temp_c" từ phần tử cụ thể
                    Integer tempC = firstHour.getInt("temp_c");

                    JSONObject conditionObject = firstHour.getJSONObject("condition");
                    String icon = conditionObject.getString("icon");
                    Log.d("Kết quả", icon);
                    String time = firstHour.getString("time");
                    String subTimeWithMinutes = time.substring(11,16);

                    String subTime = time.substring(11,13);
                    int result = hourString.compareTo(subTime);
                    if (result < 0){
                        items.add(new Hourly(subTimeWithMinutes, tempC, icon));
                    }
                    else if (result == 0){
                        items.add(new Hourly("Bây giờ", tempC, icon));
                    }


                }


                adapterHourly = new HourlyAdapters(items, requireContext());
                hourlyView.setAdapter(adapterHourly);
                hourlyView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);

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

                // Cai nay de anh xa gia tri voi api (hoi hoi na na if else)
                HashMap<String, String> weatherMapping = new HashMap<>();

                // vi du cai nay kieu nhu la "neu gia tri tu api la "clear" thi minh se hien thi len man hinh la "troi quang"
                weatherMapping.put("S", "Nam");
                weatherMapping.put("N", "Bắc");
                weatherMapping.put("W", "Tây");
                weatherMapping.put("E", "Đông");
                weatherMapping.put("SSE", "Nam - Đông Nam");
                weatherMapping.put("NNE", "Bắc - Đông Bắc");
                weatherMapping.put("NE", "Đông Bắc");
                weatherMapping.put("NW", "Tây Bắc");
                weatherMapping.put("SW", "Tây Nam");
                weatherMapping.put("SE", "Đông Nam");

                String displayValue = weatherMapping.get(windDirection);
                windDirectionTxt.setText(displayValue);

                // Tam nhin
                Double visual = currentObject.getDouble("vis_km");
                visualTxt.setText(visual+""+"km");

                // UV
                Double uv = currentObject.getDouble("uv");
                uvTxt.setText(uv+"");

                // gust
                Double gust = currentObject.getDouble("gust_kph");
                gustTxt.setText(gust+""+"km/h");

                // precip
                Double precip = currentObject.getDouble("precip_mm");
                precipTxt.setText(precip+""+"mm");

                // ap luc (don vi mb = milibar)
                Double pressure = currentObject.getDouble("pressure_mb");
                pressureTxt.setText(pressure+""+"mb");


            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}