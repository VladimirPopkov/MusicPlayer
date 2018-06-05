package ru.vladimir_popkov.musicplayer.service;

import ru.vladimir_popkov.musicplayer.data.Track;

public interface PlayTracksController {
    void play();
    void pause();
    void seekTo(int pos);
    int getSeek();
    int getDuration();
    boolean isPlaying();
    void nextTrack();
    void prevTrack();
    Track getCurrentTrack();
}
