package co.com.indi.rathole.rathole.Utils.SQLiteManager;

import android.provider.BaseColumns;

public class LogEntry implements BaseColumns {
    public static final String TABLE_NAME = "log";
    public static final String COLUMN_DATE = "date";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_DATE + " INTEGER)";
}
