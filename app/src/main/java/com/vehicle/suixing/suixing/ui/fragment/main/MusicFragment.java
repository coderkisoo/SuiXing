package com.vehicle.suixing.suixing.ui.fragment.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.fragment.MusicFragmentPresenter;
import com.vehicle.suixing.suixing.ui.adapter.MusicAdapter;
import com.vehicle.suixing.suixing.view.fragment.MusicFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/5/11.
 */
public class MusicFragment extends Fragment implements MusicFragmentView {

    @Bind(R.id.tv_music_name)
    TextView tv_music_name;
    @Bind(R.id.tv_artist)
    TextView tv_artist;
    @Bind(R.id.iv_next_music)
    ImageView iv_next_music;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;
    @Bind(R.id.rv_music)
    RecyclerView rv_music;
    @Bind(R.id.iv_start)
    ImageView iv_start;

    @OnClick(R.id.iv_start)
    void iv_start() {
        //播放
        presenter.start();
    }

    @OnClick(R.id.iv_next_music)
    void iv_next_music() {
        //下一曲
        presenter.next();
    }

    private MusicFragmentPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, null);
        ButterKnife.bind(this, view);
        presenter = new MusicFragmentPresenter(getActivity(), this);
        initEvent();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.bindService();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.unBindService();
    }

    private void initEvent() {
        srl_refresh.setColorSchemeColors(Color.parseColor("#4FA3F9"),
                Color.parseColor("#50A2F8"), Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"));
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.updateList();
            }
        });
    }

    @Override
    public void setMusicName(String title) {
        tv_music_name.setText(title);
    }

    @Override
    public void setArtist(String artist) {
        tv_artist.setText(artist);
    }

    @Override
    public void setLoad(boolean load) {
        srl_refresh.setRefreshing(load);
    }

    @Override
    public void setAdapter(MusicAdapter adapter) {
        rv_music.setAdapter(adapter);
    }

    @Override
    public void setPlay(boolean isPlay) {
        iv_start.setImageResource(isPlay ? R.mipmap.music_begin : R.mipmap.music_pause);
    }

}
