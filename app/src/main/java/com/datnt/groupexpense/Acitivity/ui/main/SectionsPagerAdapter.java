package com.datnt.groupexpense.Acitivity.ui.main;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.datnt.groupexpense.Fragment.AddNewFragment;
import com.datnt.groupexpense.Fragment.ByDayFragment;
import com.datnt.groupexpense.Fragment.ByMonthFragment;
import com.datnt.groupexpense.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private static final String[] TAB_TITLES = new String[]{"Thêm mới","Theo ngày","Theo Tháng"};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                AddNewFragment addNewFragment = new AddNewFragment();
                return addNewFragment;
            case 1:
                ByDayFragment byDayFragment = new ByDayFragment();
                return byDayFragment;
            case 2:
                ByMonthFragment byMonthFragment = new ByMonthFragment();
                return byMonthFragment;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES[position];
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}