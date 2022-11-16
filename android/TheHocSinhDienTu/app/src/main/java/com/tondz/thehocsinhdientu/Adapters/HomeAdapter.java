package com.tondz.thehocsinhdientu.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.tondz.thehocsinhdientu.Fragments.CalendarFragment;
import com.tondz.thehocsinhdientu.Fragments.HomeFragment;
import com.tondz.thehocsinhdientu.Fragments.NotiFragment;
import com.tondz.thehocsinhdientu.Fragments.OtherFragment;

public class HomeAdapter extends FragmentStatePagerAdapter {
    public HomeAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new CalendarFragment();
            case 2:
                return new NotiFragment();
            case 3:
                return new OtherFragment();
            default:
                return new HomeFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
