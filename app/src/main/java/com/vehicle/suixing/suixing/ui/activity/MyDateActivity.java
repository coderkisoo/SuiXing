package com.vehicle.suixing.suixing.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.AdapterView;
import android.widget.ListView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.activity.MyDateActivityPresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.ui.adapter.DateAdapter;
import com.vehicle.suixing.suixing.view.activity.MyDateActivityView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/5/16.
 */
public class MyDateActivity extends BaseSlidingActivity implements MyDateActivityView{
    @OnClick(R.id.iv_toolbar_left_image)
    void iv_toolbar_left_image(){
        finish();
    }
    @Bind(R.id.srl_refresh)
    SwipeRefreshLayout srl_refresh;
    @Bind(R.id.lv_my_date)
    ListView lv_my_date;
    private MyDateActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_date);
        ButterKnife.bind(this);
        srl_refresh.setColorSchemeColors(Color.parseColor("#4FA3F9"),
                Color.parseColor("#50A2F8"), Color.parseColor("#3F51B5"),
                Color.parseColor("#303F9F"));
        presenter = new MyDateActivityPresenter(this,this);
        srl_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.requestList();
            }
        });
        presenter.requestList();
    }

    @Override
    public void showRefresh(boolean refresh) {
        srl_refresh.setRefreshing(refresh);
    }

    @Override
    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        lv_my_date.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void setAdapter(DateAdapter adapter) {
        lv_my_date.setAdapter(adapter);
    }
}
