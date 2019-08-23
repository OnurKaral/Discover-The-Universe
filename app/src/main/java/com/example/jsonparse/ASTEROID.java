package com.example.jsonparse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ASTEROID extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem enyakin;
    private TabItem tüm;
    private PagerAdapter pagerAdapter;
    private RequestQueue requestQueue;
    private TextView textView1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asteroid);

        textView1 = findViewById(R.id.yakinfragmenttext);


        tabLayout = findViewById(R.id.tablayout1);
        viewPager = findViewById(R.id.viewpager1);
        enyakin = findViewById(R.id.enyakinlar);
        tüm = findViewById(R.id.tümü);

        Adapter pageAdapter = new Adapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        requestQueue = Volley.newRequestQueue(this);
        jsonParse();
    }

    private void jsonParse() {
        String url= "https://api.nasa.gov/neo/rest/v1/feed?api_key=DEMO_KEY";
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("near_earth_objects");
                    JSONArray imageinfo1 = response.getJSONArray("near_earth_objects");
                    imageinfo1.getString(Integer.parseInt("near_earth_objects"));

                    textView1.setText((CharSequence) imageinfo1);

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
