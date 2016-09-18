package com.vehicle.suixing.suixing.ui.fragment.peccany;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.presenter.fragment.PeccanyFragmentPresenter;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.view.fragment.PeccanyFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/31.
 */
public class PeccanyFragment extends Fragment implements PeccanyFragmentView {
    /**
     * 查询前的fragment
     * */
    private View view;
    private String TAG = this.getClass().getName();
    private PeccanyFragmentPresenter presenter;

    @Bind(R.id.rl_choose_myverhicle)
    RelativeLayout rl_choose_myvehicle;
    @Bind(R.id.tv_myvehicle)
    TextView tv_myvehicle;

    public PeccanyFragment() {
    }

    @OnClick(R.id.rl_choose_myverhicle)
    void rl_choose_myverhicle() {
        presenter.showWindow();
    }
    @OnClick(R.id.tv_query)
    void tv_query(){
        /**
         * 查询
         * */
        presenter.query();
    }
    @OnClick(R.id.tv_query_others)
    void tv_query_others(){
        /**
         * 查询其他车辆
         * */
        presenter.queryOthers();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_peccany_main, null);
        ButterKnife.bind(this, view);
        presenter = new PeccanyFragmentPresenter(this, getActivity());
        return view;
    }

    @Override
    public void showWindow(PopupWindow window) {
        Log.e(TAG, "popipwindow已经弹出");
        window.showAsDropDown(rl_choose_myvehicle);
    }

    @Override
    public int getWidth() {
        return rl_choose_myvehicle.getWidth();
    }

    @Override
    public int getHeight() {
        return rl_choose_myvehicle.getHeight();
    }

    @Override
    public void setVehicle(String name) {
        tv_myvehicle.setText(name);
    }
}
