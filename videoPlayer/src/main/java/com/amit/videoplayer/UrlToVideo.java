package com.amit.videoplayer;

import android.content.Context;
import android.graphics.Canvas;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UrlToVideo extends FrameLayout {
    private Context mContext;
    private Button mPlay;
    private Button mStop;
    private VideoView mVideoView;
    private Uri mVideoUrl;
    private MediaController mediaController;
    private final String TAG = "myVideoView";


    public UrlToVideo(@NonNull Context context) {
        super(context);
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.simple_video_view, this, true);

        mVideoView = findViewById(R.id.my_videoView);
        mPlay = findViewById(R.id.my_start);
        mStop = findViewById(R.id.my_stop);
//        mediaController = new MediaController(mContext);
//        mVideoView.setMediaController(mediaController);
        mPlay.setOnClickListener(startClickListener);
        mStop.setOnClickListener(stopClickListener);
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    View.OnClickListener startClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "PLAY clicked!");
            mVideoView.start();
        }
    };

    View.OnClickListener stopClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "STOP clicked!");
            mVideoView.pause();
        }
    };

    public void setUrlToPlay(String url) {
        mVideoUrl = Uri.parse(url);
        mVideoView.setVideoURI(mVideoUrl);
    }
}
