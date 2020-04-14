package com.example.timekeeper;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Shift> mShifts;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<Shift> days) {
        this.mInflater = LayoutInflater.from(context);
        this.mShifts = days;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd");

        holder.dateTextView.setText(sdf.format(mShifts.get(position).getDate()));
        holder.sunMoonImageView.setImageResource(R.drawable.sun_calendar);
        holder.overtimeTextView.setText(Double.toString(mShifts.get(position).getOvertime()));

    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mShifts.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dateTextView;
        ImageView sunMoonImageView;
        TextView overtimeTextView;


        ViewHolder(View itemView) {
            super(itemView);
            dateTextView = itemView.findViewById(R.id.date_text);
            itemView.setOnClickListener(this);
            sunMoonImageView = itemView.findViewById(R.id.sun_moon_icon_view);
            overtimeTextView = itemView.findViewById(R.id.overtime_text);


        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    String getItem(int id) {
        return mShifts.get(id).toString();
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
