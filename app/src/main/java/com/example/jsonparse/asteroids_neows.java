package com.example.jsonparse;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class asteroids_neows extends AppCompatActivity {
    private RequestQueue requestQueue;
    private String nearobjects;
    private TextView mAsteroidstw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroids_neows);
        mAsteroidstw = findViewById(R.id.asteroidstw);
        requestQueue = Volley.newRequestQueue(this);
        String url = "https://api.nasa.gov/neo/rest/v1/feed?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.getString("element_count");
                    nearobjects = response.getString("element_count");
                    mAsteroidstw.setText(nearobjects);

                    //RESERVE
                    System.out.println(nearobjects);

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
