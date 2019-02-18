package co.com.indi.rathole.rathole.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import co.com.indi.rathole.rathole.Fragments.LogFragment;
import co.com.indi.rathole.rathole.Fragments.OptionsFragment;
import co.com.indi.rathole.rathole.Fragments.RatHoleServiceFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private static final String TAB_OPTIONS_TITLE = "Options";
    private static final String TAB_LOG_TITLE = "Log";
    private static final String TAB_SERVICE_TITLE = "Service";

    public CustomPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                RatHoleServiceFragment ratHoleServiceFragment = new RatHoleServiceFragment();
                return ratHoleServiceFragment;
            case 1:
                LogFragment logFragment = new LogFragment();
                return logFragment;
            case 2:
                OptionsFragment optionsFragment = new OptionsFragment();
                return optionsFragment;

            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return TAB_SERVICE_TITLE;
            case 1:
                return TAB_LOG_TITLE;
            case 2:
                return TAB_OPTIONS_TITLE;
            default:
                return "Error";
        }
    }
}
