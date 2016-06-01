package com.vehicle.suixing.suixing.ui.fragment.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.app.SuixingApp;
import com.vehicle.suixing.suixing.presenter.fragment.VehicleInfoFragmentPresenter;
import com.vehicle.suixing.suixing.ui.adapter.MyPageTransFormer;
import com.vehicle.suixing.suixing.ui.adapter.MyPagerAdapter;
import com.vehicle.suixing.suixing.util.Log;
import com.vehicle.suixing.suixing.view.fragment.VehicleInfoFragmentView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by KiSoo on 2016/3/20.
 */
public class VehicleInformationFragment extends Fragment implements VehicleInfoFragmentView {
    private String TAG = "VehicleInformationFragment";
    private View view;
    private VehicleInfoFragmentPresenter presenter;
    private int nowPosition = 0;
    @Bind(R.id.vp_choose_vehicle_list)
    ViewPager vp_choose_vehicle_list;
    @Bind(R.id.ll_container)
    LinearLayout ll_container;

    @OnClick(R.id.v_left_view)
    void v_left_view() {
        vp_choose_vehicle_list.setCurrentItem(nowPosition - 1);
    }

    @OnClick(R.id.v_right_view)
    void v_right_view() {
        vp_choose_vehicle_list.setCurrentItem(nowPosition + 1);
    }


    @OnClick(R.id.iv_add_vehicle)
    void iv_add_vehicle() {
        Log.d(TAG, "iv_add_vehicle: 添加汽车");
        /**
         * 启动相机是耗时操作，防止多次点击创建多个activity
         * */
        presenter.addVehicle();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_vehicle_information, null);
        ButterKnife.bind(this, view);
        presenter = new VehicleInfoFragmentPresenter(this, getActivity(),getChildFragmentManager());
        initView();
        return view;
    }

    /*初始化控件的各项属性*/
    private void initView() {
        ll_container.setOnTouchListener(new View.OnTouchListener() {
            /**
             * 将父控件的onTouch()方法分发
             * */
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return vp_choose_vehicle_list.dispatchTouchEvent(event);
            }
        });

        vp_choose_vehicle_list.setPageTransformer(true, new MyPageTransFormer());
        vp_choose_vehicle_list.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                presenter.startFragment(position);
                nowPosition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
        vp_choose_vehicle_list.setOffscreenPageLimit(SuixingApp.infos.size() + 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.initInfo();
        if (vp_choose_vehicle_list.getAdapter() == null){
            presenter.setAdapter();
        }else {
            vp_choose_vehicle_list.getAdapter().notifyDataSetChanged();
        }
        vp_choose_vehicle_list.setOffscreenPageLimit(SuixingApp.infos.size() + 1);
    }

    @Override
    public void setAdapter(MyPagerAdapter adapter) {
        vp_choose_vehicle_list.setAdapter(adapter);
    }

}
