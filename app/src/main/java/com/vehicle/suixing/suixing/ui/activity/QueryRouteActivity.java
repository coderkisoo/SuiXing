package com.vehicle.suixing.suixing.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.activity.QueryRoutePresenter;
import com.vehicle.suixing.suixing.ui.BaseSlidingActivity;
import com.vehicle.suixing.suixing.ui.adapter.RecentRouteAdapter;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.view.activity.QueryRouteActivityView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/7/19.
 */
public class QueryRouteActivity extends BaseSlidingActivity implements QueryRouteActivityView,AdapterView.OnItemClickListener {
    @Bind(R.id.et_start)
    EditText et_start;
    @Bind(R.id.et_target)
    EditText et_target;
    @Bind(R.id.lv_route_example)
    ListView lv_route_example;
    @OnClick(R.id.iv_exchange)
    void exchange(){
        presenter.exchange();
    }
    @OnClick(R.id.iv_go)
    void go(){
        presenter.go();
    }
    @OnClick(R.id.iv_toolbar_left_image)
    void back(){
        finish();
    }
    private QueryRoutePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_route);
        ButterKnife.bind(this);
        presenter = new QueryRoutePresenter(this,this,getIntent());
        lv_route_example.setOnItemClickListener(this);

    }

    @Override
    public void setStartWatcher(TextWatcher startWatcher) {
        et_start.addTextChangedListener(startWatcher);
    }

    @Override
    public void setEndWatcher(TextWatcher endWatcher) {
        et_target.addTextChangedListener(endWatcher);
    }

    @Override
    public void setAdapter(RecentRouteAdapter adapter) {
        lv_route_example.setAdapter(adapter);
    }

    @Override
    public void setText(boolean isStart, String name) {
        if (isStart){
            Log.e(TAG,"current focus is et_start");
            et_start.setText(name);
        }else {
            Log.e(TAG,"current focus is et_target");
            et_target.setText(name);
        }
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public String getStart() {
        return et_start.getText().toString();
    }

    @Override
    public String getEnd() {
        return et_target.getText().toString();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        presenter.choose(position);
    }
}
