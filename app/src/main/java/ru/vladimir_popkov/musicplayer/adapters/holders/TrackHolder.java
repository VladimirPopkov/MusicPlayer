package ru.vladimir_popkov.musicplayer.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Track;

/**
 * Created by v.popkov on 05.05.2018.
 */
public class TrackHolder extends RecyclerView.ViewHolder {

    private ImageView mTrackCover;
    private TextView mTrackName;
    private TextView mTrackPerformer;
    private Track mTrack;

    public interface TrackClickListener {
        void onTrackClicked(Track track);
    }

    public TrackHolder(View itemView, final TrackClickListener trackClickListener){
        super(itemView);
        mTrackCover = (ImageView) itemView.findViewById(R.id.track_cover);
        mTrackName = (TextView) itemView.findViewById(R.id.track_name);
        mTrackPerformer = (TextView) itemView.findViewById(R.id.track_performer);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (trackClickListener != null) {
                    trackClickListener.onTrackClicked(mTrack);
                }
            }
        });
    }

    public void bindCrime(Track track){
        mTrack = track;
        Picasso.get() //передаем контекст приложения
                .load( mTrack.getCover()) //адрес изображения
                .into(mTrackCover); //ссылка на ImageView
        mTrackName.setText(mTrack.getName());
        mTrackPerformer.setText(mTrack.getArtist().getName());
    }
}
