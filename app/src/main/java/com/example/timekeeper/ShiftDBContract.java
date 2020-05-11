package com.example.timekeeper;

import android.provider.BaseColumns;

public class ShiftDBContract {
    private ShiftDBContract() {
    }

    public static class ShiftRecords implements BaseColumns {
        public static final String TABLE_NAME = "shiftlist";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_OVERTIME = "overtime";
        public static final String COLUMN_NIGHT_HOUR = "nighthour";

        public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                COLUMN_DATE + " TEXT PRIMARY KEY, " +
                COLUMN_OVERTIME + " REAL, " +
                COLUMN_NIGHT_HOUR + " REAL" + ")";

    }
}
