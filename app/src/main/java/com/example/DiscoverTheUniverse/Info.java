package com.example.DiscoverTheUniverse;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Info extends AppCompatActivity {

    private FloatingActionButton button1;
    private FloatingActionButton button2;
    private FloatingActionButton button3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);


        button1 = findViewById(R.id.bir);
        button2 = findViewById(R.id.iki);
        button3 = findViewById(R.id.uc);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.linkedin.com/in/onur-karal/"));
                startActivity(browserIntent);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/qwertyF0"));
                startActivity(browserIntent);
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://stackoverflow.com/users/11248769/onur-karal"));
                startActivity(browserIntent);
            }
        });

    }
}