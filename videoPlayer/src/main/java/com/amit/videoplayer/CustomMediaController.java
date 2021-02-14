package com.amit.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class CustomMediaController extends MediaController {

    private FloatingActionButton fab1, fab2;
    private ImageButton fullScreen;
    private String isFullScreen;
    private VideoView mVideo;

    public CustomMediaController(Context context, VideoView video) {
        super(context);
        mVideo = video;
    }

    @Override
    public void setAnchorView(View view) {

        super.setAnchorView(view);
        removeAllViews();
        //image button for full screen to be added to media controller
//        fullScreen = new ImageButton(super.getContext());
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.media_controller, this, true);

        fab1 = new FloatingActionButton(getContext());
        fab1.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mVideo.start();
            }
        });

//        FrameLayout.LayoutParams params =
//                new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//                        LayoutParams.WRAP_CONTENT);
//        params.gravity = Gravity.CENTER;
//        removeAllViews();
//        addView(fab1, params);
//        addView(fullScreen, params);

        //fullscreen indicator from intent
//        isFullScreen = ((Activity) getContext()).getIntent().
//                getStringExtra("fullScreenInd");
//
//        if ("y".equals(isFullScreen)) {
//            fullScreen.setImageResource(R.drawable.ic_full_screen);
//        } else {
//            fullScreen.setImageResource(R.drawable.ic_full_screen);
//        }

        //add listener to image button to handle full screen and exit full screen events
//        fullScreen.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });

    }
}


