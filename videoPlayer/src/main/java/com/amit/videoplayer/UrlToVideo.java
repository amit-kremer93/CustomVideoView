package com.amit.videoplayer;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class UrlToVideo extends FrameLayout {
    private Context mContext;
    private Button mPlay;
    private Button mStop;
    private final String TAG = "myVideoView";


    public UrlToVideo(@NonNull Context context) {
        super(context);
        mContext = context;
        inflate();
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        inflate();
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        inflate();
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        inflate();
    }

    private void inflate(){
        LayoutInflater.from(mContext).inflate(R.layout.simple_video_view, this);
    }

    private void init(){
        mStop = findViewById(R.id.my_stop);
        mPlay = findViewById(R.id.my_start);
        mStop.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "stopppp");
            }
        });
        mPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "playyyy");
            }
        });
        addView(mStop);
        addView(mPlay);
    }
}
