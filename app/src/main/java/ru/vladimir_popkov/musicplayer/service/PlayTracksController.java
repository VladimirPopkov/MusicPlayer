package ru.vladimir_popkov.musicplayer.service;

public interface PlayTracksController {
    void play();
    void pause();
    void seekTo(int pos);
    int getSeek();
    int getDuration();
    boolean isPlaying();
}
