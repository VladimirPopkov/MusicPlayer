package ru.vladimir_popkov.musicplayer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TimeFormatException;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.service.PlayTracksBinder;
import ru.vladimir_popkov.musicplayer.service.PlayTracksController;
import ru.vladimir_popkov.musicplayer.service.PlayTracksService;

public class TrackActivity extends AppCompatActivity {

    TextView mTrackName;
    TextView mTrackTime;
    SeekBar mSeekBar;
    ImageView mBtnPrevious;
    ImageView mBtnPlay;
    ImageView mBtnNext;
    ImageView mCover;
    private Handler mProgressHandler;
    private PlayTracksController controller;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controller = ((PlayTracksBinder)service).getController();
            startProgressListener();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            controller = null;
            stopProgressListener();
        }
    };

    public static void playTrack(Context context, Track track){
        Intent intent = new Intent(context, TrackActivity.class);
        intent.putExtra(Track.class.getCanonicalName(), track);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        Track track = (Track) getIntent().getSerializableExtra(Track.class.getCanonicalName());
        mTrackName = findViewById(R.id.track_name);
        mCover = findViewById(R.id.cover);
        mSeekBar = findViewById(R.id.seek_bar);
        mBtnPlay = findViewById(R.id.button_play);
        mTrackTime = findViewById(R.id.track_time);

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && controller != null) {
                    controller.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mBtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null && !controller.isPlaying()) {
                    controller.play();
                    startProgressListener();
                    updatePlayPauseBtn(true);
                } else if (controller != null && controller.isPlaying()) {
                    controller.pause();
                    stopProgressListener();
                    updatePlayPauseBtn(false);
                }
            }
        });

        mProgressHandler = new Handler();

        mTrackName.setText(track.getArtist().getName() + " - " + track.getName());

        Picasso.get()
                .load( track.getCover())
                .into(mCover);

        PlayTracksService.start(this, track.getPath());
        startProgressListener();
        updatePlayPauseBtn(true);
    }

    private void updatePlayPauseBtn(boolean isPlaying) {
        if (isPlaying) {
            mBtnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));
        } else {
            mBtnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, PlayTracksService.class), connection, BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopProgressListener();
        unbindService(connection);
        controller = null;
    }


    private void startProgressListener() {
        mProgressHandler.removeCallbacks(null);
        if (controller != null) {
            mProgressHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (controller != null && controller.isPlaying()) {
                        mSeekBar.setMax(controller.getDuration());
                        mSeekBar.setProgress(controller.getSeek());
                        mTrackTime.setText(controller.getTimeTrack());
                        mTrackTime.setText(controller.getTimeLeft());
                        mProgressHandler.postDelayed(this, 1000);
                    }
                }
            });
        }
    }

    private void stopProgressListener() {
        mProgressHandler.removeCallbacks(null);
    }
}
