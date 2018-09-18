package co.com.indi.rathole.rathole;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class RatHoleIntentService  extends IntentService{

    private final static String TAG = "RatHoleIntentService";

    public RatHoleIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

    }
}
