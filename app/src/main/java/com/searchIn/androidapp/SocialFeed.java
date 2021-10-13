package com.searchIn.androidapp;

import android.os.Bundle;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SocialFeed extends AppCompatActivity {

    // creating variables for our requestqueue,
    // array list, progressbar, edittext,
    // image button and our recycler view.

    private RequestQueue mRequestQueue;
    private ArrayList<InstaModal> instaModalArrayList;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_feed);

        // initializing our views.
        progressBar = findViewById(R.id.idLoadingPB);
        instaModalArrayList = new ArrayList<>();

        // calling method to load
        // data in recycler view.
        getInstagramData();
    }

    private void getInstagramData() {
        // below line is use to initialize the variable for our request queue.
        mRequestQueue = Volley.newRequestQueue(SocialFeed.this);

        // below line is use to clear cache this will
        // be use when our data is being updated.
        mRequestQueue.getCache().clear();

        // below is the url for getting data
        // from API in json format.
        String url = "https://v1.nocodeapi.com/aniket/instagram/vdkuMWhNJoKmAxNm";

        // below line we are creating a new request queue.
        RequestQueue queue = Volley.newRequestQueue(SocialFeed.this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressBar.setVisibility(View.GONE);
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    for (int i = 0; i < dataArray.length(); i++) {
                        // below line is to extract data from JSON file.
                        JSONObject dataObj = dataArray.getJSONObject(i);
                        String id = dataObj.getString("id");
                        String media_type = dataObj.getString("media_type");
                        String permalink = dataObj.getString("permalink");
                        String media_url = dataObj.getString("media_url");
                        String username = dataObj.getString("username");
                        String caption = dataObj.getString("caption");
                        String timestamp = dataObj.getString("timestamp");

                        // below line is to add a constant author image URL to our recycler view.
                        String author_url = "https://search-in.s3.amazonaws.com/media/Product_Images/Turmeric_Tata.png";
                        int likesCount = 100 + (i * 10);

                        // below line is use to add data to our modal class.
                        InstaModal instaModal = new InstaModal(id, media_type, permalink, media_url, username, caption, timestamp, author_url, likesCount);

                        // below line is use to add modal
                        // class to our array list.
                        instaModalArrayList.add(instaModal);

                        // below line we are creating an adapter class and adding our array list in it.
                        InstagramFeedRVAdapter adapter = new InstagramFeedRVAdapter(instaModalArrayList, SocialFeed.this);
                        RecyclerView instRV = findViewById(R.id.idRVInstaFeeds);

                        // below line is for setting linear layout manager to our recycler view.
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SocialFeed.this, RecyclerView.VERTICAL, false);

                        // below line is to set layout manager to our recycler view.
                        instRV.setLayoutManager(linearLayoutManager);

                        // below line is to set adapter
                        // to our recycler view.
                        instRV.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    // handling error case.
                    e.printStackTrace();
                    Toast.makeText(SocialFeed.this, "Fail to get Data.." + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // handling error message.
                Toast.makeText(SocialFeed.this, "Fail to get Data.." + error, Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
    }
}

