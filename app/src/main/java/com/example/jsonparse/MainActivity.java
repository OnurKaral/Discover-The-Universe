package com.example.jsonparse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView imagetitle;
    private RequestQueue requestQueue;
    private ImageView imageView;
    private TextView imageinfo;
    private TextView imagedate;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;
    private MaterialButton videobutton;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String urldate;
    private String URL;
    private String HDURL;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagetitle = findViewById(R.id.titletv);
        imageView = findViewById(R.id.image_view);
        imageinfo = findViewById(R.id.infotv);
        imagedate = findViewById(R.id.datetv);

        videobutton = findViewById(R.id.mvideobutton);
        progressBar = findViewById(R.id.mprogressbar);

        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        urldate = dateFormat.format(calendar.getTime());


        bottomAppBar = findViewById(R.id.bar);
        setSupportActionBar(bottomAppBar);
        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BlankFragment fragment = new BlankFragment();
                fragment.show(getSupportFragmentManager(), "TAG");
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(HDURL); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);
        jsonParse();

        //Select a date
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DialogFragment DatePicker = new DatePickerFragment();
                DatePicker.show(getSupportFragmentManager(), "DatePicker");
            }
        });
    }

    //***JsonParse!
    public void jsonParse() {
        String url = "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3&date=" + urldate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    response.getString("title");
                    response.getString("explanation");
                    response.getString("date");
                    response.getString("media_type");
                    response.getString("hdurl");

                    String image_date = response.getString("date");
                    String image_info = response.getString("explanation");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");
                    String image_type = response.getString("media_type");
                    String image_hd_url = response.getString("hdurl");

                    imagedate.setText(image_date);
                    imageinfo.setText(image_info);
                    imagetitle.setText(imagename);


                    Picasso.get().load(image_url).into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(Exception e) {
                        }
                    });

                    imageView.setVisibility(View.VISIBLE);
                    videobutton.setVisibility(View.INVISIBLE);

                    if (image_type.equals("video")) {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.GONE);
                        videobutton.setVisibility(View.VISIBLE);
                        videobutton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Uri uri = Uri.parse(URL); // missing 'http://' will cause crashed
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });

                    }
                    URL = image_url;
                    HDURL = image_hd_url;

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(imagedate, "Unexpected response code 500", Snackbar.LENGTH_LONG);
                snackbar.setAnchorView(floatingActionButton);
                snackbar.show();
            }
        });
        requestQueue.add(request);

    }


    //BottomAppbarNavigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sendbutton:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, URL);
                sendIntent.setType("text/plain");

                Intent shareIntent = Intent.createChooser(sendIntent, null);
                startActivity(shareIntent);
                break;
            case R.id.downloadbutton:
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        return true;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        month++;
        String date = year + "-" + month + "-" + dayOfMonth;

        urldate = date;
        progressBar.setVisibility(View.VISIBLE);
        jsonParse();   // Refresh parse

    }
}






