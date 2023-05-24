package com.example.librarymanageapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.librarymanageapp.fragment.BookFragment;
import com.example.librarymanageapp.fragment.CategoryFragment;
import com.example.librarymanageapp.fragment.ConnectFragment;
import com.example.librarymanageapp.fragment.HistoryFragment;
import com.example.librarymanageapp.fragment.HomeFragment;
import com.example.librarymanageapp.fragment.ManageFragment;
import com.example.librarymanageapp.fragment.NewsFragment;
import com.example.librarymanageapp.fragment.NotificationFragment;

public class ViewPagerUserAdapter extends FragmentStatePagerAdapter {
    public ViewPagerUserAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new HomeFragment();
            case 1: return new HistoryFragment();
            case 2: return new ConnectFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
