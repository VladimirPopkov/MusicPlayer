package ru.vladimir_popkov.musicplayer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.network.MusicAPI;

public class TrackListActivity extends AppCompatActivity {

    private TrackAdapter mAdapter;
    RecyclerView mTrackList;
    List<Track> mTracks = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_list);

        mTrackList = (RecyclerView) findViewById(R.id.track_list);
        mTrackList.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrackAdapter(mTracks);
        mAdapter.setTrackClickListener(new TrackHolder.TrackClickListener() {
            @Override
            public void onTrackClicked(Track track) {
                Intent intent = new Intent(TrackListActivity.this, TrackActivity.class);
                intent.putExtra(Track.class.getCanonicalName(), track);
                startActivity(intent);
            }
        });
        mTrackList.setAdapter(mAdapter);
        loadTracks();
    }

    private void loadTracks(){
        MusicAPI.getMusicService().getTracks().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                mTracks.clear();
                mTracks.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                Log.d("vova", "onFailure: " + t);
            }
        });
    }
}
