package ru.vladimir_popkov.musicplayer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import ru.vladimir_popkov.musicplayer.data.Albom;
import ru.vladimir_popkov.musicplayer.data.Artist;
import ru.vladimir_popkov.musicplayer.fragments.AlbomDetailsFragments;
import ru.vladimir_popkov.musicplayer.fragments.ArtistDetailsFragments;

public class DetailsActivity extends AppCompatActivity {

    private static final int TYPE_ARTIST = 0;
    private static final int TYPE_ALBOM = 1;
    private static final String TYPE_KEY = "details type";

    public static void showArtist(Context context, Artist artist){
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Artist.class.getCanonicalName(), artist);
        intent.putExtra(TYPE_KEY, TYPE_ARTIST);
        context.startActivity(intent);
    }

    public static void showAlbom(Context context, Albom albom){
        Intent intent = new Intent(context, DetailsActivity.class);
        intent.putExtra(Albom.class.getCanonicalName(), albom);
        intent.putExtra(TYPE_KEY, TYPE_ALBOM);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if (getIntent().getIntExtra(TYPE_KEY, -1) == TYPE_ARTIST){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.albom_frgm, ArtistDetailsFragments
                            .getInstance((Artist) getIntent()
                                    .getSerializableExtra(Artist.class.getCanonicalName())))
                    .commit();
        }else if (getIntent().getIntExtra(TYPE_KEY, -1) == TYPE_ALBOM){
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.albom_frgm, AlbomDetailsFragments
                            .getInstance((Albom) getIntent()
                                    .getSerializableExtra(Albom.class.getCanonicalName())))
                    .commit();
        }
    }
}
