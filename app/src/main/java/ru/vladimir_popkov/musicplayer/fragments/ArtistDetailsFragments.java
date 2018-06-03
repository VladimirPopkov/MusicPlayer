package ru.vladimir_popkov.musicplayer.fragments;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ru.vladimir_popkov.musicplayer.adapters.AlbomAdapter;
import ru.vladimir_popkov.musicplayer.adapters.holders.AlbomHolder;
import ru.vladimir_popkov.musicplayer.DetailsActivity;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Albom;
import ru.vladimir_popkov.musicplayer.data.Artist;

public class ArtistDetailsFragments extends Fragment {

    public static ArtistDetailsFragments getInstance(Artist artist){
        ArtistDetailsFragments artistDetailsFragments = new ArtistDetailsFragments();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Artist.class.getCanonicalName(), artist);
        artistDetailsFragments.setArguments(bundle);
        return artistDetailsFragments;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Artist artist = (Artist) getArguments().getSerializable(Artist.class.getCanonicalName());
        final View view = inflater.inflate(R.layout.fragment_details_artist, null);
        RecyclerView albomsList = (RecyclerView) view.findViewById(R.id.data_list);
        AlbomAdapter adapter = new AlbomAdapter(artist);
        adapter.setAlbomClickListener(new AlbomHolder.AlbomClickListener() {
            @Override
            public void onAlbomClicked(Albom albom) {
                DetailsActivity.showAlbom(view.getContext(), albom);
            }
        });
        albomsList.setLayoutManager(new LinearLayoutManager(view.getContext()));
        albomsList.setAdapter(adapter);

        return view;
    }
}
