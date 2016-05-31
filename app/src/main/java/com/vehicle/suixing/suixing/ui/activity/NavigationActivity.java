package com.vehicle.suixing.suixing.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.baidu.navisdk.adapter.BNRouteGuideManager;
import com.baidu.navisdk.adapter.BNRouteGuideManager.OnNavigationListener;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviBaseCallbackModel;
import com.baidu.navisdk.adapter.BaiduNaviCommonModule;
import com.baidu.navisdk.adapter.NaviModuleFactory;
import com.baidu.navisdk.adapter.NaviModuleImpl;
import com.vehicle.suixing.suixing.R;
import com.vehicle.suixing.suixing.common.Config;
import com.vehicle.suixing.suixing.ui.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * 诱导界面
 *
 * @author sunhao04
 */
public class NavigationActivity extends BaseActivity {

    private final String TAG = "NavigationActivity";
    private BNRoutePlanNode mBNRoutePlanNode = null;
    private String name;
    private BaiduNaviCommonModule mBaiduNaviCommonModule = null;

    /*
     * 对于导航模块有两种方式来实现发起导航。 1：使用通用接口来实现 2：使用传统接口来实现
     */
    // 是否使用通用接口

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用通用接口
        mBaiduNaviCommonModule = NaviModuleFactory.getNaviModuleManager().getNaviCommonModule(
                NaviModuleImpl.BNaviCommonModuleConstants.ROUTE_GUIDE_MODULE,
                this,
                BNaviBaseCallbackModel.BNaviBaseCallbackConstants.CALLBACK_ROUTEGUIDE_TYPE,
                mOnNavigationListener);
        mBaiduNaviCommonModule.onCreate();
        View view = mBaiduNaviCommonModule.getView();
        setContentView(view);
        Bundle bundle = getIntent().getExtras();
        mBNRoutePlanNode = (BNRoutePlanNode) bundle.getSerializable(Config.KEY_ROUTE_PLANE_NODE);
        name = bundle.getString(Config.KEY_GAS_NAME);
//        addCustomizedLayerItems();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onResume();
        }
        BNRouteGuideManager.getInstance().resetEndNodeInNavi(new BNRoutePlanNode(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(), name, null, BNRoutePlanNode.CoordinateType.BD09LL));
    }

    protected void onPause() {
        super.onPause();


        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onPause();
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onDestroy();
        }


    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onStop();
        }


    }

    @Override
    public void onBackPressed() {
        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onBackPressed(false);
        }
    }

    public void onConfigurationChanged(android.content.res.Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (mBaiduNaviCommonModule != null) {
            mBaiduNaviCommonModule.onConfigurationChanged(newConfig);
        }
    }

    private void addCustomizedLayerItems() {
        List<BNRouteGuideManager.CustomizedLayerItem> items = new ArrayList<>();
        BNRouteGuideManager.CustomizedLayerItem item1 = new BNRouteGuideManager.CustomizedLayerItem(mBNRoutePlanNode.getLongitude(), mBNRoutePlanNode.getLatitude(),
                BNRoutePlanNode.CoordinateType.BD09LL, getResources().getDrawable(R.mipmap.ic_launcher), BNRouteGuideManager.CustomizedLayerItem.ALIGN_CENTER);
        items.add(item1);

        BNRouteGuideManager.getInstance().setCustomizedLayerItems(items);
        BNRouteGuideManager.getInstance().showCustomizedLayer(true);
    }


    private OnNavigationListener mOnNavigationListener = new OnNavigationListener() {

        @Override
        public void onNaviGuideEnd() {
            finish();
        }

        @Override
        public void notifyOtherAction(int actionType, int arg1, int arg2, Object obj) {

            if (actionType == 0) {
                Log.i(TAG, "notifyOtherAction actionType = " + actionType + ",导航到达目的地！");
            }

            Log.i(TAG, "actionType:" + actionType + "arg1:" + arg1 + "arg2:" + arg2 + "obj:" + obj.toString());
        }

    };
}
