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
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Locale;

import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.service.PlayTracksBinder;
import ru.vladimir_popkov.musicplayer.service.PlayTracksController;
import ru.vladimir_popkov.musicplayer.service.PlayTracksService;

public class TrackActivity extends AppCompatActivity {

    public final static String TRACKS = "tracks_key";
    public final static String POS = "pos_key";
    private TextView mTrackName;
    private TextView mTrackTime;
    private SeekBar mSeekBar;
    private ImageView mBtnPlay;
    private ImageView mCover;
    private Handler mProgressHandler;
    private PlayTracksController controller;
    private Track lastTrack;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            controller = ((PlayTracksBinder) service).getController();
            startProgressListener();
            updatePlayPauseBtn(controller != null && controller.isPlaying());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            controller = null;
            stopProgressListener();
        }
    };

    public static void playTrack(Context context, ArrayList<Track> tracks, int pos) {
        Intent intent = new Intent(context, TrackActivity.class);
        intent.putExtra(TRACKS, tracks);
        intent.putExtra(POS, pos);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        mProgressHandler = new Handler();

        mTrackName = findViewById(R.id.track_name);
        mCover = findViewById(R.id.cover);
        mBtnPlay = findViewById(R.id.button_play);
        mTrackTime = findViewById(R.id.track_time);

        mSeekBar = findViewById(R.id.seek_bar);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && controller != null) {
                    controller.seekTo(progress);
                    updateInfo();
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
                    updatePlayPauseBtn(true);
                } else if (controller != null && controller.isPlaying()) {
                    controller.pause();
                    updatePlayPauseBtn(false);
                }
            }
        });
        findViewById(R.id.button_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.prevTrack();
                    updateInfo();
                }
            }
        });

        findViewById(R.id.button_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (controller != null) {
                    controller.nextTrack();
                    updateInfo();
                }
            }
        });
        findViewById(R.id.stop_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlayTracksService.stop(TrackActivity.this);
                finish();
            }
        });

        if (getIntent().hasExtra(TRACKS)) {
            PlayTracksService.start(this,
                    (ArrayList<Track>) getIntent().getSerializableExtra(TRACKS),
                    getIntent().getIntExtra(POS, 0));
        }
    }

    private void updatePlayPauseBtn(boolean isPlaying) {
        if (isPlaying) {
            mBtnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.pause));
            stopProgressListener();
        } else {
            mBtnPlay.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.play));
            startProgressListener();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindService(new Intent(this, PlayTracksService.class), connection, 0);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopProgressListener();
        unbindService(connection);
        controller = null;
    }

    private void startProgressListener() {
        stopProgressListener();
        if (controller != null) {
            mProgressHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (controller != null) {
                        updateInfo();
                        mProgressHandler.postDelayed(this, 1000);
                    }
                }
            });
        }
    }

    private void stopProgressListener() {
        mProgressHandler.removeCallbacks(null);
    }

    private void updateInfo() {
        mSeekBar.setMax(controller.getDuration());
        mSeekBar.setProgress(controller.getSeek());
        mTrackTime.setText(getTime(controller.getDuration() - controller.getSeek()));
        Track track = controller.getCurrentTrack();
        if (track != null && !track.equals(lastTrack)) {
            lastTrack = track;
            mTrackName.setText(lastTrack.getArtist().getName() + " - " + lastTrack.getName());
            Picasso.get()
                    .load(track.getCover())
                    .into(mCover);
        }
    }

    public String getTime(int timeMs) {
        return String.format(Locale.getDefault(), "%02d:%02d",
                (timeMs / (1000 * 60) % 60),
                (timeMs / 1000) % 60);
    }
}
