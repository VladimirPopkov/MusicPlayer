package ru.vladimir_popkov.musicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.adapters.holders.TrackHolder;
import ru.vladimir_popkov.musicplayer.data.Track;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class TrackAdapter extends RecyclerView.Adapter<TrackHolder> {

    private ArrayList<Track> mTracks;
    private TrackHolder.TrackClickListener mTrackClickListener;

    public TrackAdapter(ArrayList<Track> tracks){
        mTracks = tracks;
        notifyDataSetChanged();
    }

    @Override
    public TrackHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_track, parent, false);
        return new TrackHolder(view, mTrackClickListener);
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

    public void setTrackClickListener(TrackHolder.TrackClickListener trackClickListener) {
        mTrackClickListener = trackClickListener;
    }

    public void setTracks(List<Track> tracks){
        mTracks.clear();
        if (tracks != null) {
            mTracks.addAll(tracks);
        }
        notifyDataSetChanged();
    }

    public ArrayList<Track> getTracks() {
        return mTracks;
    }
}
