package com.vehicle.suixing.suixing.ui.fragment.main;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.model.MeFragmentView;
import com.vehicle.suixing.suixing.presenter.MeFragmentPresenter;
import com.vehicle.suixing.suixing.ui.adapter.InfoAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by KiSoo on 2016/4/11.
 */
public class MeFragment extends Fragment implements MeFragmentView {

    private View view;
    private MeFragmentPresenter presenter;

    //绑定
//    @Bind(R.id.civ_head)
//    CircleImageView civ_head;
//    @Bind(R.id.tv_name)
//    TextView tv_name;
//    @Bind(R.id.tv_motto)
//    TextView tv_motto;
    @Bind(R.id.rv_vehicle)
    RecyclerView rv_vehicle;
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;
   //点击事件
//    @OnClick(R.id.civ_head)
//    void civ_head(){
//        /**
//         * 修改头像
//         * */
//    }
//
//    @OnClick(R.id.tv_name)
//    void tv_name(){
//        /**
//         * 修改名称
//         * */
//    }
//
//    @OnClick(R.id.tv_motto)
//    void tv_motto(){
//        /**
//         * 修改个性签名
//         * */
//    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_me,null);
        ButterKnife.bind(this,view);
        presenter = new MeFragmentPresenter(getActivity(),this);
        initEvents();
        presenter.getInfo();
        return view;
    }


    private void initEvents() {
        srl_refresh.setColorSchemeColors(Color.parseColor("#4D7FE5D2"),
                Color.parseColor("#137FE5D2"), Color.parseColor("#AE7FE5D2"),
                Color.parseColor("#7FE5D2"));
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });
    }


    @Override
    public void setAdapter(InfoAdapter infoAdapter) {
        rv_vehicle.setLayoutManager(new LinearLayoutManager(getActivity()));
        rv_vehicle.setAdapter(infoAdapter);
    }
}
