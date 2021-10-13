package com.searchIn.androidapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.unity3d.player.UnityPlayerActivity;

public class UnityHandlerActivity extends AppCompatActivity {
    private Button sm;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unity_handler);
        Button buttonEnter = findViewById(R.id.buttonEnter);
        sm = (Button)findViewById(R.id.sm);

        buttonEnter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnityHandlerActivity.this, UnityPlayerActivity.class);
                startActivity(intent);
            }


        });
        sm.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v){
                openSocialFeed();

            }
            public void openSocialFeed(){
                Intent intent = new Intent(UnityHandlerActivity.this,SocialFeed.class);
                startActivity(intent);

            }
        });


    }
}