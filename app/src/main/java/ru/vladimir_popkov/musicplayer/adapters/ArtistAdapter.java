package ru.vladimir_popkov.musicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.vladimir_popkov.musicplayer.adapters.holders.ArtistHolder;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Artist;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistHolder> {

    private List<Artist> mArtists;
    private ArtistHolder.ArtistClickListener mArtistClickListener;

    public ArtistAdapter(List<Artist> artists){
        mArtists = artists;
        notifyDataSetChanged();
    }

    @Override
    public ArtistHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);
        return new ArtistHolder(view, mArtistClickListener);
    }

    @Override
    public void onBindViewHolder(ArtistHolder holder, int position) {
        Artist artist = mArtists.get(position);
        holder.bindCrime(artist);
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    public void setArtistClickListener(ArtistHolder.ArtistClickListener artistClickListener) {
        mArtistClickListener = artistClickListener;
    }
}
