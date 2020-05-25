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

public class APODinfo extends AppCompatActivity {
    private RequestQueue requestQueue;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apodinfo);

        textView = findViewById(R.id.text1);
        requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }
    private void jsonParse() {
        String url= "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("explanation");
                    String imageinfo = response.getString("explanation");

                    textView.setText(imageinfo);

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
