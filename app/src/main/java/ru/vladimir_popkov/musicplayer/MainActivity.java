package ru.vladimir_popkov.musicplayer;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ru.vladimir_popkov.musicplayer.adapters.ViewPagerAdapter;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ((ViewPager) findViewById(R.id.data_music))
                .setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
    }
}
