package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class AddShiftActivity extends AppCompatActivity {

    private Button addButton;
    private EditText editTextShiftDay, editTExtShiftStartTime, editTextShiftEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_add_shift);
        editTextShiftDay = (EditText) findViewById(R.id.editTextShiftDate);
        editTExtShiftStartTime = (EditText) findViewById(R.id.editTextShiftStartTime);
        editTextShiftEndTime = (EditText)findViewById(R.id.editTextShiftEndTime);
        addButton = (Button) findViewById(R.id.buttonAddShift);
    }

}
