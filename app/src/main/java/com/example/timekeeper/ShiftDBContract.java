package com.example.timekeeper;

import android.provider.BaseColumns;

public class ShiftDBContract {
    private ShiftDBContract() {
    }

    public static final class ShiftRecords implements BaseColumns {
        public static final String TABLE_NAME = "shiftList";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_OVERTIME = "overtime";
        public static final String COLUMN_NIGHTHOUR = "nightHour";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS" + TABLE_NAME + " (" +
                _ID + " INTEGER PRIMAR KEY AUTOINCREMENT, " +
                COLUMN_DATE + "TEXT, " +
                COLUMN_OVERTIME + " REAL, " +
                COLUMN_NIGHTHOUR + " REAL" + ")";

    }
}
