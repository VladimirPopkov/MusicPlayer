package ru.vladimir_popkov.musicplayer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class TrackHolder extends RecyclerView.ViewHolder {

    private ImageView mTrackCover;
    private TextView mTrackName;
    private TextView mTrackPerformer;
    private Track mTrack;

    TrackHolder(View itemView){
        super(itemView);
        mTrackCover = (ImageView) itemView.findViewById(R.id.track_cover);
        mTrackName = (TextView) itemView.findViewById(R.id.track_name);
        mTrackPerformer = (TextView) itemView.findViewById(R.id.track_performer);
    }

    public void bindCrime(Track track){
        mTrack = track;
        Picasso.with(itemView.getContext()) //передаем контекст приложения
                .load( mTrack.getCover()) //адрес изображения
                .into(mTrackCover); //ссылка на ImageView
        mTrackName.setText(mTrack.getTrackName());
        mTrackPerformer.setText(mTrack.getTrackPerformer());
    }
}
