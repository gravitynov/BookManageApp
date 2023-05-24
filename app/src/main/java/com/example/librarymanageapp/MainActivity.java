package com.example.librarymanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.librarymanageapp.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }

    private void initview() {
        viewPager=findViewById(R.id.activity_main_viewPager);
        bottomNavigationView=findViewById(R.id.activity_main_bottomNavigation);

        ViewPagerAdapter viewPagerAdapter=new ViewPagerAdapter(getSupportFragmentManager(),
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:bottomNavigationView.getMenu().findItem(R.id.mCategory).setChecked(true);
                        break;
                    case 1:bottomNavigationView.getMenu().findItem(R.id.mBook).setChecked(true);
                        break;
                    case 2:bottomNavigationView.getMenu().findItem(R.id.mNews).setChecked(true);
                        break;
                    case 3:bottomNavigationView.getMenu().findItem(R.id.mNoti).setChecked(true);
                        break;
                    case 4:bottomNavigationView.getMenu().findItem(R.id.mManager).setChecked(true);
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
                    case R.id.mCategory:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.mBook:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.mNews:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.mNoti:
                        viewPager.setCurrentItem(3);
                        break;
                    case R.id.mManager:
                        viewPager.setCurrentItem(4);
                        break;
                }
                return true;
            }
        });
    }
}