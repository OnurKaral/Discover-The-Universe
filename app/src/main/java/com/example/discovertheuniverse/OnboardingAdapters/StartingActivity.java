package com.example.discovertheuniverse.OnboardingAdapters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.discovertheuniverse.LoginActivity;
import com.example.discovertheuniverse.MainActivity;
import com.example.discovertheuniverse.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {

    private ViewPager screenPager;
    private ViewPagerAdapter viewPagerAdapter;
    private TabLayout tabIndicator;
    private MaterialButton button_next;
    private MaterialButton button_start;

    int position =0;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SharedPref
        if (restorePrefData()) {
            Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(mainActivity);
            finish();
        }
        setContentView(R.layout.activity_starting);

        button_next = findViewById(R.id.btn_next);
        button_start = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);

        //List
        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("Title", "description here...", R.drawable.nasaround));
        mList.add(new ScreenItem("Title", "description here...", R.raw.nasa123));

        screenPager = findViewById(R.id.screen_viewpager);
        viewPagerAdapter = new ViewPagerAdapter(this, mList);
        screenPager.setAdapter(viewPagerAdapter);
        tvSkip = findViewById(R.id.tv_skip);
        tabIndicator.setupWithViewPager(screenPager);

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                position= screenPager.getCurrentItem();
                if (position< mList.size()){

                    position++;
                    screenPager.setCurrentItem(position);

                }
                if (position== mList.size()-1){
                    loadLastScreen();

                }
            }
        });

        //noinspection deprecation
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1){
                    loadLastScreen();

                }

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                loadfirstScreen();
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        //GetStarted
        button_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                savePrefsData();
                finish();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(StartingActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
    private boolean restorePrefData() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpenedBefore = pref.getBoolean("isIntroOpened",false);
        return  isIntroActivityOpenedBefore;
    }

    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened", true);
        editor.commit();

    }

    private void loadLastScreen() {
        button_next.setVisibility(View.INVISIBLE);
        button_start.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
    }

    private void loadfirstScreen() {
        button_next.setVisibility(View.VISIBLE);
        button_start.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.VISIBLE);
    }
}
