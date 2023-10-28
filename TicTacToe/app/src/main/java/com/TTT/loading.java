package com.TTT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class loading extends AppCompatActivity {

    int progressInt=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        ProgressBar progressBar= findViewById(R.id.progressbar);

        Activity activity = loading.this;
        progressBar.setProgress(progressInt);
        progressBar.setMax(100);

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                progressInt = progressInt +10;
                progressBar.setProgress(progressInt);

                if (progressBar.getProgress() == 100){
                    timer.cancel();
                    Intent intent = new Intent(loading.this,MainActivity.class);
                    activity.startActivity(intent);
                    finish();
                }
            }
        },1000,50);

    }
}