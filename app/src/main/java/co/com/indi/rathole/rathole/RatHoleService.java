package co.com.indi.rathole.rathole;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

public class RatHoleService extends Service {

    private static final String TAG = "RatHoleService";
    private boolean isRunning = false;
    private MediaPlayer player;

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
        //int a =  super.onStartCommand(intent, flags, startId);

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                for (int i = 0; i < 5; i++) {
//                    try{
//                        Thread.sleep(1000);
//                    }catch (Exception e){
//
//                    }
//
//                    if(isRunning){
//                        Log.i(TAG, "Service Thread ok");
//                    }
//                }
//            }
//        }).start();
//
//        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
//        player.setLooping(true);
//        player.start();

        Log.i(TAG, "onStartCommand-->Service Started");
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

    public void startAlarm(){
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        player.setLooping(true);
        player.start();
    }

    public void  stopAlarm(){
        if(player != null){
            player.stop();
        }
    }
}
