package com.example.DiscoverTheUniverse;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.DiscoverTheUniverse.NavigationUI.BlankFragment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private int STORAGE_PERMISSION_CODE = 1;
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
    private String image_hd_url;
    private String image_type;
    private Uri bmpUri = null;
    private MenuItem download;
    private String imagetitlesend;
    private String imagedatesend;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private ToggleButton addfavoritesbutton;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private String uid;
    private NetworkInfo activeNetwork;
    private UploadTask muploadtask;
    private Drawable d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        uid = firebaseUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference("favorites/");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        d = getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24);

        imagetitle = findViewById(R.id.titletv);
        imageView = findViewById(R.id.image_view);
        imageinfo = findViewById(R.id.infotv);
        imagedate = findViewById(R.id.datetv);

        addfavoritesbutton = findViewById(R.id.addfavorites);
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

        addfavoritesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addfavorites();
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
                if (isNetworkAvailable() == true) {

                    download.setEnabled(true);
                    try {
                        response.getString("title");
                        response.getString("explanation");
                        response.getString("date");
                        response.getString("media_type");

                        try {
                            response.getString("hdurl");
                            image_hd_url = response.getString("hdurl");
                        } catch (Exception e) {
                            System.out.println("error");
                        }

                        String image_date = response.getString("date");
                        String image_info = response.getString("explanation");
                        String imagename = response.getString("title");
                        String image_url = response.getString("url");
                        image_type = response.getString("media_type");

                        imagedate.setText(image_date);
                        imageinfo.setText(image_info);
                        imagetitle.setText(imagename);
                        imagetitlesend = imagename;
                        imagedatesend = image_date;

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
                            download.setVisible(false);
                            HDURL = null;
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

                        } else {
                            download.setVisible(true);
                        }
                        URL = image_url;
                        HDURL = image_hd_url;

                        addfavoritesbutton.setChecked(false);
                        addfavoritesbutton.setEnabled(true);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Snackbar snackbar = Snackbar.make(imagedate, "No Connection", Snackbar.LENGTH_LONG);
                    snackbar.setAnchorView(floatingActionButton);
                    snackbar.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                download.setEnabled(false);
                progressBar.setVisibility(View.INVISIBLE);
                Snackbar snackbar = Snackbar.make(imagedate, "Unexpected response code 500", Snackbar.LENGTH_LONG);
                snackbar.setAnchorView(floatingActionButton);
                snackbar.show();

            }
        });
        requestQueue.add(request);
    }

    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable) {
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }

        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider", file);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }

    private void addfavorites() {
        bmpUri = getLocalBitmapUri(imageView);

        if (bmpUri != null) {


            final StorageReference filereference = mStorageRef.child(uid).child(imagedatesend + " " + '"' + imagetitlesend + '"');

            muploadtask = (UploadTask) filereference.putFile(bmpUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {
                    filereference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            String uId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            User user = new User(uId, uri.toString(), imagetitlesend);
                            mDatabaseRef.child("favorites").child(uId).child(imagedatesend + " " + '"' + imagetitlesend + '"').setValue(user);

                        }
                    });
                    taskSnapshot.getMetadata();

                }
            });
            addfavoritesbutton.setEnabled(false);

        } else {
            requestStoragePermission();
        }
    }

    //BottomAppbarNavigation
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.downloadbutton:
                if (image_type.equals("video")) {

                } else if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

                    bmpUri = getLocalBitmapUri(imageView);
                    Snackbar snackbar = Snackbar.make(imagedate, "Image Downloaded.", Snackbar.LENGTH_LONG);
                    snackbar.setAnchorView(floatingActionButton);
                    snackbar.show();
                } else {
                    requestStoragePermission();
                }
                break;
            case R.id.sendbutton:
                bmpUri = getLocalBitmapUri(imageView);
                if (bmpUri != null && ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    // Construct a ShareIntent with link to image
                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, imagetitlesend);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
                    shareIntent.setType("image/*");
                    // Launch sharing dialog for image
                    startActivity(Intent.createChooser(shareIntent, "Share Image"));
                } else if (image_type.equals("video")) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_TEXT, URL);
                    sendIntent.setType("text/plain");
                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);
                } else {
                    // ...sharing failed, handle error
                    requestStoragePermission();
                }
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar_menu, menu);
        download = menu.findItem(R.id.downloadbutton);
        if (isNetworkAvailable() == true) {
            download.setEnabled(false);

        }
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

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("You should give the permission for Download an image")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);

        }
    }

    public void checkConnection() {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = manager.getActiveNetworkInfo();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}






