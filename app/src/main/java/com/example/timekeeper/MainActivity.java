package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {
    private ImageButton previousMonth;
    private TextView monthName;
    private TextView totalView;
    private TextView totalOvertimeView;
    private TextView totalNightShiftView;
    private String monthNameInHeader;
    private ImageButton nextMonth;
    private Button btnCalc;
    private Button btnAddShift;
    private RecyclerView recyclerView;
    private MyRecyclerViewAdapter adapter;
    private SQLiteDatabase sqLiteDatabase;
    Context c;



    // how many days to show, defaults to six weeks, 42 days

    private String dateFormat;
    private static final int DAYS_COUNT = 42;
    public static Calendar currentDate = Calendar.getInstance();
    private double total;
    private double totalOvertime;
    private double totalNightShifts;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        previousMonth = (ImageButton) findViewById(R.id.buttonPrev);
        nextMonth = (ImageButton) findViewById(R.id.buttonNext);
        monthName = (TextView) findViewById(R.id.monthName);

        btnCalc = (Button) findViewById(R.id.buttonCalc);
        btnAddShift = (Button) findViewById(R.id.buttonAddDay);
        totalView = (TextView) findViewById(R.id.total_amount);
        totalOvertimeView = (TextView) findViewById(R.id.total_overtime_amount);
        totalNightShiftView = (TextView) findViewById(R.id.total_nighthours_amount);

        previousMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });
        nextMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH,1);
                updateCalendar();
            }
        });

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
        updateCalendar();
    }

    @Override
    protected void onResume() {
        updateCalendar();
        super.onResume();
    }

    public void updateCalendar() {
        final ShiftDBSQLiteHelper dbHelper = new ShiftDBSQLiteHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-M-d");
        monthNameInHeader = currentDate.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.getDefault());
        monthName.setText(monthNameInHeader);
        int numberOfColumns = 7;
        ArrayList<Date> cells = new ArrayList<>();
        ArrayList<Shift> shifts = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        // determine the cell for current month's beginning
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 2;

        // move calendar backwards to the beginning of the weekdebu
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        // fill cells
        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());

            calendar.add(Calendar.DAY_OF_MONTH, 1);
            String [] columns = {
                    ShiftDBContract.ShiftRecords.COLUMN_DATE,
                    ShiftDBContract.ShiftRecords.COLUMN_OVERTIME,
                    ShiftDBContract.ShiftRecords.COLUMN_NIGHT_HOUR};

            String selection = ShiftDBContract.ShiftRecords.COLUMN_DATE + " =?";
            String [] selectionArgs = {sdf.format(cells.get(cells.size()-1))};
            Cursor cursor = sqLiteDatabase.query(
                    ShiftDBContract.ShiftRecords.TABLE_NAME,
                    null,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null);

                if (cursor.moveToFirst()) {
                    int dateIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_DATE);
                    int ovIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_OVERTIME);
                    int nsIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_NIGHT_HOUR);
                        Date date = cells.get(cells.size() - 1);
                        Double ov = cursor.getDouble(ovIndex);
                        Double ns = cursor.getDouble(nsIndex);
                        shifts.add(new Shift(date, 1, ov, ns));

                } else {
                    shifts.add(new Shift(cells.get(cells.size()-1),0,0,0));
                }
                cursor.close();

        }
            total = 0;
            totalOvertime = 0;
            totalNightShifts = 0;

        for(Shift shift:shifts) {
            if(shift.dayNight == 1) total = total + 8;
            if(shift.overtime != 0 ) totalOvertime = totalOvertime + shift.getOvertime();
            if(shift.nightHours != 0) totalNightShifts = totalNightShifts + shift.getNightHours();

        }

        totalView.setText(String.valueOf(total));
        totalOvertimeView.setText(String.valueOf(totalOvertime));
        totalNightShiftView.setText(String.valueOf(totalNightShifts));



        // set up the RecyclerView
        recyclerView = findViewById(R.id.calendarView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns, GridLayoutManager.VERTICAL, false));
        adapter = new MyRecyclerViewAdapter(this, shifts);
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

        Toast.makeText(c, "Clicked", Toast.LENGTH_LONG).show();
    }


}
