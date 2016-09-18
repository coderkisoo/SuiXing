package com.vehicle.suixing.suixing.ui.activity;

import android.content.Intent;
import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.bean.MapBean.MapInfo;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.BaseNaviActivity;

import java.util.Calendar;

/**
 * Created by KiSoo on 2016/7/24.
 */
public class NaviActivity extends BaseNaviActivity {

    private MapInfo startPoint, targetPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_nav);
        anv_map = (AMapNaviView) findViewById(R.id.anv_map);
        super.onCreate(savedInstanceState);
        initView();
        Intent intent = getIntent();
        startPoint = intent.getParcelableExtra(Config.KEY_START_LATING);
        targetPoint = intent.getParcelableExtra(Config.KEY_END_LATING);
        setRoute(startPoint,targetPoint);
    }

    private void initView() {
        if (anv_map == null) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setSettingMenuEnabled(true);//设置菜单按钮是否在导航界面显示
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        viewOptions.setNaviNight(hour>18 || hour<6);//设置导航界面是否显示黑夜模式
        viewOptions.setReCalculateRouteForYaw(true);//设置偏航时是否重新计算路径
        viewOptions.setReCalculateRouteForTrafficJam(false);//前方拥堵时是否重新计算路径
        viewOptions.setTrafficInfoUpdateEnabled(true);//设置交通播报是否打开
        viewOptions.setCameraInfoUpdateEnabled(true);//设置摄像头播报是否打开
        viewOptions.setScreenAlwaysBright(true);//设置导航状态下屏幕是否一直开启。
        viewOptions.setNaviViewTopic(AMapNaviViewOptions.WHITE_COLOR_TOPIC);//设置导航界面的主题
        anv_map.setViewOptions(viewOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNavi.startNavi(NaviType.EMULATOR);
    }
}
