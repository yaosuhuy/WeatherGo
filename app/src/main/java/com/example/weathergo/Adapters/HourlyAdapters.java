package com.example.weathergo.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.weathergo.Domains.Daily;
import com.example.weathergo.Domains.Hourly;
import com.example.weathergo.R;

import java.util.ArrayList;

public class HourlyAdapters extends RecyclerView.Adapter<HourlyAdapters.viewHolder> {
    ArrayList<Hourly> items;
    Context context;

    public HourlyAdapters(ArrayList<Hourly> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public HourlyAdapters.viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // inflate la gi??
//        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly, parent, false);
//        context = parent.getContext();
//        return new viewHolder(inflate);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_hourly, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HourlyAdapters.viewHolder holder, int position) {
        holder.hourTxt.setText(items.get(position).getHour());
        holder.tempTxt.setText(items.get(position).getTemp() + "°");

        // int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position).getPicPath(), "drawable", holder.itemView.getContext().getPackageName());

//        Glide.with(context).load().into(holder.pic);

//        String item = String.valueOf(items.get(position));
//        holder.tempTxt.setText(item);

        String picPath = items.get(position).getPicPath();
        Log.d("PicPathDebug", "PicPath: " + picPath); // In đường dẫn vào Logcat
        Glide.with(holder.itemView.getContext()).load("https:"+picPath).into(holder.pic);
        Log.d("Test", "Load ảnh thành công");
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        TextView hourTxt, tempTxt;
        ImageView pic;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            hourTxt = itemView.findViewById(R.id.hourTxt);
            tempTxt = itemView.findViewById(R.id.tempTxt);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
