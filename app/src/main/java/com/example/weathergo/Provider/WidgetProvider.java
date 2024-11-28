package com.example.weathergo.Provider;

import static android.util.TypedValue.COMPLEX_UNIT_SP;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.weathergo.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import com.example.weathergo.Activities.MainActivity;

public class WidgetProvider extends AppWidgetProvider {
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        Log.d("WidgetProvider", "onUpdate called");

        // Lấy dữ liệu từ SharedPreferences hoặc từ API nếu cần
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        String city = sharedPreferences.getString("location","");
        String url = "http://api.weatherapi.com/v1/current.json?key=09842721b7d347b085c154111242511&q="+city+"&aqi=no";
        for (int appWidgetId : appWidgetIds) {
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            // Intent mở ứng dụng
            Intent intent = new Intent(context, MainActivity.class);

            // PendingIntent cho widget
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_MUTABLE
            );

            // Gán PendingIntent cho view chính của widget
            views.setOnClickPendingIntent(R.id.openApplication, pendingIntent);

            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                try {
                    views.setTextViewText(R.id.cityTxtWidget, city);
                    // nhiet do o khung chinh
                    JSONObject currentObject = response.getJSONObject("current");
                    double temperature = currentObject.getDouble("temp_c");
                    String dispTempCelText = String.valueOf((int) temperature);
                    double temperatureF = temperature*9/5+32;
                    String dispTempFahText = String.valueOf((int) temperatureF);

                    String selectedUnit = sharedPreferences.getString("unit","Celsius");

                    if (selectedUnit.equals("Celsius")) {
                        Log.d("Nhiệt độ hiện widget: ", dispTempCelText);
                        views.setTextViewText(R.id.mainTempTxtWidget, dispTempCelText+"°C");
                    }
                    else if (selectedUnit.equals("Fahrenheit")){
                        Log.d("Đơn vị đã chọn: ", selectedUnit);
                        views.setTextViewText(R.id.mainTempTxtWidget, dispTempFahText+"°F");
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
                    views.setTextViewText(R.id.conditionTxtWidget, displayValue);
                    switch (displayValue){
                        case "Mưa nặng hạt có sấm sét":
                            views.setTextViewTextSize(R.id.conditionTxtWidget,  COMPLEX_UNIT_SP, 14);
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.rain_thunder);
                            break;
                        case "Mưa có sấm sét":
                            views.setTextViewTextSize(R.id.conditionTxtWidget,  COMPLEX_UNIT_SP, 15);
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.rain_thunder);
                            break;
                        case"Có thể mưa":
                            views.setTextViewTextSize(R.id.conditionTxtWidget,  COMPLEX_UNIT_SP, 20);
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.moderate_rain);
                            break;
                        case "Âm u":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.overcast);
                            break;
                        case "Mưa nhỏ":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.moderate_rain);
                            break;
                        case "Mưa vừa":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.moderate_rain);
                            break;
                        case "Mây rải rác":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.cloudy_main_frame);
                            Log.d("Result","Load gif thành công");
                            break;
                        case "Trời quang":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.clear_mainframe);
                            Log.d("Result","Load gif thành công");
                            break;
                        case "Nắng":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.sunnygif_mainframe);
                            break;
                        case "Mưa":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.rain_condition_mainframe);
                            break;
                        case "Sương mù":
                            views.setImageViewResource(R.id.conditionViewWidget, R.drawable.mist_mainframe);
                            break;
                    }
                    appWidgetManager.updateAppWidget(appWidgetId, views);
                } catch (JSONException e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }, error -> Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show());
            queue.add(request);

        }
    }
}
