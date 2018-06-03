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
import ru.vladimir_popkov.musicplayer.adapters.ArtistAdapter;
import ru.vladimir_popkov.musicplayer.adapters.holders.ArtistHolder;
import ru.vladimir_popkov.musicplayer.DetailsActivity;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Artist;
import ru.vladimir_popkov.musicplayer.network.MusicAPI;

public class ArtistListFragments extends Fragment {
    private ArtistAdapter mAdapter;
    private List<Artist> mArtists = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_list, null);
        RecyclerView artistList = (RecyclerView) view.findViewById(R.id.data_list);
        mAdapter = new ArtistAdapter(mArtists);
        mAdapter.setArtistClickListener(new ArtistHolder.ArtistClickListener() {
            @Override
            public void onArtistClicked(Artist artist) {
                DetailsActivity.showArtist(view.getContext(), artist);
            }
        });
        artistList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        artistList.setAdapter(mAdapter);
        loadArtists();

        return view;
    }

    private void loadArtists(){
        MusicAPI.getMusicService().getArtists().enqueue(new Callback<List<Artist>>() {
            @Override
            public void onResponse(Call<List<Artist>> call, Response<List<Artist>> response) {
                mArtists.clear();
                mArtists.addAll(response.body());
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<Artist>> call, Throwable t) {
                Log.d("vova", "onFailure: " + t);
            }
        });
    }
}
