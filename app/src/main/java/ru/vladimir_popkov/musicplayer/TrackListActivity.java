package ru.vladimir_popkov.musicplayer;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class TrackListActivity extends AppCompatActivity {

    private TrackAdapter mAdapter;
    RecyclerView mTrackList;
    List<Track> mTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        mTracks.add(new Track("Кипелов", "Вавилон",
                "http://cdndl.zaycev.net/commonImage/120461_square250.jpg",
                "http://cdndl.zaycev.net/120461/4100498/kipelov_-_vavilon_%28zaycev.net%29.mp3"));
        mTracks.add(new Track("25/17", "Молитва",
                "http://cdndl.zaycev.net/commonImage/143566_square250.jpg",
                "http://cdndl.zaycev.net/143566/7119050/25_17_-_molitva_%28zaycev.net%29.mp3"));
        mTracks.add(new Track("Король и Шут", "Лесник",
                "http://cdndl.zaycev.net/commonImage/120900_square250.jpg",
                "http://cdndl.zaycev.net/120900/3051899/korol_i_shut_-_lesnik_%28zaycev.net%29.mp3"));
        mTrackList = (RecyclerView) findViewById(R.id.track_list);
        mTrackList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrackAdapter(mTracks);
        mAdapter.setTrackClickListener(new TrackHolder.TrackClickListener() {
            @Override
            public void onTrackClicked(Track track) {
                /*Intent intent = new Intent(TrackListActivity.this, TrackActivity.class);
                intent.putExtra("track", (Parcelable) track);
                startActivity(intent);*/
                Intent intent = new Intent(TrackListActivity.this, TrackActivity.class);
                intent.putExtra(Track.class.getCanonicalName(), track);
                startActivity(intent);
            }
        });
        mTrackList.setAdapter(mAdapter);
    }
}
