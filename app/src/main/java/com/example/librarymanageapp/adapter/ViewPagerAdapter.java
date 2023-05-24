package com.example.librarymanageapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.librarymanageapp.fragment.BookFragment;
import com.example.librarymanageapp.fragment.CategoryFragment;
import com.example.librarymanageapp.fragment.ManageFragment;
import com.example.librarymanageapp.fragment.NewsFragment;
import com.example.librarymanageapp.fragment.NotificationFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new CategoryFragment();
            case 1: return new BookFragment();
            case 2: return new NewsFragment();
            case 3: return new NotificationFragment();
            case 4: return new ManageFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 5;
    }
}
