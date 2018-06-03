package ru.vladimir_popkov.musicplayer.adapters.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.vladimir_popkov.musicplayer.R;
import ru.vladimir_popkov.musicplayer.data.Albom;

public class AlbomHolder extends RecyclerView.ViewHolder {

    private TextView mAlbomName;
    private ImageView mAlbomCover;
    private Albom mAlbom;

    public interface AlbomClickListener {
        void onAlbomClicked(Albom albom);
    }

    public AlbomHolder(View itemView, final AlbomClickListener albomClickListener){
        super(itemView);
        mAlbomCover = (ImageView) itemView.findViewById(R.id.albom_cover);
        mAlbomName = (TextView) itemView.findViewById(R.id.albom_name);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (albomClickListener != null) {
                    albomClickListener.onAlbomClicked(mAlbom);
                }
            }
        });
    }

    public void bindCrime(Albom albom){
        mAlbom = albom;
        Picasso.get()
                .load( mAlbom.getCover())
                .into(mAlbomCover);
        mAlbomName.setText(mAlbom.getName());
    }
}
