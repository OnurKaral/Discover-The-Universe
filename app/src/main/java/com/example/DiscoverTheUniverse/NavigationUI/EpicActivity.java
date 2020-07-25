package com.example.DiscoverTheUniverse.NavigationUI;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.DiscoverTheUniverse.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class EpicActivity extends AppCompatActivity {
    private String url = "https://api.nasa.gov/EPIC/api/natural?api_key=hhOItewgwlQmkaSH6xq7aZMpnLqCisxdUdomDfi3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_epic);

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String myresponse = response.body().string();
                    EpicActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println(myresponse);
                        }
                    });
                }


            }
        });


    }
}