package com.example.jsonparse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView imagetitle;
    private RequestQueue requestQueue;
    private ImageView imageView;
    private ImageView imageView2;
    private MaterialButton homepagebutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    imagetitle = findViewById(R.id.image_name);
    imageView = findViewById(R.id.image_view);
    homepagebutton = findViewById(R.id.button1);

        imagetitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,APODinfo.class);
                startActivity(intent);
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,APODinfo.class);
                startActivity(intent);
            }
        });
    requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
        String url= "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("title");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");

                    imagetitle.setText(imagename);
                    Picasso.get().load(image_url).fit().centerInside().into(imageView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}






