package co.com.indi.rathole.rathole.Activities;

import android.app.ActivityManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;

import co.com.indi.rathole.rathole.Adapters.CustomPagerAdapter;
import co.com.indi.rathole.rathole.R;

public class RatHoleMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rat_hole_main);

//        Toolbar toolbar = findViewById(R.id.toolbar_main_activity);
//        setSupportActionBar(toolbar);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager_main_activity);
        CustomPagerAdapter pagerAdapter =
                new CustomPagerAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.tab_layout_main_activity);
        tabLayout.setupWithViewPager(viewPager);

    }
}
