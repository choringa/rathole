package co.com.indi.rathole.rathole.Services;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.com.indi.rathole.rathole.Receivers.PhoneUnlockReceiver;
import co.com.indi.rathole.rathole.R;
import co.com.indi.rathole.rathole.Utils.Constants;
import co.com.indi.rathole.rathole.Utils.SQLiteManager.LogDictionaryOpenHelper;

public class RatHoleService extends Service {

    private static final String TAG = "RatHoleService";
    private boolean isRunning = false;
    private MediaPlayer player;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        Log.i(TAG, "OnCreate-->Service Started");
        isRunning = true;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        //throw new UnsupportedOperationException("Not yet implemented");
        Log.i(TAG, "OnBind-->Service Binded");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.i(TAG, "onStartCommand-->Service Started");
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, MODE_PRIVATE);
        registerReceiver(new PhoneUnlockReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));
        Toast.makeText(this, "Service Started 3:)", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "onDestroy-->Service Stoped");
        Toast.makeText(this, "Service Stoped", Toast.LENGTH_SHORT).show();
        stopAlarm();
    }

    public void manageUnlockResult(){
        addUnlockCounter();
        addUnlockToSqlLiteLog();
        if(sharedPreferences.getBoolean(Constants.ALARM_STATE_KEY, false)){
            startAlarm();
        }
    }

    private void addUnlockToSqlLiteLog() {
        LogDictionaryOpenHelper logDictionaryOpenHelper = new LogDictionaryOpenHelper(this);
        logDictionaryOpenHelper.saveToDB();
    }

    private void addUnlockToLogToCacheFile() {
        File cacheDir = getCacheDir();
        File cacheFile = new File(cacheDir, Constants.CACHE_FILE_NAME);
        try {
            FileOutputStream fos = new FileOutputStream(cacheFile);
            Date today = Calendar.getInstance().getTime();
            //TODO: probar esto del locale
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
            fos.write(simpleDateFormat.format(today).getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAlarm(){
//        AudioManager amanager = (AudioManager) this.getSystemService(Context.AUDIO_SERVICE);
//        int maxVolume = amanager.getStreamMaxVolume(AudioManager.STREAM_ALARM);
//        amanager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0);
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
//        player.setAudioStreamType(AudioManager.STREAM_ALARM);
        //player.setVolume(maxVolume, maxVolume);
        player.start();
    }

    public void stopAlarm(){
        if(player != null){
            player.stop();
        }
    }

    public void addUnlockCounter(){
        SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();
        int currentUnlockCounter = sharedPreferences.getInt(Constants.UNLOCK_COUNTER_KEY, 0);
        sharedPreferencesEditor.putInt(Constants.UNLOCK_COUNTER_KEY, currentUnlockCounter+1).apply();
    }
}
