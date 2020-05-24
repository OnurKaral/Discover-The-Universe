package com.example.jsonparse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StartingActivity extends AppCompatActivity {

    private ViewPager screenPager;
    ViewPagerAdapter intropageradapter;
    TabLayout tabIndicator;
    Button button;
    MaterialButton button2;
    int position =0;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (restorePrefData()) {

            Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class );
            startActivity(mainActivity);
            finish();


        }


        setContentView(R.layout.activity_starting);


        button =findViewById(R.id.btn_next);
        button2=findViewById(R.id.btn_get_started);
        tabIndicator=findViewById(R.id.tab_indicator);

        final List<ScreenItem> mList = new ArrayList<>();
        mList.add(new ScreenItem("First","TEST",R.drawable.img1));
        mList.add(new ScreenItem("Two","TEST",R.drawable.img2));
        mList.add(new ScreenItem("Three","TEST",R.drawable.img3));



        screenPager =findViewById(R.id.screen_viewpager);
        intropageradapter = new ViewPagerAdapter(this,mList);
        screenPager.setAdapter(intropageradapter);
        tvSkip = findViewById(R.id.tv_skip);
        tabIndicator.setupWithViewPager(screenPager);
        button.setOnClickListener(new View.OnClickListener() {
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

        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==mList.size()-1){
                    loadLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                savePrefsData();
                finish();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });

    }
    private boolean restorePrefData() {


        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpened",false);
        return  isIntroActivityOpnendBefore;



    }
    private void savePrefsData() {

        SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean("isIntroOpened",true);
        editor.commit();


    }
    private void loadLastScreen() {
        button.setVisibility(View.INVISIBLE);
        button2.setVisibility(View.VISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
    }
}
