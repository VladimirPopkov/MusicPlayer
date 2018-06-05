package ru.vladimir_popkov.musicplayer.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.TrackActivity;
import ru.vladimir_popkov.musicplayer.data.Track;

public class PlayTracksService extends Service implements PlayTracksController {

    public final static String START_FOREGROUND = "MusicServiceStart";
    public final static String STOP_FOREGROUND = "MusicServiceStop";
    public final static String TRACKS = "tracks_key";
    public final static String POS = "pos_key";
    private PlayTracksBinder binder = new PlayTracksBinder(this);
    private MediaPlayer mMediaPlayer;
    private ArrayList<Track> mTrackList;
    private int mCurrentPos;
    private final static Map<String, String> headers = new HashMap<>();
    static {
        headers.put("Authorization", "Basic dm92YW46bWFsb3k=");
    }

    public static void start(Context context, ArrayList<Track> trackList, int pos) {
        Intent intent = new Intent(context, PlayTracksService.class);
        intent.setAction(START_FOREGROUND);
        intent.putExtra(TRACKS, trackList);
        intent.putExtra(POS, pos);
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
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                nextTrack();
            }
        });
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
                mTrackList = (ArrayList<Track>) intent.getSerializableExtra(TRACKS);
                mCurrentPos = intent.getIntExtra(POS, 0);
                Track track = mTrackList.get(mCurrentPos);
                if (mMediaPlayer != null && !mMediaPlayer.isPlaying()) {
                    startForeground(1, createNotif(track.getArtist().getName(), track.getName(), R.drawable.play));
                }
                start(track.getPath());
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
            mMediaPlayer.prepare();
            mMediaPlayer.start();
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
        Intent notifyIntent = new Intent(this, TrackActivity.class);
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this,
                0,
                notifyIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String CHANNEL_ID = "channel_01";
            return new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(iconId)
                    .setContentIntent(notifyPendingIntent)
                    .build();
        } else {
            return new Notification.Builder(this)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(iconId)
                    .setContentIntent(notifyPendingIntent)
                    .setPriority(Notification.PRIORITY_DEFAULT)
                    .build();
        }
    }

    @Override
    public void nextTrack() {
        if (mCurrentPos < mTrackList.size() - 1) {
            mCurrentPos++;
            start(mTrackList.get(mCurrentPos).getPath());
        }
    }

    @Override
    public void prevTrack() {
        if (mCurrentPos > 0) {
            mCurrentPos--;
            start(mTrackList.get(mCurrentPos).getPath());
        }
    }

    @Override
    public Track getCurrentTrack() {
        return mTrackList.get(mCurrentPos);
    }
}
