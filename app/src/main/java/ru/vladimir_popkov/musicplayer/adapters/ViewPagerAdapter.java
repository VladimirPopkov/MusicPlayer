package ru.vladimir_popkov.musicplayer.adapters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.List;

import ru.vladimir_popkov.musicplayer.fragments.ArtistListFragments;
import ru.vladimir_popkov.musicplayer.fragments.TrackListFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0){
            return new ArtistListFragments();
        } else if (position == 1){
            return new TrackListFragment();
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return "Исполнители";
        } else if (position == 1){
            return "Все треки";
        } else {
            return super.getPageTitle(position);
        }
    }
}
