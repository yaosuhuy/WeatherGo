package com.example.weathergo.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.weathergo.Fragments.DetailFragment;
import com.example.weathergo.Fragments.HomeFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new HomeFragment();
            case 1:
                return new DetailFragment();
            default: return new HomeFragment();
        }
    }

    // tablayout có bao nhiêu cái thì return chừng đó
    @Override
    public int getItemCount() {
        return 3;
    }
}
