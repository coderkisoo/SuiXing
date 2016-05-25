package com.vehicle.suixing.suixing.view.fragment;

import com.vehicle.suixing.suixing.ui.adapter.MusicAdapter;

/**
 * Created by KiSoo on 2016/5/11.
 */
public interface MusicFragmentView {

    void setMusicName(String title);

    void setArtist(String artist);

    void setLoad(boolean load);

    void setAdapter(MusicAdapter adapter);

    void setPlay(boolean isPlay);
}
