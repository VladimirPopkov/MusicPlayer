package ru.vladimir_popkov.musicplayer.network;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.vladimir_popkov.musicplayer.data.Artist;
import ru.vladimir_popkov.musicplayer.data.Track;
import ru.vladimir_popkov.musicplayer.data.Albom;

/**
 * Created by v.popkov on 20.05.2018.
 */
public interface MusicService {
    @GET("albom/{id}")
    Call<Albom> getAlbom(@Path("id") String id);

    @GET("artist/{id}")
    Call<Artist> getArtist(@Path("id") String id);

    @GET("track/{id}")
    Call<Track> getTrack(@Path("id") String id);

    @GET("tracks")
    Call<List<Track>> getTracks();

    @GET("artists")
    Call<List<Artist>> getArtists();
}
