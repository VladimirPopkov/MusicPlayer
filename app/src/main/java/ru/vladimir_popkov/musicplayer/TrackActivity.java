package ru.vladimir_popkov.musicplayer;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class TrackActivity extends AppCompatActivity {

    TextView mTrackName;
    TextView mTrackTime;
    SeekBar mSeekBar;
    ImageView mBtnPrevious;
    ImageView mBtnPlay;
    ImageView mBtnNext;
    ImageView mCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);

        mTrackName = (TextView) findViewById(R.id.track_name);
        mCover = (ImageView) findViewById(R.id.cover);

        Track track = (Track) getIntent().getParcelableExtra(Track.class.getCanonicalName());
        mTrackName.setText(track.getTrackPerformer() + " - " +track.getTrackName());

        Picasso.with(this) //передаем контекст приложения
                .load( track.getCover()) //адрес изображения
                .into(mCover); //ссылка на ImageView
    }
}
