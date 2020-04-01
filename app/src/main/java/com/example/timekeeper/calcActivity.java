package com.example.timekeeper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class calcActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
    }
    public void onCalcButtonClick (View v) {
        EditText hourStake = (EditText)findViewById(R.id.hourStake);
        EditText overTimeHours = (EditText)findViewById(R.id.overtimeHours);
        EditText holidaysHours = (EditText)findViewById(R.id.holidaysHours);
        EditText nightShiftHours = (EditText)findViewById(R.id.nightShiftsHours);
        EditText holidaysOverTimeHours = (EditText)findViewById(R.id.holidaysOverTimeHours);
        EditText monthCalc = (EditText)findViewById(R.id.monthCalc);
        EditText yearCalc = (EditText)findViewById(R.id.yearCalc);
        TextView result = (TextView)findViewById(R.id.result);
        TextView workHoursAmount = (TextView)findViewById(R.id.workHours);


        double stake = Double.parseDouble(hourStake.getText().toString());
        double overtime = Double.parseDouble(overTimeHours.getText().toString());
        double holidays = Double.parseDouble(holidaysHours.getText().toString());
        double nightshifts = Double.parseDouble(nightShiftHours.getText().toString());
        double holidaysovertime = Double.parseDouble(holidaysOverTimeHours.getText().toString());
        Integer month = Integer.parseInt(monthCalc.getText().toString());
        Integer year = Integer.parseInt(yearCalc.getText().toString());


        Calendar cal = new GregorianCalendar();
        cal.set((Integer)year, month, 1);
        int daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int workDays = daysInMonth;
        for(int i = 1; i<=daysInMonth; i++) {
            cal.set(year, month,i);
            if(cal.get(Calendar.DAY_OF_WEEK) == 6) {
                workDays--;
            }
        }

        double workDaysD = workDays;
        double gibon = workDaysD * stake * 8;
        double overtimeBonus = overtime * stake * 1.5;
        double holidaysBonus = holidays * stake * 1.5;
        double nightShiftsBonus = nightshifts * stake * 0.5;
        double holidaysovertimeBonus = holidaysovertime * stake *2;

        double salary = gibon + overtimeBonus + holidaysBonus + nightShiftsBonus + holidaysovertimeBonus;

        result.setText(Double.toString(salary));
        workHoursAmount.setText(Integer.toString(workDays));

    }
}
