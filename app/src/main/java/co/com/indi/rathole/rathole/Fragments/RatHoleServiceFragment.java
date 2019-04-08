package co.com.indi.rathole.rathole.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import co.com.indi.rathole.rathole.R;
import co.com.indi.rathole.rathole.Services.RatHoleService;
import co.com.indi.rathole.rathole.Utils.Constants;
import co.com.indi.rathole.rathole.Utils.UtilMethods;

public class RatHoleServiceFragment extends Fragment implements View.OnClickListener {

    private Button serviceStartBtn, serviceStopBtn, restartCounterBtn;
    private TextView unlockCounterTextView;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedPreferencesEditor;
    private boolean isServiceRunning;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rat_hole_service, container, false);
        isServiceRunning = UtilMethods.isServiceRunning(RatHoleService.class, getContext());
        initViews(view);
        initPreferences();
        return  view;
    }

    private void initViews(View view) {
        serviceStartBtn = view.findViewById(R.id.main_activity_service_btn_start);
        serviceStopBtn = view.findViewById(R.id.main_activity_service_btn_stop);
        restartCounterBtn = view.findViewById(R.id.main_activity_restart_btn);
        unlockCounterTextView = view.findViewById(R.id.main_activity_counter_tv);

        toogleServiceVisibility(isServiceRunning);

        serviceStartBtn.setOnClickListener(this);
        serviceStopBtn.setOnClickListener(this);
        restartCounterBtn.setOnClickListener(this);
    }


    private void initPreferences() {
        sharedPreferences = getActivity().getSharedPreferences(Constants.SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        sharedPreferencesEditor = sharedPreferences.edit();
        int unlockCounter = sharedPreferences.getInt(Constants.UNLOCK_COUNTER_KEY, 0);
        unlockCounterTextView.setText(unlockCounter + "");
    }

    private void toogleServiceVisibility(boolean isServiceRunning){
        if(isServiceRunning){
            serviceStartBtn.setVisibility(View.GONE);
            serviceStopBtn.setVisibility(View.VISIBLE);
        }
        else{
            serviceStartBtn.setVisibility(View.VISIBLE);
            serviceStopBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        if (view == serviceStartBtn) {
            getContext().startService(new Intent(getContext(), RatHoleService.class));
            toogleServiceVisibility(true);
        } else if (view == serviceStopBtn) {
            getContext().stopService(new Intent(getContext(), RatHoleService.class));
            toogleServiceVisibility(false);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you fucking sure?").setPositiveButton("Fuck Yeah", dialogClickListener)
                .setNegativeButton("Fuck No", dialogClickListener).show();
    }


    private void resetUnlockCounter() {
        sharedPreferencesEditor.putInt(Constants.UNLOCK_COUNTER_KEY, 0);
        sharedPreferencesEditor.commit();
        unlockCounterTextView.setText("0");
    }

}
