package ru.vladimir_popkov.musicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.vladimir_popkov.musicplayer.adapters.holders.AlbomHolder;
import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Albom;
import ru.vladimir_popkov.musicplayer.data.Artist;

public class AlbomAdapter extends RecyclerView.Adapter<AlbomHolder> {

    private List<Albom> mAlboms;
    private AlbomHolder.AlbomClickListener mAlbomClickListener;

    public AlbomAdapter(Artist artist){
        mAlboms = artist.getAlboms();
        notifyDataSetChanged();
    }

    @Override
    public AlbomHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_albom, parent, false);
        return new AlbomHolder(view, mAlbomClickListener);
    }

    @Override
    public void onBindViewHolder(AlbomHolder holder, int position) {
        Albom albom = mAlboms.get(position);
        holder.bindCrime(albom);
    }

    @Override
    public int getItemCount() {
        return mAlboms.size();
    }

    public void setAlbomClickListener(AlbomHolder.AlbomClickListener albomClickListener) {
        mAlbomClickListener = albomClickListener;
    }
}
