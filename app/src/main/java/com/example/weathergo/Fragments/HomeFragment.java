package com.example.weathergo.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.weathergo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private SharedPreferences sharedPreferences;
    TextView cityTxt, realFeelTxt, cloudTxt, windSpeedTxt, humidityTxt, mainTempTxt, conditionTxt;
    ImageView conditionGif;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // trong fragment ko findview by id duoc, phai lam the nay
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        realFeelTxt = view.findViewById(R.id.realFeelTxt);
        mainTempTxt = view.findViewById(R.id.mainTempTxt);
        humidityTxt = view.findViewById(R.id.humidityTxt);
        windSpeedTxt = view.findViewById(R.id.windSpeedTxt);
        cloudTxt = view.findViewById(R.id.cloudTxt);
        conditionTxt = view.findViewById(R.id.conditionTxt);
        cityTxt = view.findViewById(R.id.cityTxt);
        conditionGif = view.findViewById(R.id.conditionView);
        // Inflate the layout for this fragment

        sharedPreferences = requireActivity().getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        getWeatherData();
        // return inflater.inflate(R.layout.fragment_home, container, false);
        return view;
    }

    public void getWeatherData() {
//        String apikey = "3e196e42d16c6b34d661461bffccea60";
        String city = sharedPreferences.getString("location","");
        Log.d("Thành phố hiện tại: ", city);

        // String url = "https://api.openweathermap.org/data/2.5/weather?q="+city+"&appid="+apikey+"";
        String url = "http://api.weatherapi.com/v1/current.json?key=09842721b7d347b085c154111242511&q="+city+"&aqi=no";
        RequestQueue queue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
            try {
                cityTxt.setText(city);
                // nhiet do o khung chinh
                JSONObject currentObject = response.getJSONObject("current");
                double temperature = currentObject.getDouble("temp_c");
                String tempCelText = String.valueOf((int) temperature);
                double temperatureF = temperature*9/5+32;
                String tempFahText = String.valueOf((int) temperatureF);

                String selectedUnit = sharedPreferences.getString("unit","Celsius");

                if (selectedUnit.equals("Celsius")) {
                    mainTempTxt.setText(tempCelText + "°");
                }
                else if (selectedUnit.equals("Fahrenheit")){
                    Log.d("Đơn vị đã chọn: ", selectedUnit);
                    mainTempTxt.setText(tempFahText + "°");
                }


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
                weatherMapping.put("Moderate rain", "Mưa vừa");
                weatherMapping.put("Light rain", "Mưa nhỏ");
                weatherMapping.put("Overcast", "Âm u");
                weatherMapping.put("Patchy rain possible", "Có thể mưa");
                weatherMapping.put("Patchy light rain with thunder", "Mưa có sấm sét");
                weatherMapping.put("Moderate or heavy rain with thunder", "Mưa nặng hạt có sấm sét");
                weatherMapping.put("Mist", "Sương mù");
                String displayValue = weatherMapping.get(textCondition);
                conditionTxt.setText(displayValue);
                switch (displayValue){
                    case "Mưa nặng hạt có sấm sét":
                        conditionTxt.setTextSize(14);
                        Glide.with(this).load(R.drawable.rain_thunder).into(conditionGif);
                        break;
                    case "Mưa có sấm sét":
                        conditionTxt.setTextSize(15);
                        Glide.with(this).load(R.drawable.rain_thunder).into(conditionGif);
                        break;
                    case"Có thể mưa":
                        conditionTxt.setTextSize(20);
                        Glide.with(this).load(R.drawable.moderate_rain).into(conditionGif);
                        break;
                    case "Âm u":
                        Glide.with(this).load(R.drawable.overcast).into(conditionGif);
                        break;
                    case "Mưa nhỏ":
                        Glide.with(this).load(R.drawable.moderate_rain).into(conditionGif);
                        break;
                    case "Mưa vừa":
                        Glide.with(this).load(R.drawable.moderate_rain).into(conditionGif);
                        break;
                    case "Mây rải rác":
                        Glide.with(this).load(R.drawable.cloudy_main_frame).into(conditionGif);
                        Log.d("Result","Load gif thành công");
                        break;
                    case "Trời quang":
                        Glide.with(this).load(R.drawable.clear_mainframe).into(conditionGif);
                        Log.d("Result","Load gif thành công");
                        break;
                    case "Nắng":
                        Glide.with(this).load(R.drawable.sunnygif_mainframe).into(conditionGif);
                        break;
                    case "Mưa":
                        Glide.with(this).load(R.drawable.rain_condition_mainframe).into(conditionGif);
                        break;
                    case "Sương mù":
                        Glide.with(this).load(R.drawable.mist_mainframe).into(conditionGif);
                        break;
                }


                // nhiet do cam nhan
                int realFeelTemp = currentObject.getInt("feelslike_c");
                int realFeelTemperatureF = realFeelTemp*9/5+32;
                if (selectedUnit.equals("Celsius")){
                    realFeelTxt.setText(realFeelTemp+""+"°C");
                }
                else if (selectedUnit.equals("Fahrenheit")){
                    realFeelTxt.setText(realFeelTemperatureF+""+"°F");
                }

                // do am
                int humidity = currentObject.getInt("humidity");
                humidityTxt.setText(humidity+""+"%");

                // toc do gio
                double windSpeed = currentObject.getDouble("wind_kph");
                windSpeedTxt.setText(windSpeed+""+"km/h");

                // luong may
                int cloud = currentObject.getInt("cloud");
                cloudTxt.setText(cloud+""+"%");


            } catch (JSONException e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}