package co.com.indi.rathole.rathole.Utils.SQLiteManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import co.com.indi.rathole.rathole.Utils.Constants;

public class LogDictionaryOpenHelper extends SQLiteOpenHelper {

    private static final String TAG = "LogDictionaryOpenHelper";

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "RatHoleDB.db";

    public LogDictionaryOpenHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(LogEntry.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + LogEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void saveToDB() {
        SQLiteDatabase database = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        try {
            Calendar calendar = Calendar.getInstance();
            long date = calendar.getTimeInMillis();
            values.put(LogEntry.COLUMN_DATE, date);
        }
        catch (Exception e) {
            Log.e(TAG, "SQLite --> Error", e);
            return;
        }
        long newRowId = database.insert(LogEntry.TABLE_NAME, null, values);

        Log.i(TAG, "SQLite --> The new Row Id is " + newRowId);
    }

    public List<String> readFromDBAllLogEntrys() {
        SQLiteDatabase database = this.getReadableDatabase();

        String[] projection = {
                LogEntry._ID,
                LogEntry.COLUMN_DATE
        };

        String selection = LogEntry.COLUMN_DATE;

        String[] selectionArgs = {};

        Cursor cursor = database.query(
                LogEntry.TABLE_NAME,     // The table to query
                projection,                               // The columns to return
                selection,                                // The columns for the WHERE clause
                selectionArgs,                            // The values for the WHERE clause
                null,                                     // don't group the rows
                null,                                     // don't filter by row groups
                null                                      // don't sort
        );

        List logs = new ArrayList<String>();
        while (cursor.moveToNext()){
            long dateLong = cursor.getLong(cursor.getColumnIndexOrThrow(LogEntry.COLUMN_DATE));
            String dateString = new SimpleDateFormat(Constants.DATE_FORMAT).format(new Date(dateLong));
            logs.add(dateString);
            Log.d(TAG, "LogEntry found: " + dateString);
        }
        cursor.close();
        return logs;
    }
}
