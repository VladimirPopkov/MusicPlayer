package ru.vladimir_popkov.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ru.vladimir_popkov.musicplayer.data.Track;

public class TrackActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    TextView mTrackName;
    TextView mTrackTime;
    SeekBar mSeekBar;
    ImageView mBtnPrevious;
    ImageView mBtnPlay;
    ImageView mBtnNext;
    ImageView mCover;
    MediaPlayer mediaPlayer;
    AudioManager am;
    private final static Map<String, String> headers = new HashMap<>();
    static {
        headers.put("Authorization", "Basic dm92YW46bWFsb3k=");
    }

    public static void playTrack(Context context, Track track){
        Intent intent = new Intent(context, TrackActivity.class);
        intent.putExtra(Track.class.getCanonicalName(), track);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        mTrackName = (TextView) findViewById(R.id.track_name);
        mCover = (ImageView) findViewById(R.id.cover);

        Track track = (Track) getIntent().getSerializableExtra(Track.class.getCanonicalName());
        mTrackName.setText(track.getArtist().getName() + " - " + track.getName());

        Picasso.get() //передаем контекст приложения
                .load( track.getCover()) //адрес изображения
                .into(mCover); //ссылка на ImageView

        mediaPlayer = new MediaPlayer();
        try {
            //mediaPlayer.setDataSource(track.getPath());
            mediaPlayer.setDataSource(this, Uri.parse(track.getPath()), headers);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
