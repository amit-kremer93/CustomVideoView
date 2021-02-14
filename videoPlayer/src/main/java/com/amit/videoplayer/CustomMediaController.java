package com.amit.videoplayer;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.github.clans.fab.FloatingActionButton;

import static android.content.Context.SENSOR_SERVICE;

public class CustomMediaController extends MediaController {

    private FloatingActionButton mStart, mStop, mFFW, mFR;
    private TextView mCurrent, mTotalTime;
    private SeekBar mSeekBar;
    private VideoView mVideo;
    private Handler mHandler = new Handler();
    private Context mContext;
    private SensorEventListener mSensorEventListener;

    public CustomMediaController(Context context, VideoView video, Uri uri) {
        super(context);
        mVideo = video;
        mContext = context;
    }


    /**
     * init the media controller and the buttons
     */
    private void initMediaController() {
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.media_controller, this, true);

        mStart = findViewById(R.id.FAB_start);
        mStop = findViewById(R.id.FAB_stop);
        mFFW = findViewById(R.id.FAB_fw);
        mFR = findViewById(R.id.FAB_bw);
        mSeekBar = findViewById(R.id.SKB_progress);
        mCurrent = findViewById(R.id.time_current);
        mTotalTime = findViewById(R.id.total_time);
        mStart.setOnClickListener(startPause);
        mStop.setOnClickListener(stop);
        mFFW.setOnClickListener(fastForward);
        mFR.setOnClickListener(fastRewind);
        mSensorEventListener = new SensorEventListener(){

            @Override
            public void onSensorChanged(SensorEvent event) {
                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                }
                else {
                    fullScreenMode();
                }
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        SensorManager sm = (SensorManager)mContext.getSystemService(SENSOR_SERVICE);
        sm.registerListener(mSensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);

        mVideo.setOnPreparedListener(videoViewOnPrepare);
        mSeekBar.setOnSeekBarChangeListener(seekBarListener);
    }

    @Override
    public void setAnchorView(View view) {
        super.setAnchorView(view);
        removeAllViews();
        initMediaController();
    }

    /**
     * resize the video view size to fit landscape orientation
     */
    private void fullScreenMode(){
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        android.widget.RelativeLayout.LayoutParams params = (android.widget.RelativeLayout.LayoutParams) mVideo.getLayoutParams();
        params.width = metrics.widthPixels;
        params.height = metrics.heightPixels;
        params.leftMargin = 0;
        mVideo.setLayoutParams(params);
    }

    /**
     * Start or Pause listener for videoView. also change the icon of the button
     */
    View.OnClickListener startPause = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mVideo != null) {
                if (mVideo.isPlaying()) {
                    //set pause icon
                    mStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24, null));
                    mVideo.pause();
                } else {
                    //set start icon
                    mStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_24, null));
                    mHandler.postDelayed(updateVideoTime, 100);
                    mVideo.start();
                }
            }
        }
    };

    /**
     * Stop listener
     */
    View.OnClickListener stop = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mVideo != null) {
                mVideo.seekTo(0);
                mVideo.pause();
                mStart.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_arrow_24, null));
            }
        }
    };

    /**
     * Fast-forward listener. +10 seconds every press
     */
    View.OnClickListener fastForward = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mVideo != null) {
                mVideo.seekTo(mVideo.getCurrentPosition() + 10000);
                mSeekBar.setProgress(mVideo.getCurrentPosition() + 10000);
            }
        }
    };

    /**
     * Fast-rewind listener. -10 seconds every press
     */
    View.OnClickListener fastRewind = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mVideo != null) {
                mVideo.seekTo(mVideo.getCurrentPosition() - 10000);
                mSeekBar.setProgress(mVideo.getCurrentPosition() - 10000);
            }
        }
    };

    /**
     * Handler for the seek bar. updates it according to the video
     */
    private Runnable updateVideoTime = new Runnable() {
        @Override
        public void run() {
            long currentPosition = mVideo.getCurrentPosition();
            mSeekBar.setProgress((int) currentPosition);
            mCurrent.setText(getStringTimeFromProgress(currentPosition));
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     * init the media player before it starts to run
     */
    MediaPlayer.OnPreparedListener videoViewOnPrepare = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mSeekBar.setMax(mp.getDuration());
            mSeekBar.setProgress(0);
            mCurrent.setText("00:00");
            mTotalTime.setText(getStringTimeFromProgress(mp.getDuration()));
        }
    };

    /**
     * format milliseconds to string "xx:xx"
     * @param milliseconds
     * @return
     */
    private String getStringTimeFromProgress(long milliseconds) {
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int) (milliseconds / (1000 * 60 * 60));
        int minutes = (int) (milliseconds % (1000 * 60 * 60)) / (1000 * 60);
        int seconds = (int) ((milliseconds % (1000 * 60 * 60)) % (1000 * 60) / 1000);
        // Add hours if there
        if (hours > 0) {
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = "" + seconds;
        }

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * video progress seekBar listener
     */
    SeekBar.OnSeekBarChangeListener seekBarListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                mVideo.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
}


