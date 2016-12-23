package xyz.viseator.anonymouscard.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import xyz.viseator.anonymouscard.data.DataPackage;
import xyz.viseator.anonymouscard.ui.MainFragment;

/**
 * Created by viseator on 2016/12/20.
 * Wudi
 * viseator@gmail.com
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ArrayList<DataPackage> dataPackages;

    public ViewPagerAdapter(FragmentManager fm, Context context, ArrayList<DataPackage> dataPackages) {
        super(fm);
        this.context = context;
        this.dataPackages = dataPackages;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                MainFragment mainFragment = new MainFragment();
                mainFragment.setName("主页");
                return mainFragment;
            case 1:
                MainFragment mainFragment1 = new MainFragment();
                mainFragment1.setName("次页");
                return mainFragment1;
            case 2:
                MainFragment mainFragment2 = new MainFragment();
                mainFragment2.setName("三页");
                return mainFragment2;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ((MainFragment) getItem(position)).getName();
    }

    @Override
    public int getCount() {
        return 3;
    }
}
