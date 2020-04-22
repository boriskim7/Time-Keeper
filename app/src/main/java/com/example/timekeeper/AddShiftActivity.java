package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddShiftActivity extends AppCompatActivity {

    private Button addButton;
    private EditText editTextShiftDay, editTextShiftOvertime, editTextShiftNightHour;
    SQLiteDatabase sqldb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        ShiftDBSQLiteHelper dbHelper = new ShiftDBSQLiteHelper(this);
        sqldb = dbHelper.getWritableDatabase();

        editTextShiftDay = (EditText) findViewById(R.id.editTextShiftDate);
        editTextShiftOvertime = (EditText) findViewById(R.id.editTextShiftOvertime);
        editTextShiftNightHour = (EditText)findViewById(R.id.editTextShiftNightHour);
        addButton = (Button) findViewById(R.id.buttonAddShift);

    }

    private void addShift() throws ParseException {
        if(editTextShiftDay.getText().toString().trim().length() == 0 || editTextShiftOvertime.getText().toString().trim().length() == 0 ||
        editTextShiftNightHour.getText().toString().trim().length() == 0 ) {
            return;
        }
        Date evShiftDate = new SimpleDateFormat("dd/MM/yyyy").parse(editTextShiftDay.getText().toString());
        double overtime = Double.parseDouble(editTextShiftOvertime.getText().toString());
        double nightHour = Double.parseDouble(editTextShiftNightHour.getText().toString());

    }

}
