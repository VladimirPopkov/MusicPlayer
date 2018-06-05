package ru.vladimir_popkov.musicplayer.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.TrackActivity;
import ru.vladimir_popkov.musicplayer.adapters.TrackAdapter;
import ru.vladimir_popkov.musicplayer.adapters.holders.TrackHolder;
import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.network.MusicAPI;

public class TrackListFragment extends Fragment {

    private TrackAdapter mAdapter;
    private View mProgress;
    private ArrayList<Track> mTracks = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, null);
        RecyclerView trackList = (RecyclerView) view.findViewById(R.id.data_list);
        mProgress = view.findViewById(R.id.progress);
        mAdapter = new TrackAdapter(mTracks);
        mAdapter.setTrackClickListener(new TrackHolder.TrackClickListener() {
            @Override
            public void onTrackClicked(Track track) {
                ArrayList<Track> tracks = mAdapter.getTracks();
                TrackActivity.playTrack(view.getContext(), tracks, tracks.indexOf(track));
            }
        });
        trackList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        trackList.setAdapter(mAdapter);
        loadTracks();

        return view;
    }

    private void loadTracks(){
        mProgress.setVisibility(View.VISIBLE);
        mProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        MusicAPI.getMusicService().getTracks().enqueue(new Callback<List<Track>>() {
            @Override
            public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                mProgress.setVisibility(View.GONE);
                mTracks.clear();
                mTracks.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Track>> call, Throwable t) {
                mProgress.setVisibility(View.GONE);
            }
        });
    }
}
