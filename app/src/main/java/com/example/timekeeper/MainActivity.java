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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private Button previousMonth;
    private TextView monthName;
    private String monthNameInHeader;
    private Button nextMonth;
    private Button btnCalc;
    private Button btnAddShift;
    RecyclerView recyclerView;
    MyRecyclerViewAdapter adapter;

    // how many days to show, defaults to six weeks, 42 days
    // default date format

    // date format
    private String dateFormat;
    private static final int DAYS_COUNT = 42;
    private Calendar currentDate = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Calendar calendar = (Calendar) currentDate.clone();

        monthName = (TextView) findViewById(R.id.monthName);
        monthNameInHeader = calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
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



        int numberOfColumns = 7;
        ArrayList<Date> cells = new ArrayList<>();
        ArrayList<Shift> days = new ArrayList<Shift>();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        // move calendar backwards to the beginning of the week
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            days.add(new Shift(cells.get(cells.size()-1), 1, 2, 6.5));
        }

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.calendarView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerViewAdapter(this, days);
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
