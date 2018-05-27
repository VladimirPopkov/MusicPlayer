package ru.vladimir_popkov.musicplayer.network;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by v.popkov on 20.05.2018.
 */
public class MusicAPI {

    public static final String ENDPOINT = "https://hidden-castle-61020.herokuapp.com/api/";

    private Retrofit retrofit;
    private MusicService musicService;

    private static MusicAPI INSTANCE;

    private static MusicAPI getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MusicAPI();
        }
        return INSTANCE;
    }

    private MusicAPI() {
        retrofit = new Retrofit.Builder()
                .baseUrl(ENDPOINT)
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new ApiInterceptor())
                        .addInterceptor(new HttpLoggingInterceptor()
                                .setLevel(HttpLoggingInterceptor.Level.BODY))
                        .build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static MusicService getMusicService() {
        if (getInstance().musicService == null) {
            getInstance().musicService = getInstance().retrofit.create(MusicService.class);
        }
        return getInstance().musicService;
    }

    private class ApiInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization", "Basic dm92YW46bWFsb3k=")
                    .build());
        }
    }
}
