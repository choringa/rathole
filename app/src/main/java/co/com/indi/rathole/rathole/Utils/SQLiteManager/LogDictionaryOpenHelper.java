package co.com.indi.rathole.rathole.Utils.SQLiteManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;

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
}
