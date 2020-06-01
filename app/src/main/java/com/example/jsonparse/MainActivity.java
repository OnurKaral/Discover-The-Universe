package com.example.jsonparse;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private TextView imagetitle;
    private RequestQueue requestQueue;
    private ImageView imageView;
    private TextView imageinfo;
    private TextView imagedate;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton floatingActionButton;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private DatePickerDialog.OnDateSetListener dateSetListener;

    private String urldate;
    private String fileUri;
    private  String URL;
    public static final int PERMISSION_WRITE = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagetitle = findViewById(R.id.titletv);
        imageView = findViewById(R.id.image_view);
        imageinfo = findViewById(R.id.infotv);
        imagedate = findViewById(R.id.datetv);

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

        requestQueue = Volley.newRequestQueue(this);
      //  jsonParse();

        // * Json Parse
        String url = "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3&date=" + urldate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("title");
                    response.getString("explanation");
                    response.getString("date");

                    String image_date = response.getString("date");
                    String image_info = response.getString("explanation");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");

                    imagedate.setText(image_date);
                    imageinfo.setText(image_info);
                    imagetitle.setText(imagename);

                    Picasso.get().load(image_url).fit().centerInside().into(imageView);
                    URL = image_url;

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

        //Select a date
        floatingActionButton = findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = year + "-" + month + "-" + dayOfMonth;
                urldate = date;
                jsonParse();   // Refresh parse
            }
        };
    }

    /*
    public void DownloadImage(String url) {
        Picasso.get().load(url).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                try {
                    File mydir = new File(Environment.getExternalStorageDirectory() +"");
                    if (!mydir.exists()) {
                        mydir.mkdirs();
                    }

                    fileUri = mydir.getAbsolutePath() + File.separator + System.currentTimeMillis() + ".jpg";
                    FileOutputStream outputStream = new FileOutputStream(fileUri);

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch(IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(getApplicationContext(), "Image Downloaded", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });
    }

    //runtime storage permission
    public boolean checkPermission() {
        int READ_EXTERNAL_PERMISSION = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if((READ_EXTERNAL_PERMISSION != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_WRITE);
            return true;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==PERMISSION_WRITE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //do somethings
        }
    }
/*

            */
    //***JsonParse!
    public  void jsonParse() {
        //String url= "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";
        String url = "https://api.nasa.gov/planetary/apod?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3&date=" + urldate;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    response.getString("title");
                    response.getString("explanation");
                    response.getString("date");

                    String image_date = response.getString("date");
                    String image_info = response.getString("explanation");
                    String imagename = response.getString("title");
                    String image_url = response.getString("url");

                    imagedate.setText(image_date);
                    imageinfo.setText(image_info);
                    imagetitle.setText(imagename);

                    Picasso.get().load(image_url).fit().centerInside().into(imageView);
                    URL = image_url;

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
       getMenuInflater().inflate(R.menu.bar_menu,menu);

       return true;
    }
}






