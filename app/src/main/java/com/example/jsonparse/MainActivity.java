package com.example.jsonparse;

import androidx.appcompat.app.AppCompatActivity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private TextView imagetitle;
    private RequestQueue requestQueue;
    private VideoView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    imagetitle = findViewById(R.id.image_name);
    imageView = findViewById(R.id.image_view);

    requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
        String url= "https://api.nasa.gov/planetary/apod?api_key=DEMO_KEY";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("title");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");

                    imageView.setVideoURI(Uri.parse(image_url));
                    imagetitle.setText(imagename);

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






