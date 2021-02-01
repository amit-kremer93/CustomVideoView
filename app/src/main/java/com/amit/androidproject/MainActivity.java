package com.amit.androidproject;

import android.os.Bundle;
import android.widget.Button;
import android.widget.VideoView;

import com.amit.videoplayer.UrlToVideo;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private UrlToVideo mVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mVideo = findViewById(R.id.video);
        mVideo.setUrlToPlay("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4");
    }

}