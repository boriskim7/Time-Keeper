package com.example.timekeeper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timekeeper.R.drawable;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    private ArrayList<Shift> mShifts;
    private LayoutInflater mInflater;
    private Calendar calendarR = MainActivity.currentDate;
    private int currentMonth = calendarR.MONTH +1;
    Context c;

    // data is passed into the constructor
    MyRecyclerViewAdapter(Context context, ArrayList<Shift> shifts) {
        mInflater = LayoutInflater.from(context);
        mShifts = shifts;
    }

    // inflates the scell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recyclerview_item, parent, false);

        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final SimpleDateFormat sdf = new SimpleDateFormat("dd");
        holder.dateTextView.setText(sdf.format(mShifts.get(position).getDate()));

        if(mShifts.get(position).nightHours > 0 && mShifts.get(position).dayNight == 1) {
            holder.sunMoonImageView.setImageResource(drawable.moon_calendar_tr);
        }

        if(mShifts.get(position).nightHours == 0 && mShifts.get(position).dayNight == 1) {
            holder.sunMoonImageView.setImageResource(drawable.sun_calendar_tr);
        }

        if(mShifts.get(position).overtime != 0 && mShifts.get(position).dayNight == 1)
        holder.overtimeTextView.setText(Double.toString(mShifts.get(position).getOvertime()));
        SimpleDateFormat sd = new SimpleDateFormat("M");
        String date = sd.format(mShifts.get(position).getDate());
        int month = Integer.parseInt(date);

//        holder.setItemLongClickListener(new ItemLongClickListener() {
//            @Override
//            public void onItemLongClick(View view, int position) {
//                Toast.makeText(c, sdf.format(mShifts.get(position).getDate()), Toast.LENGTH_LONG).show();
//
//            }
//        });
//        if(currentMonth != month) {
//            holder.recyclerViewItem.setBackgroundResource(drawable.calendaritem_grey);
//            Log.d("Months", String.valueOf(currentMonth) + "cur " + String.valueOf(month) + "shift");
//        } else {
//            holder.recyclerViewItem.setBackgroundResource(drawable.calendaritem);
//        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mShifts.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView dateTextView;
        ImageView sunMoonImageView;
        TextView overtimeTextView;
        RelativeLayout recyclerViewItem;
        ItemClickListener mClickListener;
//        ItemLongClickListener itemLongClickListener;

        ViewHolder(View itemView) {
            super(itemView);
            recyclerViewItem = itemView.findViewById(R.id.recycler_view_item);
            dateTextView = itemView.findViewById(R.id.date_text);
            sunMoonImageView = itemView.findViewById(R.id.sun_moon_icon_view);
            overtimeTextView = itemView.findViewById(R.id.overtime_text);
//            itemView.setOnLongClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            Log.d("onClick", "clicked");
            Toast.makeText(v.getContext(),"clicked " + getAdapterPosition() , Toast.LENGTH_SHORT).show();
            showPopUpMenu(v);
        }

        private void showPopUpMenu(View view) {
            PopupMenu popupMenu =  new PopupMenu(view.getContext(), view);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.show();
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }

        // convenience method for getting data at click position
        String getItem(int id) {
            return mShifts.get(id).toString();
        }

        // allows clicks events to be caught
        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

//        public void setItemLongClickListener(ItemLongClickListener ic) {
//            this.itemLongClickListener = ic;
//        }

    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

//    public interface ItemLongClickListener {
//        void onItemLongClick(View view, int position);
//    }
}
