package co.com.indi.rathole.rathole.Receivers;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import co.com.indi.rathole.rathole.Services.RatHoleService;

public class PhoneUnlockReceiver extends BroadcastReceiver {

    private static final String TAG = "PhoneUnlockReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        KeyguardManager keyguardManager = (KeyguardManager)context.getSystemService(Context.KEYGUARD_SERVICE);
        if (keyguardManager.isKeyguardSecure()) {
            Log.i(TAG, "onReceive-->1: Context: " + context.toString() + "; Intent: " + intent.toString());
            RatHoleService service = (RatHoleService) context;
            service.manageUnlockResult();
        }
        else{
            Log.i(TAG, "onReceive-->2");
        }
    }
}
