package ru.vladimir_popkov.musicplayer.service;

import android.app.Notification;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.service.PlayTracksController;

public class PlayTracksService extends Service implements PlayTracksController, MediaPlayer.OnPreparedListener {

    public final static String START_FOREGROUND = "MusicServiceStart";
    public final static String STOP_FOREGROUND = "MusicServiceStop";
    public final static String PATH = "path_key";
    private PlayTracksBinder binder = new PlayTracksBinder(this);
    private MediaPlayer mMediaPlayer;
    private final static Map<String, String> headers = new HashMap<>();
    static {
        headers.put("Authorization", "Basic dm92YW46bWFsb3k=");
    }

    public static void start(Context context, String path) {
        Intent intent = new Intent(context, PlayTracksService.class);
        intent.setAction(START_FOREGROUND);
        intent.putExtra(PATH, path);
        context.startService(intent);
    }

    public static void stop(Context context) {
        Intent intent = new Intent(context, PlayTracksService.class);
        intent.setAction(STOP_FOREGROUND);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMediaPlayer = new MediaPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            if (START_FOREGROUND.equals(intent.getAction())) {
                if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    startForeground(1, createNotif("Notification's title", "Notification's text", R.drawable.play));
                }
                start(intent.getStringExtra(PATH));
            } else if (STOP_FOREGROUND.equals(intent.getAction())) {
                stopForeground(true);
                stop();
                stopSelf();
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void start(String path) {
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(this, Uri.parse(path), headers);
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setOnPreparedListener(this);
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
        }
    }

    @Override
    public void play() {
        if (!mMediaPlayer.isPlaying()) {
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
        }
    }

    @Override
    public void seekTo(int pos) {
        mMediaPlayer.seekTo(pos);
    }

    @Override
    public int getSeek() {
        return mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer.isPlaying();
    }

    private Notification createNotif(String title, String text, int iconId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "channel_01";
            return new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(iconId)
                    .build();
        } else {
            return new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(iconId)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .build();
        }
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
    }
}
