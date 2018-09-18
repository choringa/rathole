package co.com.indi.rathole.rathole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class RatHoleMainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button counterStart, counterStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_hole_main);

        counterStart = findViewById(R.id.main_activity_counter_btn_start);
        counterStop = findViewById(R.id.main_activity_counter_btn_stop);

        counterStart.setOnClickListener(this);
        counterStop.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if(view == counterStart){
            startService(new Intent(this, RatHoleService.class));
        }
        else if(view == counterStop){
            stopService(new Intent(this, RatHoleService.class));
        }
    }
}
