package ru.vladimir_popkov.musicplayer;

import android.app.Application;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by v.popkov on 20.05.2018.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initPicasso();
    }

    private void initPicasso() {
        Picasso.setSingletonInstance(new Picasso.Builder(getApplicationContext()).downloader(
                new OkHttp3Downloader(new OkHttpClient.Builder()
                        .addNetworkInterceptor(new PicassoInterceptor()).build()))
                .build());
    }

    private class PicassoInterceptor implements Interceptor {

        @Override
        public Response intercept(Interceptor.Chain chain) throws IOException {
            return chain.proceed(chain.request().newBuilder()
                    .addHeader("Authorization", "Basic dm92YW46bWFsb3k=")
                    .build());
        }
    }
}
