package co.com.indi.rathole.rathole.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import co.com.indi.rathole.rathole.R;
import co.com.indi.rathole.rathole.Utils.Constants;

public class OptionsFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private Switch emailSwitch, alarmSwitch, pictureSwitch;
    private TextView unlockCounterTextView;
    private SharedPreferences.Editor sharedPreferencesEditor;

    public OptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_options, container, false);
        initViews(view);
        initPreferences();
        return view;
    }

    private void initPreferences() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();

        int unlockCounter = sharedPreferences.getInt(Constants.UNLOCK_COUNTER_KEY, 0);
        unlockCounterTextView.setText(unlockCounter + "");

        boolean emailState = sharedPreferences.getBoolean(Constants.EMAIL_STATE_KEY, false);
        emailSwitch.setChecked(emailState);

        boolean alarmState = sharedPreferences.getBoolean(Constants.ALARM_STATE_KEY, false);
        alarmSwitch.setChecked(alarmState);

        boolean pictureState = sharedPreferences.getBoolean(Constants.PICTURE_STATE_KEY, false);
        pictureSwitch.setChecked(pictureState);
    }

    private void initViews(View view) {

        unlockCounterTextView = view.findViewById(R.id.options_fragment_counter_tv);
        emailSwitch = view.findViewById(R.id.options_fragment_mail_switch);
        alarmSwitch = view.findViewById(R.id.options_fragment_alarm_switch);
        pictureSwitch = view.findViewById(R.id.options_fragment_picture_switch);

        emailSwitch.setOnCheckedChangeListener(this);
        alarmSwitch.setOnCheckedChangeListener(this);
        pictureSwitch.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.equals(emailSwitch)) {
            sharedPreferencesEditor.putBoolean(Constants.EMAIL_STATE_KEY, b);
            sharedPreferencesEditor.commit();
        } else if (compoundButton.equals(alarmSwitch)) {
            sharedPreferencesEditor.putBoolean(Constants.ALARM_STATE_KEY, b);
            sharedPreferencesEditor.commit();
        } else if (compoundButton.equals(pictureSwitch)) {
            sharedPreferencesEditor.putBoolean(Constants.PICTURE_STATE_KEY, b);
            sharedPreferencesEditor.commit();
        }
    }
}
