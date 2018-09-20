package co.com.indi.rathole.rathole;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class RatHoleMainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private Button serviceStartBtn, serviceStopBtn, restartCounterBtn;
    private Switch emailSwitch, alarmSwitch, pictureSwitch;
    private TextView unlockCounterTextView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private LottieAnimationView switchLottieAlarmAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_hole_main);
        sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_key), MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        initViews();
        initPreferences();
    }

    private void initViews() {
        serviceStartBtn = findViewById(R.id.main_activity_service_btn_start);
        serviceStopBtn = findViewById(R.id.main_activity_service_btn_stop);
        restartCounterBtn = findViewById(R.id.main_activity_restart_btn);
        unlockCounterTextView = findViewById(R.id.main_activity_counter_tv);
        emailSwitch = findViewById(R.id.main_activity_mail_switch);
        alarmSwitch = findViewById(R.id.main_activity_alarm_switch);
        pictureSwitch = findViewById(R.id.main_activity_picture_switch);
        switchLottieAlarmAnimator = findViewById(R.id.main_activity_lottie_alarm_switch);

        serviceStartBtn.setOnClickListener(this);
        serviceStopBtn.setOnClickListener(this);
        restartCounterBtn.setOnClickListener(this);
        emailSwitch.setOnCheckedChangeListener(this);
        alarmSwitch.setOnCheckedChangeListener(this);
        pictureSwitch.setOnCheckedChangeListener(this);
    }


    private void initPreferences() {
        int unlockCounter = sharedPreferences.getInt(getString(R.string.unlock_counter_key), 0);
        unlockCounterTextView.setText(unlockCounter + "");

        boolean emailState = sharedPreferences.getBoolean(getString(R.string.email_state_key), false);
        emailSwitch.setChecked(emailState);

        boolean alarmState = sharedPreferences.getBoolean(getString(R.string.alarm_state_key), false);
        alarmSwitch.setChecked(alarmState);

        boolean pictureState = sharedPreferences.getBoolean(getString(R.string.picture_state_key), false);
        pictureSwitch.setChecked(pictureState);
    }


    @Override
    public void onClick(View view) {
        if (view == serviceStartBtn) {
            startService(new Intent(this, RatHoleService.class));
        } else if (view == serviceStopBtn) {
            stopService(new Intent(this, RatHoleService.class));
        } else if (view == restartCounterBtn) {
            showConfirmationDialog();
        }
    }

    private void showConfirmationDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                switch (i) {
                    case DialogInterface.BUTTON_POSITIVE:
                        resetUnlockCounter();
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you fucking sure?").setPositiveButton("Fuck Yeah", dialogClickListener)
                .setNegativeButton("Fuck No", dialogClickListener).show();
    }


    private void resetUnlockCounter() {
        sharedPreferencesEditor.putInt(getString(R.string.unlock_counter_key), 0);
        sharedPreferencesEditor.commit();
        unlockCounterTextView.setText("0");
    }

    private void startCheckAnimation() {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 0.4f).setDuration(600);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                switchLottieAlarmAnimator.setProgress((Float) valueAnimator.getAnimatedValue());
            }
        });

        if (switchLottieAlarmAnimator.getProgress() == 0f) {
            animator.start();
        } else {
            animator.start();
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.equals(emailSwitch)) {
            sharedPreferencesEditor.putBoolean(getString(R.string.email_state_key), b);
            sharedPreferencesEditor.commit();
        } else if (compoundButton.equals(alarmSwitch)) {
            sharedPreferencesEditor.putBoolean(getString(R.string.alarm_state_key), b);
            sharedPreferencesEditor.commit();
            startCheckAnimation();
        } else if (compoundButton.equals(pictureSwitch)) {
            sharedPreferencesEditor.putBoolean(getString(R.string.picture_state_key), b);
            sharedPreferencesEditor.commit();
        }
    }
}
