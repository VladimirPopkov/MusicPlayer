package ru.vladimir_popkov.musicplayer.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import ru.vladimir_popkov.musicplayer.data.Artist;
import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.data.Albom;

/**
 * Created by v.popkov on 20.05.2018.
 */
public interface MusicService {
    @GET("albom")
    Call<List<Albom>> getAlbom(@Query("id") String id);

    @GET("artist")
    Call<List<Artist>> getArtist(@Query("id") String id);

    @GET("track")
    Call<List<Track>> getTrack(@Query("id") String id);

    @GET("tracks")
    Call<List<Track>> getTracks();
}
