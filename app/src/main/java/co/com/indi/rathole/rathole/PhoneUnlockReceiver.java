package co.com.indi.rathole.rathole;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class PhoneUnlockReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneUnlockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            Log.i(TAG, "onReceive-->1: Context: " + context.toString() + "; Intent: " + intent.toString());
            RatHoleService service = (RatHoleService) context;
            service.startAlarm();
        }
        else{
            Log.i(TAG, "onReceive-->2");
        }
    }
}
