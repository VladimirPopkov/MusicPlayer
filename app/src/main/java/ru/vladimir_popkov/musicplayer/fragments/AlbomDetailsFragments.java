package ru.vladimir_popkov.musicplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.TrackActivity;
import ru.vladimir_popkov.musicplayer.adapters.TrackAdapter;
import ru.vladimir_popkov.musicplayer.adapters.holders.TrackHolder;
import ru.vladimir_popkov.musicplayer.data.Albom;
import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.network.MusicAPI;

public class AlbomDetailsFragments extends Fragment {
    private TrackAdapter mAdapter;
    private TextView mAlbomName;
    View mProgress;
    private Albom mAlbom;

    public static AlbomDetailsFragments getInstance(Albom albom){
        AlbomDetailsFragments albomDetailsFragments = new AlbomDetailsFragments();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Albom.class.getCanonicalName(), albom);
        albomDetailsFragments.setArguments(bundle);
        return albomDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAlbom =(Albom) getArguments().getSerializable(Albom.class.getCanonicalName());
        final View view = inflater.inflate(R.layout.fragment_detail_albom, null);
        mAlbomName = view.findViewById(R.id.albom_name);
        RecyclerView tracksList = view.findViewById(R.id.data_list);
        mProgress = view.findViewById(R.id.progress);
        mAdapter = new TrackAdapter(new ArrayList<Track>());
        mAdapter.setTrackClickListener(new TrackHolder.TrackClickListener() {
            @Override
            public void onTrackClicked(Track track) {
                ArrayList<Track> tracks = mAdapter.getTracks();
                TrackActivity.playTrack(view.getContext(), tracks, tracks.indexOf(track));
            }
        });
        tracksList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        tracksList.setAdapter(mAdapter);
        updateUI();
        loadAlbom();

        return view;
    }

    private void loadAlbom(){
        mProgress.setVisibility(View.VISIBLE);
        mProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        MusicAPI.getMusicService().getAlbom(mAlbom.getId()).enqueue(new Callback<Albom>() {
            @Override
            public void onResponse(Call<Albom> call, Response<Albom> response) {
                mProgress.setVisibility(View.GONE);
                mAlbom = response.body();
                updateUI();
            }

            @Override
            public void onFailure(Call<Albom> call, Throwable t) {
                mProgress.setVisibility(View.GONE);
            }
        });
    }

    private void updateUI(){
        mAlbomName.setText(mAlbom.getName());
        mAdapter.setTracks(mAlbom.getTracks());
    }
}
