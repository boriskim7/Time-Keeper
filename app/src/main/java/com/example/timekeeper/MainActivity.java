package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private Button previousMonth;
    private TextView monthName;
    private String monthNameInHeader;
    private Button nextMonth;
    private Button btnCalc;
    private Button btnAddShift;
    MyRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        monthName = (TextView) findViewById(R.id.monthName);
        monthNameInHeader = Calendar.getInstance().getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        monthName.setText(monthNameInHeader);
        btnCalc = (Button) findViewById(R.id.buttonCalc);
        btnAddShift = (Button) findViewById(R.id.buttonAddDay);

        btnCalc.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View v) {
                    Toast.makeText(MainActivity.this, "Calc opened", Toast.LENGTH_SHORT).show();
                    openCalc();
            }
        });

        btnAddShift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDay();
            }
        });
        String[] data = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16",
                "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32",
                "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48"};

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.calendarView);
        int numberOfColumns = 7;
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns));
        adapter = new MyRecyclerViewAdapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }
    public void openCalc() {
        Intent intent = new Intent(this, calcActivity.class);
        startActivity(intent);
    }

    public void AddDay() {
        Intent intent = new Intent(this, AddShiftActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(View view, int position) {
        Log.i("TAG", "You clicked number " + adapter.getItem(position) + ", which is at cell position " + position);
    }
}
