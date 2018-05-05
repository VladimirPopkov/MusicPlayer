package ru.vladimir_popkov.musicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackHolder> {

    List<Track> mTracks;

    public TrackAdapter(List<Track> tracks){
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_track, parent, false);
        return new TrackHolder(view);
    }

    @Override
    public void onBindViewHolder(TrackHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindCrime(track);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}