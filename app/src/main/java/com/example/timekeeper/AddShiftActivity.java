package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddShiftActivity extends AppCompatActivity {

    private Button addButton;
    private Button deleteButton;
    private DatePicker picker;
    private EditText editTextShiftOvertime, editTextShiftNightHour;
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        picker = (DatePicker) findViewById(R.id.datePicker);
        editTextShiftOvertime = (EditText) findViewById(R.id.editTextShiftOvertime);
        editTextShiftNightHour = (EditText)findViewById(R.id.editTextShiftNightHour);
//        editTextShiftNightHour.setText("0");
        addButton = (Button) findViewById(R.id.buttonAddShift);

        final ShiftDBSQLiteHelper dbHelper = new ShiftDBSQLiteHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();
        Intent intent = getIntent();
        String rcvdDate = null;
        rcvdDate = intent.getStringExtra("date");
//        if(rcvdDate!=null) {
//            Bundle str = getIntent().getBundleExtra("date");
//            String stringDate = str.getString("date");
//            String [] dates = stringDate.split("-");
//            picker.updateDate(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2]));
//        }

        Log.d("date transfer", rcvdDate);

        deleteButton = (Button) findViewById(R.id.buttonDB);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               deleteShift();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShift();
            }
        });
    }

    private void addShift() {

        if(editTextShiftOvertime.getText().toString().trim().length() == 0 || editTextShiftNightHour.getText().toString().trim().length() == 0 ) {
            Toast.makeText(AddShiftActivity.this, "Please fill in all fields", Toast.LENGTH_LONG).show();
            return;
        }
        int monthPick = picker.getMonth() + 1;
        String pickerDate =  picker.getYear() + "-" + monthPick + "-" + picker.getDayOfMonth();
        Log.d("record", pickerDate);

        double overtime = Double.parseDouble(editTextShiftOvertime.getText().toString());
        double nightHour = Double.parseDouble(editTextShiftNightHour.getText().toString());

        ContentValues cv = new ContentValues();
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_DATE, pickerDate);
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_OVERTIME, overtime);
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_NIGHT_HOUR, nightHour);

        sqLiteDatabase.insert(ShiftDBContract.ShiftRecords.TABLE_NAME, null, cv);
    }

    private void deleteShift () {

        int monthPick = picker.getMonth() + 1;
        String pickerDate =  picker.getYear() + "-" + monthPick + "-" + picker.getDayOfMonth();
        String [] dateClause = {pickerDate};

        sqLiteDatabase.delete(ShiftDBContract.ShiftRecords.TABLE_NAME, ShiftDBContract.ShiftRecords.COLUMN_DATE + "=?", dateClause);

    }

}
