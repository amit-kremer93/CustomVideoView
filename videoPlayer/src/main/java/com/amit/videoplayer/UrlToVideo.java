package com.amit.videoplayer;

import android.content.Context;
import android.media.AudioManager;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gaurav.gesto.OnGestureListener;

public class UrlToVideo extends FrameLayout {
    private Context mContext;
    private VideoView mVideoView;
    private Uri mVideoUrl;
    private CustomMediaController mediaController;
    public static final String TAG = "myVideoView";
    private int currentVolume = 5;
    private AudioManager audioManager;


    public UrlToVideo(@NonNull Context context) {
        super(context);
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.simple_video_view, this, true);

        audioManager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        mVideoView = findViewById(R.id.my_videoView);

        //volume gesture. swipe right or left in order to change the volume
        mVideoView.setOnTouchListener(new OnGestureListener(mContext) {
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                changeVolume(sides.RIGHT);
            }

            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                changeVolume(sides.LEFT);
            }

            @Override
            public void onClick() {
                super.onClick();
                if (mediaController.isShown()) {
                    mediaController.hide();
                } else {
                    mediaController.show();
                }

            }
        });

    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public UrlToVideo(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    /**
     * takes string url and parse it into URI for the videoView
     *
     * @param url
     */
    public void setUrlToPlay(String url) {
        mVideoUrl = Uri.parse(url);
        mVideoView.setVideoURI(mVideoUrl);
        mediaController = new CustomMediaController(mContext, mVideoView, mVideoUrl);
        mVideoView.setMediaController(mediaController);
    }

    /**
     * change the video volume according the gesture
     *
     * @param side
     */
    private void changeVolume(sides side) {
        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        Log.d(TAG, "current volume: " + currentVolume);
        String str = "";
        if (side == sides.RIGHT) {
            if (currentVolume == 15) {
                str = "Max Volume!";
            } else {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume + 1, AudioManager.FLAG_PLAY_SOUND);
                str = "Volume +1";
            }
        } else {
            if (currentVolume == 0) {
                str = "Mim Volume!";
            } else {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume - 1, AudioManager.FLAG_PLAY_SOUND);
                str = "Volume -1";
            }
        }
        Toast toast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}

enum sides {
    RIGHT,
    LEFT
}
