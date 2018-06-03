package ru.vladimir_popkov.musicplayer.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Artist;

public class ArtistHolder extends RecyclerView.ViewHolder {

    private TextView mArtistName;
    private Artist mArtist;

    public interface ArtistClickListener {
        void onArtistClicked(Artist artist);
    }

    public ArtistHolder(View itemView, final ArtistHolder.ArtistClickListener artistClickListener){
        super(itemView);
        mArtistName = (TextView) itemView.findViewById(R.id.artist_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (artistClickListener != null) {
                    artistClickListener.onArtistClicked(mArtist);
                }
            }
        });
    }

    public void bindCrime(Artist artist){
        mArtist = artist;
        mArtistName.setText(mArtist.getName());
    }
}
