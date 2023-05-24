package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.librarymanageapp.adapter.ViewPagerAdapter;
import com.example.librarymanageapp.adapter.ViewPagerUserAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UserActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        initview();
    }

    private void initview() {
        viewPager=findViewById(R.id.activity_user_viewPager);
        bottomNavigationView=findViewById(R.id.activity_user_bottomNavigation);

        ViewPagerUserAdapter viewPagerAdapter=new ViewPagerUserAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:bottomNavigationView.getMenu().findItem(R.id.mHome).setChecked(true);
                        break;
                    case 1:bottomNavigationView.getMenu().findItem(R.id.mHistory).setChecked(true);
                        break;
                    case 2:bottomNavigationView.getMenu().findItem(R.id.mConnect).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.mHome:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mHistory:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mConnect:
                        viewPager.setCurrentItem(2);
                        break;
                }
                return true;
            }
        });
    }
}