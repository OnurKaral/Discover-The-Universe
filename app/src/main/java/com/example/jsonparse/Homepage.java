package com.example.jsonparse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class Homepage extends AppCompatActivity {
    private MaterialCardView materialCardView;
    private MaterialCardView epicbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);



     materialCardView = findViewById(R.id.ASTEROID);
     epicbutton = findViewById(R.id.epicbutton);

        materialCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,ASTEROID.class);
                startActivity(intent);
            }
        });

        epicbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Homepage.this,EPIC.class);
                startActivity(intent);
            }
        });

}
    }