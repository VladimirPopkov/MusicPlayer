package ru.vladimir_popkov.musicplayer.service;

import android.os.Binder;

public class PlayTracksBinder extends Binder {

    private PlayTracksController mController;

    public PlayTracksBinder(PlayTracksController controller) {
        mController = controller;
    }

    public PlayTracksController getController() {
        return mController;
    }
}
