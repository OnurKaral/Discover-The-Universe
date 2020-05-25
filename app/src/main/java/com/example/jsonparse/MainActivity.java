package com.example.jsonparse;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView imagetitle;
    private RequestQueue requestQueue;
    private ImageView imageView;
    private TextView imageinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    imagetitle = findViewById(R.id.titletv);
    imageView = findViewById(R.id.image_view);
    imageinfo = findViewById(R.id.infotv);
    requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
      String url= "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";
     //   String url= "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3&date=2020-05-15";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("title");
                    response.getString("explanation");

                    String image_info = response.getString("explanation");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");

                    imageinfo.setText(image_info);
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






