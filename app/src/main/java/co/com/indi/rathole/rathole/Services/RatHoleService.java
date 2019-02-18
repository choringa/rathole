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

import co.com.indi.rathole.rathole.Receivers.PhoneUnlockReceiver;
import co.com.indi.rathole.rathole.R;

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
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE);
        registerReceiver(new PhoneUnlockReceiver(), new IntentFilter("android.intent.action.USER_PRESENT"));
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        Log.i(TAG, "onDestroy-->Service Stoped");
        stopAlarm();
    }

    public void manageUnlockResult(){
        addUnlockCounter();
        if(sharedPreferences.getBoolean(getString(R.string.alarm_state_key), false)){
            startAlarm();
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
        int currentUnlockCounter = sharedPreferences.getInt(getString(R.string.unlock_counter_key), 0);
        sharedPreferencesEditor.putInt(getString(R.string.unlock_counter_key), currentUnlockCounter+1).apply();
    }
}
