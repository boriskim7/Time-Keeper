package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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
    private Button dbButton;
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
        addButton = (Button) findViewById(R.id.buttonAddShift);

        final ShiftDBSQLiteHelper dbHelper = new ShiftDBSQLiteHelper(this);
        sqLiteDatabase = dbHelper.getWritableDatabase();

        dbButton = (Button) findViewById(R.id.buttonDB);
        dbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = sqLiteDatabase.query(ShiftDBContract.ShiftRecords.TABLE_NAME, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords._ID);
                    int dateIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_DATE);
                    int ovIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_OVERTIME);
                    int nsIndex = cursor.getColumnIndex(ShiftDBContract.ShiftRecords.COLUMN_NIGHT_HOUR);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", DATE = " + cursor.getString(dateIndex) +
                                ", overtime = " + cursor.getDouble(ovIndex) +
                                ", nightshift = " + cursor.getDouble(nsIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", "0 rows");
                    cursor.close();
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
        String pickerDate =  picker.getYear() + "-" + picker.getMonth() + "-" + picker.getDayOfMonth();
        Log.d("record", pickerDate);

        double overtime = Double.parseDouble(editTextShiftOvertime.getText().toString());
        double nightHour = Double.parseDouble(editTextShiftNightHour.getText().toString());

        ContentValues cv = new ContentValues();
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_DATE, pickerDate);
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_OVERTIME, overtime);
        cv.put(ShiftDBContract.ShiftRecords.COLUMN_NIGHT_HOUR, nightHour);

        sqLiteDatabase.insert(ShiftDBContract.ShiftRecords.TABLE_NAME, null, cv);





    }

}
